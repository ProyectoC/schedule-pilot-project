package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.entities.model.ItemEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ItemCreateRequest;
import com.schedulepilot.core.request.ItemUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ItemService {

    ModelMapper modelMapper = new ModelMapper();

    static ItemDto convertEntityToDTO(ItemEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(entity, ItemDto.class);
    }

    static ItemDto convertRequestCreateToDTO(ItemCreateRequest dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ItemDto.class);
    }

    static ItemDto convertRequestUpdateToDTO(ItemUpdateRequest dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ItemDto.class);
    }

    static ItemEntity convertDTOToEntity(ItemDto dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, ItemEntity.class);
    }

    PageResponseDto<ItemDto> getAll(Map<String, String> parameters);

    ItemEntity getByIdOrException(Long id) throws SchedulePilotException;

    List<ItemEntity> getItemsEnable(Long productId) throws SchedulePilotException;

    ItemEntity save(ItemEntity itemEntity);

    ItemEntity setOnLoan(ItemEntity itemEntity) throws SchedulePilotException;

    ItemEntity setEnable(ItemEntity itemEntity) throws SchedulePilotException;

    ItemEntity update(ItemEntity itemEntity);
}
