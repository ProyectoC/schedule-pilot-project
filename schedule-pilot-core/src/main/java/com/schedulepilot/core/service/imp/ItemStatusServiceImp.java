package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.ItemStatusDto;
import com.schedulepilot.core.dto.model.ProductStatusDto;
import com.schedulepilot.core.entities.model.ItemStatusEntity;
import com.schedulepilot.core.repository.ItemStatusServiceRepository;
import com.schedulepilot.core.service.ItemStatusService;
import com.schedulepilot.core.service.ProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemStatusServiceImp implements ItemStatusService {

    @Autowired
    private ItemStatusServiceRepository itemStatusServiceRepository;

    @Override
    public List<ItemStatusEntity> getAll() {
        return itemStatusServiceRepository.findAll();
    }

    @Override
    public List<ItemStatusDto> getAllDto() {
        List<ItemStatusDto> list = new ArrayList<>();
        this.itemStatusServiceRepository.findAll().forEach(e -> list.add(ItemStatusService.convertEntityToDTO(e)));
        return list;
    }
}
