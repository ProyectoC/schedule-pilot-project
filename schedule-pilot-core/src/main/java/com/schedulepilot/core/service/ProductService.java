package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ProductCreateRequest;
import com.schedulepilot.core.request.ProductUpdateRequest;
import com.schedulepilot.core.response.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProductService {

    ModelMapper modelMapper = new ModelMapper();

    static ProductDto convertRequestCreateToDTO(ProductCreateRequest dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ProductDto.class);
    }

    static ProductDto convertRequestUpdateToDTO(ProductUpdateRequest dto) {
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

    static ProductResponse convertDTOToResponse(ProductDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ProductResponse.class);
    }

    PageResponseDto<ProductDto> getAll(Map<String, String> parameters);

    ProductDto getByIdThrow(Long id) throws SchedulePilotException;

    ProductDto save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

}
