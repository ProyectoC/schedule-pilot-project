package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.ItemStatusDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.entities.model.ItemStatusEntity;
import com.schedulepilot.core.request.ProductCreateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemStatusService {

    ModelMapper modelMapper = new ModelMapper();

    static ItemStatusDto convertEntityToDTO(ItemStatusEntity dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ItemStatusDto.class);
    }

    List<ItemStatusEntity> getAll();

    List<ItemStatusDto> getAllDto();
}
