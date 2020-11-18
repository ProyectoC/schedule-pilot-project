package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.CollegeCareerDto;
import com.schedulepilot.core.entities.model.CollegeCareerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CollegeCareerService {

    ModelMapper modelMapper = new ModelMapper();

    static CollegeCareerEntity convertDTOToEntity(CollegeCareerDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        CollegeCareerEntity entity = modelMapper.map(dto, CollegeCareerEntity.class);
        return entity;
    }

    static CollegeCareerDto convertEntityToDTO(CollegeCareerEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (entity != null) {
            CollegeCareerDto dto = modelMapper.map(entity, CollegeCareerDto.class);
            return dto;
        } else {
            return null;
        }
    }

    List<CollegeCareerDto> getAll();
}
