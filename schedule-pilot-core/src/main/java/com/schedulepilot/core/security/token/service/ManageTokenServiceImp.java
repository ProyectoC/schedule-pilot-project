package com.schedulepilot.core.security.token.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.schedulepilot.core.config.TokenConfig;
import com.schedulepilot.core.constants.ParameterConstants;
import com.schedulepilot.core.dto.model.TokenDto;
import com.schedulepilot.core.exception.ManageParameterException;
import com.schedulepilot.core.exception.ManageTokenException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.security.token.core.ManageTokenGenerator;
import com.schedulepilot.core.service.GlobalListDynamicService;
import com.schedulepilot.core.service.TokenService;
import com.schedulepilot.core.util.dto.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class ManageTokenServiceImp implements ManageTokenService {

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenConfig tokenConfig;

    @Override
    public TokenDto createUserAccountAuthToken(String username, String[] authorities) {
        try {
            Map<String, String> parametersUser = new HashMap<>();
            parametersUser.put(TOKEN_PARAMETER_USERNAME, username);
            parametersUser.put(TOKEN_PARAMETER_GRANT_TYPE, TOKEN_VALUE_CLIENT_CREDENTIALS);
            parametersUser.put(TOKEN_PARAMETER_ID_TYPE, TOKEN_VALUE_USER_EXTERNAL);

            TokenDto newTokenUser = new TokenDto();
            newTokenUser.setTimesUsed(0);
            newTokenUser.setUsed(false);
            newTokenUser.setIsActive(true);
            newTokenUser.setKey(ManageTokenGenerator.generateCommonToken(this.tokenConfig.getClients().getUserCommon()
                            .getKey(), parametersUser, this.tokenConfig.getClients().getUserCommon().getExpirationTime(),
                    authorities));
            newTokenUser.setExpirationDate(LocalDateTime.now().plusSeconds(this.tokenConfig.getClients().getUserCommon()
                    .getExpirationTime()));
            newTokenUser.setTokenTypeEntity(globalListDynamicService.getTokenTypeByNameOrException(TOKEN_TYPE_LOGIN_USER));
            return this.tokenService.save(newTokenUser);
        } catch (UnsupportedEncodingException exception) {
            LOGGER.error(ERROR_TOKEN_USER_CREATION_UNSUPPORTED, exception.getMessage());
        } catch (JWTCreationException exception) {
            LOGGER.error(ERROR_TOKEN_USER_CREATION, exception.getMessage());
        } catch (ManageTokenException ex) {
            LOGGER.error(ERROR_TOKEN_TYPE_NOT_FOUND, ex.getMessage());
        }
        return null;
    }

    @Override
    public TokenDto createUserAccountActivationToken() {
        try {
            Long secondsTokenExpiration = this.globalListDynamicService
                    .getParameterValueAsLongOrException(ParameterConstants.PARAMETER_TOKEN_ACTIVATION_EXPIRATION_USER_ACCOUNT);
            TokenDto newTokenUser = new TokenDto();
            newTokenUser.setTimesUsed(0);
            newTokenUser.setUsed(false);
            newTokenUser.setIsActive(true);
            newTokenUser.setKey(ManageTokenGenerator.generateEmailToken());
            newTokenUser.setExpirationDate(LocalDateTime.now().plusSeconds(secondsTokenExpiration));
            newTokenUser.setTokenTypeEntity(globalListDynamicService.getTokenTypeByNameOrException(TOKEN_TYPE_ACTIVATE_USER));
            return this.tokenService.save(newTokenUser);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } catch (ManageParameterException | ManageTokenException ex) {
            LOGGER.error(ERROR_TOKEN_USER_ACTIVATION_CREATION, ex.getMessage());
        }
        return null;
    }

    @Override
    public String validateUserToken(String token, String keySecret) {
        try {
            return ManageTokenGenerator.validateCommonToken(token, keySecret, TOKEN_PARAMETER_USERNAME);
        } catch (UnsupportedEncodingException exception) {
            LOGGER.error(ERROR_TOKEN_USER_VALIDATION_UNSUPPORTED, exception.getMessage());
            return null;
        } catch (JWTVerificationException exception) {
            LOGGER.error(ERROR_TOKEN_USER_VALIDATION, exception.getMessage());
            return null;
        }
    }

    @Override
    public Validator validateActivationUserAccountToken(Long tokenId, String tokenContent) throws SchedulePilotException {
        TokenDto tokenDto = this.tokenService.getByIdAndActivateUserThrow(tokenId);
        Validator validator = tokenDto.validateForActivationUser(tokenContent);
        if (!validator.isValid()) {
            return validator;
        }
        tokenDto.setUsed(true);
        tokenDto.setTimesUsed(1);
        this.tokenService.save(tokenDto);
        return validator;
    }
}
