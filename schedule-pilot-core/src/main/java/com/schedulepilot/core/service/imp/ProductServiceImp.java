package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.entities.model.ProductEntity_;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.ManageUserException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.ProductRepository;
import com.schedulepilot.core.service.ProductService;
import com.schedulepilot.core.service.UserAccountService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

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
            Page<ProductEntity> page = this.productRepository.findAllWithPage(paginationAndOrderTask.getPageData());
            page.getContent().forEach(e -> list.add(ProductService.convertEntityToDTO(e)));
            pageResponse.build(list, page);
        } else {
            List<ProductEntity> productEntities = this.productRepository.findAllWithSort(paginationAndOrderTask.getSortData());
            productEntities.forEach(e -> list.add(ProductService.convertEntityToDTO(e)));
            pageResponse.build(list);
        }
        return pageResponse;
    }

    @Override
    public ProductDto getByIdThrow(Long id) throws SchedulePilotException {
        Optional<ProductEntity> entity = productRepository.findById(id);
        return entity.map(ProductService::convertEntityToDTO).orElseThrow(() -> new SchedulePilotException("Product Not Found"));
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        productDto.setIsActive(true);
        return ProductService.convertEntityToDTO(this.productRepository.saveAndFlush(ProductService.convertDTOToEntity(productDto)));
    }

    @Override
    @Transactional
    public ProductDto update(ProductDto productDto) {
        return ProductService.convertEntityToDTO(this.productRepository.saveAndFlush(ProductService.convertDTOToEntity(productDto)));
    }
}
