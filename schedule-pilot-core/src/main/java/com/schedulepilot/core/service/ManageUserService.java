package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.UserAccountAuthRequest;
import com.schedulepilot.core.request.UserAccountCreateRequest;
import com.schedulepilot.core.response.UserAccountAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public interface ManageUserService {

    Logger LOGGER = LoggerFactory.getLogger(ManageUserService.class);

    UserAccountDto createUserAccount(UserAccountCreateRequest userAccountCreateRequest) throws SchedulePilotException;

    String activateUserAccount(String token, Long userAccountId) throws SchedulePilotException;

    UserAccountAuthResponse authUserAccount(UserAccountAuthRequest userAccountAuthRequest) throws SchedulePilotException;

}