package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.TokenTypeDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface GlobalListDinamicService {

    TokenTypeDto getTokenTypeByNameThrow(String tokenTypeName) throws SchedulePilotException;

    RolAccountDto getRolAccountByIdThrow(Long id) throws SchedulePilotException;

    String getParameterValueNull(String keyParameter);

    String getParameterValueEmpty(String keyParameter);

    String getParameterValueThrow(String keyParameter) throws SchedulePilotException;
}
