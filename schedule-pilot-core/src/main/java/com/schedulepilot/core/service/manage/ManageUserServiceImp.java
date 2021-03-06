package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.constants.ParameterConstants;
import com.schedulepilot.core.dto.model.CountryDto;
import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.TokenDto;
import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.email.constants.EmailConstants;
import com.schedulepilot.core.entities.model.CountryEntity;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.ManageUserException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.UserAccountAuthRequest;
import com.schedulepilot.core.request.UserAccountChangePasswordRequest;
import com.schedulepilot.core.request.UserAccountCreateRequest;
import com.schedulepilot.core.request.UserAccountForgotPasswordRequest;
import com.schedulepilot.core.response.UserAccountAuthResponse;
import com.schedulepilot.core.security.token.service.ManageTokenService;
import com.schedulepilot.core.service.CountryEntityService;
import com.schedulepilot.core.service.GlobalListDynamicService;
import com.schedulepilot.core.service.NotificationLayerService;
import com.schedulepilot.core.service.UserAccountService;
import com.schedulepilot.core.service.modelmapper.CountryMapperService;
import com.schedulepilot.core.util.SecurityUtil;
import com.schedulepilot.core.util.dto.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class ManageUserServiceImp implements ManageUserService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ManageTokenService manageTokenService;

    @Autowired
    private NotificationLayerService notificationLayerService;

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CountryEntityService countryEntityService;

    @Autowired
    private CountryMapperService countryMapperService;

    @Override
    @Transactional
    public UserAccountDto createUserAccount(UserAccountCreateRequest userAccountCreateRequest) throws SchedulePilotException {

        UserAccountDto userAccount = UserAccountService.convertRequestToDTO(userAccountCreateRequest);
        userAccount.setUsername(userAccount.getEmail());

        Validator validator = this.userAccountService.validationBeforeSave(userAccount);
        if (!validator.isValid())
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_CREATE_USER, validator.getFirstError());

        RolAccountDto rolAccount = this.globalListDynamicService.getRolAccountByIdOrException(userAccount.getRolAccountEntity().getId());
        validator = rolAccount.validationForCreateUser(userAccount);
        if (!validator.isValid())
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_CREATE_USER, validator.getFirstError());

        userAccount.setActivationTokenEntity(this.manageTokenService.createUserAccountActivationToken());
        userAccount.setPassword(this.passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setPasswordExpiredDate(LocalDateTime.now().plusSeconds(this.getPasswordExpiration()));

        CountryEntity countryEntity = this.countryEntityService.getCountryByCode(userAccountCreateRequest.getPhoneCountryCode());
        CountryDto countryDto = this.countryMapperService.convertEntityToDto(countryEntity);
        userAccount.setCountryEntity(countryDto);
        userAccount = this.userAccountService.save(userAccount);

        this.notificationLayerService.sendNotificationCreateUserAccount(userAccount);

        return userAccount;
    }

    @Override
    @Transactional
    public String activateUserAccount(String token, Long userAccountId) throws SchedulePilotException {
        String urlConfirmEmail = this.globalListDynamicService.getParameterValueEmpty(EmailConstants.PARAMETER_URL_CONFIRM_EMAIL);
        StringBuilder uriBuilder = new StringBuilder(urlConfirmEmail);
        uriBuilder.append("?message=");

        UserAccountDto userAccountDto = this.userAccountService.getByIdThrow(userAccountId);
        Validator validator = userAccountDto.validationForActivateUserAccount();
        if (!validator.isValid()) {
            uriBuilder.append(validator.getFirstError());
            return uriBuilder.toString();
        }

        TokenDto userToken = userAccountDto.getActivationTokenEntity();
        validator = this.manageTokenService.validateActivationUserAccountToken(userToken.getId(), token);
        if (!validator.isValid()) {
            uriBuilder.append(validator.getFirstError());
            return uriBuilder.toString();
        }

        userAccountDto.setIsActive(true);

        userAccountDto = this.userAccountService.update(userAccountDto);

        this.notificationLayerService.sendNotificationActivationUserAccount(userAccountDto);

        return uriBuilder.append(EmailConstants.SUBJECT_DEFAULT_SEND_ACTIVATE_USER_ACCOUNT).toString();
    }

    @Override
    @Transactional
    public UserAccountAuthResponse authUserAccount(UserAccountAuthRequest userAccountAuthRequest) throws SchedulePilotException {
        UserAccountDto userAccountDto = this.userAccountService.getByUsernameOrException(userAccountAuthRequest.getUsername());
        Validator validator = userAccountDto.validationForAuthUserAccount();
        if (!validator.isValid())
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_AUTH_FAILED, validator.getFirstError());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAccountAuthRequest.getUsername(),
                            userAccountAuthRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            if (ex instanceof AuthenticationException) {
                this.updateUserFailedAuth(userAccountDto);
            }
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_AUTH_FAILED, "Auth for user account failed, username or password not valid.");
        }

        TokenDto authToken = this.manageTokenService.createUserAccountAuthToken(userAccountDto.getUsername(),
                new String[]{userAccountDto.getRolAccountEntity().getName()});
        userAccountDto.setAuthTokenEntity(authToken);
        userAccountDto.setFailedAttempts(0);
        userAccountDto = this.userAccountService.update(userAccountDto);
        return new UserAccountAuthResponse(authToken.getKey(), UserAccountService.convertDTOToResponse(userAccountDto));
    }

    @Override
    public void restorePasswordUserAccount(UserAccountForgotPasswordRequest userAccountForgotPasswordRequest) throws SchedulePilotException {
        UserAccountDto userAccountDto = this.userAccountService.getByUsernameOrNull(userAccountForgotPasswordRequest.getUsername());
        if (userAccountDto == null)
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_NOT_FOUND, "User account: " +
                    userAccountForgotPasswordRequest.getUsername() + " not found.");

        Validator validator = userAccountDto.validationForRestorePasswordUserAccount();
        if (!validator.isValid())
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_FORGOT_PASSWORD, validator.getFirstError());

        userAccountDto.setPasswordExpiredDate(LocalDateTime.now().plusSeconds(this.getPasswordExpiration()));
        userAccountDto.setBlock(false);
        userAccountDto.setIsActive(true);
        userAccountDto.setFailedAttempts(0);
        String newPassword = SecurityUtil.generatePassword();
        userAccountDto.setPassword(this.passwordEncoder.encode(newPassword));
        userAccountDto = this.userAccountService.update(userAccountDto);
        this.notificationLayerService.sendNotificationForgotPasswordUserAccount(userAccountDto, newPassword);
    }

    @Override
    public void changePasswordUserAccount(UserAccountChangePasswordRequest userAccountChangePasswordRequest) throws SchedulePilotException {
        UserAccountDto userAccountDto = this.userAccountService.getByUsernameOrNull(userAccountChangePasswordRequest.getUsername());
        if (userAccountDto == null)
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_NOT_FOUND, "User account: " +
                    userAccountChangePasswordRequest.getUsername() + " not found.");

        Validator validator = userAccountDto.validationForChangePasswordUserAccount();
        if (!validator.isValid())
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_CHANGE_PASSWORD, validator.getFirstError());

        if (!this.passwordEncoder.matches(userAccountChangePasswordRequest.getActualPassword(), userAccountDto.getPassword()))
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_CHANGE_PASSWORD_MATCH, "Actual password: " +
                    userAccountChangePasswordRequest.getActualPassword() + " is not valid.");

        userAccountDto.setPasswordExpiredDate(LocalDateTime.now().plusSeconds(this.getPasswordExpiration()));
        userAccountDto.setPassword(this.passwordEncoder.encode(userAccountChangePasswordRequest.getNewPassword()));
        userAccountDto.setFailedAttempts(0);
        userAccountDto = this.userAccountService.update(userAccountDto);
        this.notificationLayerService.sendNotificationChangePasswordUserAccount(userAccountDto);
    }

    private void updateUserFailedAuth(UserAccountDto userAccountDto) throws SchedulePilotException {
        String limitLoginStr = this.globalListDynamicService.getParameterValueThrow(EmailConstants.PARAMETER_LIMIT_FAILED_ATTEMPTS);
        int limitLogin = Integer.parseInt(limitLoginStr);
        Integer failedAttempts = userAccountDto.getFailedAttempts();
        failedAttempts++;
        if (failedAttempts >= limitLogin)
            userAccountDto.setBlock(true);
        userAccountDto.setFailedAttempts(failedAttempts);
        this.userAccountService.update(userAccountDto);
    }

    private Long getPasswordExpiration() throws SchedulePilotException {
        return this.globalListDynamicService.getParameterValueAsLongOrException(ParameterConstants.PARAMETER_PASSWORD_EXPIRATION_USER_ACCOUNT);
    }

}
