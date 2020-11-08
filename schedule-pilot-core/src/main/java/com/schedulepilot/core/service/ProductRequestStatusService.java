package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductRequestStatusService {

    List<ProductRequestStatusEntity> getAll();
}
