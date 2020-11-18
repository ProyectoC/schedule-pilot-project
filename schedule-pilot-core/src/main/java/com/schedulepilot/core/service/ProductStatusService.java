package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.ProductStatusDto;
import com.schedulepilot.core.entities.model.ProductStatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductStatusService {

    ModelMapper modelMapper = new ModelMapper();

    static ProductStatusEntity convertDTOToEntity(ProductStatusDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        ProductStatusEntity entity = modelMapper.map(dto, ProductStatusEntity.class);
        return entity;
    }

    static ProductStatusDto convertEntityToDTO(ProductStatusEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (entity != null) {
            ProductStatusDto dto = modelMapper.map(entity, ProductStatusDto.class);
            return dto;
        } else {
            return null;
        }
    }

    List<ProductStatusDto> getAll();
}
