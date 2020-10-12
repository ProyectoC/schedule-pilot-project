package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.entities.model.ProductEntity_;
import com.schedulepilot.core.repository.ProductRepository;
import com.schedulepilot.core.service.ProductService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ProductServiceImp implements ProductService {

    private static final List<String> LIST_ATTRIBUTES = Arrays.asList(ProductEntity_.name.getName(), ProductEntity_.serial1.getName());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Transactional
    public PageResponseDto<ProductDto> getAll(Map<String, String> parameters) {

        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, LIST_ATTRIBUTES);
        paginationAndOrderTask.execute();

        String propertyName = parameters.getOrDefault("name", "");
        PageResponseDto<ProductDto> pageResponse = new PageResponseDto<>();

        List<ProductDto> list = new ArrayList<>();
        if (paginationAndOrderTask.getPageData() != null) {
            Page<ProductEntity> page = this.productRepository.findAll(paginationAndOrderTask.getPageData());
            page.getContent().forEach(e -> list.add(ProductService.convertEntityToDTO(e)));
            pageResponse.build(list, page);
        } else {
            List<ProductEntity> productEntities = this.productRepository.findAll(paginationAndOrderTask.getSortData());
            productEntities.forEach(e -> list.add(ProductService.convertEntityToDTO(e)));
            pageResponse.build(list);
        }
        return pageResponse;
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        productDto.setIsActive(true);
        return ProductService.convertEntityToDTO(this.productRepository.saveAndFlush(ProductService.convertDTOToEntity(productDto)));
    }
}
