package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ProductCreateRequest;
import com.schedulepilot.core.request.ProductDeleteRequest;
import com.schedulepilot.core.request.ProductUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ManageProductService {

    Logger LOGGER = LoggerFactory.getLogger(ManageProductService.class);

    PageResponseDto<ProductDto> getAllProducts(Map<String, String> parameters) throws SchedulePilotException;

    ProductDto createProduct(ProductCreateRequest productCreateRequest) throws SchedulePilotException;

    ProductDto updateProduct(ProductUpdateRequest productCreateRequest) throws SchedulePilotException;

    void deleteProduct(ProductDeleteRequest productDeleteRequest) throws SchedulePilotException;
}
