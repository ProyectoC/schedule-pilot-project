package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.TicketCheckStatusDto;
import com.schedulepilot.core.entities.model.TicketCheckStatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketCheckStatusService {

    ModelMapper modelMapper = new ModelMapper();

    static TicketCheckStatusEntity convertDTOToEntity(TicketCheckStatusDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        TicketCheckStatusEntity entity = modelMapper.map(dto, TicketCheckStatusEntity.class);
        return entity;
    }

    static TicketCheckStatusDto convertEntityToDTO(TicketCheckStatusEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (entity != null) {
            TicketCheckStatusDto dto = modelMapper.map(entity, TicketCheckStatusDto.class);
            return dto;
        } else {
            return null;
        }
    }

    List<TicketCheckStatusEntity> getAll();

    List<TicketCheckStatusDto> getAllDto();
}
