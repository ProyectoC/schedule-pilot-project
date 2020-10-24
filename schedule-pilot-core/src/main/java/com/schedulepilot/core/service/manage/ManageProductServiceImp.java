package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.ManageProductException;
import com.schedulepilot.core.exception.ManageUserException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ProductCreateRequest;
import com.schedulepilot.core.request.ProductDeleteRequest;
import com.schedulepilot.core.request.ProductUpdateRequest;
import com.schedulepilot.core.service.ProductService;
import com.schedulepilot.core.util.dto.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

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
        ProductDto productDto = ProductService.convertRequestCreateToDTO(productCreateRequest);
        productDto = this.productService.save(productDto);
        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductUpdateRequest productCreateRequest) throws SchedulePilotException {
        ProductDto productDto = ProductService.convertRequestUpdateToDTO(productCreateRequest);
        // TODO Validate that product exists.
        productDto = this.productService.save(productDto);
        return productDto;
    }

    @Override
    public void deleteProduct(ProductDeleteRequest productDeleteRequest) throws SchedulePilotException {
        ProductDto productDto = this.productService.getByIdThrow(productDeleteRequest.getId());
        Validator validator = productDto.validationForDisableProduct();
        if (!validator.isValid())
            throw new ManageProductException(ExceptionCode.ERROR_MANAGE_PRODUCT_DISABLE_FAILED, validator.getFirstError());
        productDto.setIsActive(false);
        this.productService.update(productDto);
    }
}
