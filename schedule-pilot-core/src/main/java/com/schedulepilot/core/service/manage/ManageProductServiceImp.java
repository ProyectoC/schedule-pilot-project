package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.dto.model.ProductRolDto;
import com.schedulepilot.core.entities.id.ProductRolId;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.entities.model.ProductRolEntity;
import com.schedulepilot.core.entities.model.RolAccountEntity;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.ManageProductException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.ProductRolRepository;
import com.schedulepilot.core.request.ProductCreateRequest;
import com.schedulepilot.core.request.ProductDeleteRequest;
import com.schedulepilot.core.request.ProductUpdateRequest;
import com.schedulepilot.core.service.ProductService;
import com.schedulepilot.core.service.RolAccountService;
import com.schedulepilot.core.util.dto.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ManageProductServiceImp implements ManageProductService {

    @Autowired
    private ProductService productService;

    @Autowired
    private RolAccountService rolAccountService;

    @Autowired
    private ProductRolRepository productRolRepository;

    @Override
    public PageResponseDto<ProductDto> getAllProducts(Map<String, String> parameters) throws SchedulePilotException {
        return this.productService.getAll(parameters);
    }

    @Override
    public void createProduct(ProductCreateRequest productCreateRequest) throws SchedulePilotException {
        ProductDto productDto = ProductService.convertRequestCreateToDTO(productCreateRequest);
        Validator validator = productDto.validationForCreateProduct();
        if (!validator.isValid())
            throw new ManageProductException(ExceptionCode.ERROR_MANAGE_PRODUCT_CREATE_FAILED, validator.getFirstError());
        ProductEntity productEntity = ProductService.convertDTOToEntity(productDto);
        productEntity.setProductRolEntityList(new ArrayList<>());
        this.setProductRoles(productEntity, productDto.getProductRoles());
        this.productService.save(productEntity);
    }

    @Override
    public void updateProduct(ProductUpdateRequest productUpdateRequest) throws SchedulePilotException {
        ProductDto productDto = ProductService.convertRequestUpdateToDTO(productUpdateRequest);
        Validator validator = productDto.validationForCreateProduct();
        if (!validator.isValid())
            throw new ManageProductException(ExceptionCode.ERROR_MANAGE_PRODUCT_UPDATE_FAILED, validator.getFirstError());
        ProductEntity productEntityNew = ProductService.convertDTOToEntity(productDto);
        ProductEntity productEntityActual = this.productService.getByIdOrException(productUpdateRequest.getId());
        productEntityNew.setProductRolEntityList(new ArrayList<>());
        productEntityNew.setCreatedBy(productEntityActual.getCreatedBy());
        productEntityNew.setCreatedDate(productEntityActual.getLastModifiedDate());
        this.setProductRoles(productEntityNew, productDto.getProductRoles());
        this.productService.update(productEntityNew);
    }

    @Override
    public void deleteProduct(ProductDeleteRequest productDeleteRequest) throws SchedulePilotException {
        ProductEntity productEntity = this.productService.getByIdOrException(productDeleteRequest.getId());
        ProductDto productDto = ProductService.convertEntityToDTO(productEntity);
        Validator validator = productDto.validationForDisableProduct();
        if (!validator.isValid())
            throw new ManageProductException(ExceptionCode.ERROR_MANAGE_PRODUCT_DISABLE_FAILED, validator.getFirstError());
        productEntity.setIsActive(false);
        this.productService.update(productEntity);
    }

    private void setProductRoles(ProductEntity productEntity, List<ProductRolDto> productRolDtoList) throws SchedulePilotException {
        for (ProductRolDto productRolDto : productRolDtoList) {
            RolAccountEntity rolAccount = this.rolAccountService.getByIdEntityOrException(productRolDto.getRol());
            if (productEntity.getId() != null) {
                Optional<ProductRolEntity> productRolEntityOptional = this.productRolRepository
                        .findByProductAndRol(productEntity.getId(), rolAccount.getId());
                if (productRolEntityOptional.isPresent()) {
                    ProductRolEntity productRolEntity = productRolEntityOptional.get();
                    productRolEntity.setLoanTime(productRolDto.getLoanTime());
                    productEntity.addProductRole(productRolEntity);
                    continue;
                }
            }
            ProductRolId productRolId = new ProductRolId(rolAccount);
            ProductRolEntity productRolEntity = new ProductRolEntity(productRolId, productRolDto.getLoanTime());
            productEntity.addProductRole(productRolEntity);
        }
    }
}
