package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ProductCreateRequest;
import com.schedulepilot.core.service.manage.ManageProductService;
import com.schedulepilot.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ManageProductServiceImp implements ManageProductService {

    @Autowired
    private ProductService productService;

    @Override
    public PageResponseDto<ProductDto> getAllProducts(Map<String, String> parameters) throws SchedulePilotException {
        return this.productService.getAll(parameters);
    }

    @Override
    public ProductDto createProduct(ProductCreateRequest productCreateRequest) throws SchedulePilotException {
        ProductDto productDto = ProductService.convertRequestToDTO(productCreateRequest);
        productDto = this.productService.save(productDto);
        return productDto;
    }
}
