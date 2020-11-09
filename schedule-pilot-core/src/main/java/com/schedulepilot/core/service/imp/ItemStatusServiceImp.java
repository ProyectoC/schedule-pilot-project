package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.ItemStatusEntity;
import com.schedulepilot.core.repository.ItemStatusServiceRepository;
import com.schedulepilot.core.service.ItemStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemStatusServiceImp implements ItemStatusService {

    @Autowired
    private ItemStatusServiceRepository itemStatusServiceRepository;

    @Override
    public List<ItemStatusEntity> getAll() {
        return itemStatusServiceRepository.findAll();
    }
}
