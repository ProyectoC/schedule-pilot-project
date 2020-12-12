package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.TokenTypeDto;
import com.schedulepilot.core.entities.model.ItemStatusEntity;
import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import com.schedulepilot.core.entities.model.TicketCheckStatusEntity;
import com.schedulepilot.core.exception.ManageParameterException;
import com.schedulepilot.core.exception.ManageTokenException;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface GlobalListDynamicService {

    TokenTypeDto getTokenTypeByNameOrException(String tokenTypeName) throws ManageTokenException;

    RolAccountDto getRolAccountByIdOrException(Long id) throws SchedulePilotException;

    String getParameterValueNull(String keyParameter);

    String getParameterValueEmpty(String keyParameter);

    String getParameterValueThrow(String keyParameter) throws SchedulePilotException;

    Long getParameterValueAsLongOrException(String keyParameter) throws ManageParameterException;

    ProductRequestStatusEntity getProductRequestStatusOrException(String status) throws SchedulePilotException;

    TicketCheckStatusEntity getTicketCheckStatusOrException(String status) throws SchedulePilotException;

    ItemStatusEntity getItemStatusOrException(String status) throws SchedulePilotException;
}
