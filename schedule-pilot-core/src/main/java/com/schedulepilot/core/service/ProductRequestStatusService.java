package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.ProductRequestStatusDto;
import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductRequestStatusService {

    ModelMapper modelMapper = new ModelMapper();

    static ProductRequestStatusEntity convertDTOToEntity(ProductRequestStatusDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        ProductRequestStatusEntity entity = modelMapper.map(dto, ProductRequestStatusEntity.class);
        return entity;
    }

    static ProductRequestStatusDto convertEntityToDTO(ProductRequestStatusEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (entity != null) {
            ProductRequestStatusDto dto = modelMapper.map(entity, ProductRequestStatusDto.class);
            return dto;
        } else {
            return null;
        }
    }

    List<ProductRequestStatusEntity> getAll();

    List<ProductRequestStatusDto> getAllDto();
}
