package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.constants.ParameterConstants;
import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.TokenDto;
import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.email.constants.EmailConstants;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.ManageUserException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.UserAccountAuthRequest;
import com.schedulepilot.core.request.UserAccountCreateRequest;
import com.schedulepilot.core.response.UserAccountAuthResponse;
import com.schedulepilot.core.security.token.service.ManageTokenService;
import com.schedulepilot.core.service.GlobalListDinamicService;
import com.schedulepilot.core.service.ManageUserService;
import com.schedulepilot.core.service.NotificationLayerService;
import com.schedulepilot.core.service.UserAccountService;
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
    private GlobalListDinamicService globalListDinamicService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserAccountDto createUserAccount(UserAccountCreateRequest userAccountCreateRequest) throws SchedulePilotException {

        UserAccountDto userAccount = UserAccountService.convertRequestToDTO(userAccountCreateRequest);

        Validator validator = this.userAccountService.validationBeforeSave(userAccount);
        if (!validator.isValid())
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_CREATE_USER, validator.getFirstError());

        RolAccountDto rolAccount = this.globalListDinamicService.getRolAccountByIdOrException(userAccount.getRolAccountEntity().getId());
        validator = rolAccount.validationForCreateUser(userAccount);
        if (!validator.isValid())
            throw new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_CREATE_USER, validator.getFirstError());

        userAccount.setActivationTokenEntity(this.manageTokenService.createUserAccountActivationToken());
        userAccount.setPassword(this.passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setPasswordExpiredDate(LocalDateTime.now().plusSeconds(this.getPasswordExpiration()));

        userAccount = this.userAccountService.save(userAccount);

        this.notificationLayerService.sendNotificationCreateUserAccount(userAccount);

        return userAccount;
    }

    @Override
    @Transactional
    public String activateUserAccount(String token, Long userAccountId) throws SchedulePilotException {
        String urlConfirmEmail = this.globalListDinamicService.getParameterValueEmpty(EmailConstants.PARAMETER_URL_CONFIRM_EMAIL);
        StringBuilder uriBuilder = new StringBuilder(urlConfirmEmail);
        uriBuilder.append("?message=");

        UserAccountDto userAccountDto = this.userAccountService.getByIdThrow(userAccountId);
        Validator validator = userAccountDto.validationForActivationUser();
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
        UserAccountDto userAccountDto = this.userAccountService.getByUsernameThrow(userAccountAuthRequest.getUsername());
        Validator validator = userAccountDto.validationForAuthUser();
        if (!validator.isValid()) {
            throw new SchedulePilotException(validator.getFirstError());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userAccountAuthRequest.getUsername(),
                            userAccountAuthRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            if (ex instanceof AuthenticationException) {
                this.updateUserFailedAuth(userAccountDto);
            }
            throw new SchedulePilotException("Auth for user account failed, username or password not valid.");
        }

        TokenDto authToken = this.manageTokenService.createUserAccountAuthToken(userAccountDto.getUsername(),
                new String[]{userAccountDto.getRolAccountEntity().getName()});
        userAccountDto.setAuthTokenEntity(authToken);
        userAccountDto.setFailedAttempts(0);
        this.userAccountService.update(userAccountDto);
        return new UserAccountAuthResponse(authToken.getKey());
    }

    private void updateUserFailedAuth(UserAccountDto userAccountDto) throws SchedulePilotException {
        String limitLoginStr = this.globalListDinamicService.getParameterValueThrow(EmailConstants.PARAMETER_LIMIT_FAILED_ATTEMPTS);
        int limitLogin = Integer.parseInt(limitLoginStr);
        Integer failedAttempts = userAccountDto.getFailedAttempts();
        failedAttempts++;
        if (failedAttempts >= limitLogin)
            userAccountDto.setBlock(true);
        userAccountDto.setFailedAttempts(failedAttempts);
        this.userAccountService.update(userAccountDto);
    }

    private Long getPasswordExpiration() throws SchedulePilotException {
        return this.globalListDinamicService.getParameterValueAsLongOrException(ParameterConstants.PARAMETER_PASSWORD_EXPIRATION_USER_ACCOUNT);
    }

}
