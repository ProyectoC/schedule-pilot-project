package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ItemStatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemStatusService {

    List<ItemStatusEntity> getAll();
}
