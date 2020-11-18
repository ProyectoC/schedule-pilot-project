package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.entities.model.RolAccountEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolAccountService {

    ModelMapper modelMapper = new ModelMapper();

    static RolAccountEntity convertDTOToEntity(RolAccountDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        RolAccountEntity entity = modelMapper.map(dto, RolAccountEntity.class);
        return entity;
    }

    static RolAccountDto convertEntityToDTO(RolAccountEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (entity != null) {
            RolAccountDto dto = modelMapper.map(entity, RolAccountDto.class);
            return dto;
        } else {
            return null;
        }
    }

    List<RolAccountDto> getAll();

    List<RolAccountDto> getAllWithoutSuperAdmin();

    RolAccountDto getByIdOrException(Long id) throws SchedulePilotException;

    RolAccountEntity getByIdEntityOrException(Long id) throws SchedulePilotException;
}
