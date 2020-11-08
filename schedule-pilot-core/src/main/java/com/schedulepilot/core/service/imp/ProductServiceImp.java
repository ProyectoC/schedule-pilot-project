package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.entities.model.ProductEntity_;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.ProductRepository;
import com.schedulepilot.core.service.ProductService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductServiceImp implements ProductService {

    private static final List<String> LIST_ATTRIBUTES = Arrays.asList(ProductEntity_.name.getName());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
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
    public ProductEntity getByIdOrException(Long id) throws SchedulePilotException {
        Optional<ProductEntity> entity = productRepository.findById(id);
        if (!entity.isPresent()) {
            throw new SchedulePilotException("Product Not Found");
        }
        return entity.get();
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        productEntity.setIsActive(true);
        return this.productRepository.save(productEntity);
    }

    @Override
    public ProductEntity update(ProductEntity productEntity) {
        return this.productRepository.save(productEntity);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        return ProductService.convertEntityToDTO(this.productRepository.save(ProductService.convertDTOToEntity(productDto)));
    }
}
