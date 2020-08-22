package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.ParameterDto;
import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.dto.model.TokenTypeDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.GlobalListDinamicService;
import com.schedulepilot.core.util.dto.GlobalListDinamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class GlobalListDinamicServiceImp implements GlobalListDinamicService {

    @Autowired
    private GlobalListDinamic<TokenTypeDto> globalTokenTypesList;

    @Autowired
    private GlobalListDinamic<ParameterDto> globalParameterList;

    @Autowired
    private GlobalListDinamic<RolAccountDto> globalRolAccountList;

    @Override
    @Transactional
    public TokenTypeDto getTokenTypeByNameThrow(String tokenTypeName) throws SchedulePilotException {
        Optional<TokenTypeDto> optionalTokenTypeDto = this.globalTokenTypesList.getItems().stream().filter(parameter ->
                parameter.getName().equals(tokenTypeName)).findFirst();
        if (optionalTokenTypeDto.isPresent()) {
            return optionalTokenTypeDto.get();
        }
        throw new SchedulePilotException("Token Type not found.");
    }

    @Override
    @Transactional
    public RolAccountDto getRolAccountByIdThrow(Long id) throws SchedulePilotException {
        Optional<RolAccountDto> optional = this.globalRolAccountList.getItems().stream().filter(parameter ->
                parameter.getId().equals(id)).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new SchedulePilotException("Rol Account not found.");
    }

    @Override
    @Transactional
    public String getParameterValueNull(String keyParameter) {
        Optional<ParameterDto> parameterSecurityDto = this.globalParameterList.getItems().stream().filter(parameter ->
                parameter.getName().equals(keyParameter)).findFirst();
        return parameterSecurityDto.map(ParameterDto::getValue).orElse(null);
    }

    @Override
    @Transactional
    public String getParameterValueEmpty(String keyParameter) {
        Optional<ParameterDto> parameterSecurityDto = this.globalParameterList.getItems().stream().filter(parameter ->
                parameter.getName().equals(keyParameter)).findFirst();
        return parameterSecurityDto.map(ParameterDto::getValue).orElse("");
    }

    @Override
    @Transactional
    public String getParameterValueThrow(String keyParameter) throws SchedulePilotException {
        Optional<ParameterDto> parameterSecurityDto = this.globalParameterList.getItems().stream().filter(parameter ->
                parameter.getName().equals(keyParameter)).findFirst();
        return parameterSecurityDto.map(ParameterDto::getValue).orElseThrow(() ->
                new SchedulePilotException("Parameter with key: " + keyParameter + " not found."));
    }
}
