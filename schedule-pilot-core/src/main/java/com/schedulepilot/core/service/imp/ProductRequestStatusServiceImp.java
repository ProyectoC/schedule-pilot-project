package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.ProductRequestStatusDto;
import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import com.schedulepilot.core.repository.ProductRequestStatusRepository;
import com.schedulepilot.core.service.ProductRequestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRequestStatusServiceImp implements ProductRequestStatusService {

    @Autowired
    private ProductRequestStatusRepository productRequestStatusRepository;

    @Override
    public List<ProductRequestStatusEntity> getAll() {
        return productRequestStatusRepository.findAll();
    }

    @Override
    public List<ProductRequestStatusDto> getAllDto() {
        List<ProductRequestStatusDto> list = new ArrayList<>();
        this.productRequestStatusRepository.findAll().forEach(e -> list.add(ProductRequestStatusService.convertEntityToDTO(e)));
        return list;
    }
}
