package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.request.ProductCreateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProductService {

    ModelMapper modelMapper = new ModelMapper();

    static ProductDto convertRequestToDTO(ProductCreateRequest dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ProductDto.class);
    }

    static ProductEntity convertDTOToEntity(ProductDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ProductEntity.class);
    }

    static ProductDto convertEntityToDTO(ProductEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(entity, ProductDto.class);
    }

    PageResponseDto<ProductDto> getAll(Map<String, String> parameters);

    ProductDto save(ProductDto productDto);
}
