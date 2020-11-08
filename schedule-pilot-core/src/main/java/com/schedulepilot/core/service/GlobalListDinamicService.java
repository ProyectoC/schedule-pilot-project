package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.TokenTypeDto;
import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import com.schedulepilot.core.exception.ManageParameterException;
import com.schedulepilot.core.exception.ManageTokenException;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface GlobalListDinamicService {

    TokenTypeDto getTokenTypeByNameOrException(String tokenTypeName) throws ManageTokenException;

    RolAccountDto getRolAccountByIdOrException(Long id) throws SchedulePilotException;

    String getParameterValueNull(String keyParameter);

    String getParameterValueEmpty(String keyParameter);

    String getParameterValueThrow(String keyParameter) throws SchedulePilotException;

    Long getParameterValueAsLongOrException(String keyParameter) throws ManageParameterException;

    ProductRequestStatusEntity getProductRequestStatusOrException(String status) throws SchedulePilotException;
}
