package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.UserAccountCreateRequest;
import com.schedulepilot.core.util.dto.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserAccountService extends UserDetailsService {

    ModelMapper modelMapper = new ModelMapper();

    static UserAccountDto convertRequestToDTO(UserAccountCreateRequest dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, UserAccountDto.class);
    }

    static UserAccountEntity convertDTOToEntity(UserAccountDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, UserAccountEntity.class);
    }

    static UserAccountDto convertEntityToDTO(UserAccountEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(entity, UserAccountDto.class);
    }

    UserAccountDto getByIdNull(Long id);

    UserAccountDto getByIdThrow(Long id) throws SchedulePilotException;

    UserAccountEntity getByIdOrException(Long id) throws SchedulePilotException;

    UserAccountDto getByUsername(String username);

    UserAccountDto getByUsernameOrException(String username) throws SchedulePilotException;

    UserAccountDto getByUsernameOrNull(String username);

    UserAccountDto getByIdentification(String identification);

    UserAccountDto getByIdentificationCode(String identificationCode);

    UserAccountDto getByEmailOrEmailBackup(String email);

    UserDetails loadUserById(Long id);

    UserAccountDto save(UserAccountDto userAccountDto);

    UserAccountDto update(UserAccountDto userAccountDto);

    Validator validationBeforeSave(UserAccountDto userAccountDto);
}
