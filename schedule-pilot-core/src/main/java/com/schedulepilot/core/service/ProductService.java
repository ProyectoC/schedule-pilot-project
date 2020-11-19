package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.dto.model.ProductRolDto;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.entities.model.ProductRolEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ProductCreateRequest;
import com.schedulepilot.core.request.ProductUpdateRequest;
import com.schedulepilot.core.response.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        ProductDto productDto = modelMapper.map(entity, ProductDto.class);

        productDto.setItemsCount(entity.getItemEntityList().size());

        List<ProductRolDto> productRoles = new ArrayList<>();
        List<ProductRolEntity> productRolEntities = entity.getProductRolEntityList();
        for (ProductRolEntity productRolEntity : productRolEntities) {
            ProductRolDto productRolDto = new ProductRolDto();
            productRolDto.setLoanTime(productRolEntity.getLoanTime());
            productRolDto.setRol(productRolEntity.getProductRolId().getRolAccountEntity().getId());
            productRoles.add(productRolDto);
        }
        productDto.setProductRoles(productRoles);
        return productDto;
    }

    static ProductResponse convertDTOToResponse(ProductDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ProductResponse.class);
    }

    PageResponseDto<ProductDto> getAll(Map<String, String> parameters);

    ProductEntity getByIdOrException(Long id) throws SchedulePilotException;

    ProductEntity save(ProductEntity productEntity);

    ProductEntity update(ProductEntity productEntity);

    ProductDto update(ProductDto productDto);

}
