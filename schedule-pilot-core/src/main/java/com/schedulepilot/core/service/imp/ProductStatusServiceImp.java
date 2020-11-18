package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.ProductStatusDto;
import com.schedulepilot.core.repository.ProductStatusRepository;
import com.schedulepilot.core.service.ProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductStatusServiceImp implements ProductStatusService {

    @Autowired
    private ProductStatusRepository productStatusRepository;

    @Override
    public List<ProductStatusDto> getAll() {
        List<ProductStatusDto> list = new ArrayList<>();
        this.productStatusRepository.findAll().forEach(e -> list.add(ProductStatusService.convertEntityToDTO(e)));
        return list;
    }
}
