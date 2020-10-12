package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.ProductConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ProductDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ProductCreateRequest;
import com.schedulepilot.core.service.manage.ManageProductService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = ProductConstants.REST_PATH_DEFAULT_V1)
public class ProductController {

    @Autowired
    private ManageProductService manageProductService;

    @ResponseBody
    @GetMapping()
    public ResponseEntity<ResponseDto<PageResponseDto<ProductDto>>> getAllProducts(@RequestParam Map<String, String> parameters) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageProductService.getAllProducts(parameters)), HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping(ProductConstants.CREATE_PRODUCT_REST)
    public ResponseEntity<ResponseDto<ProductDto>> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageProductService.createProduct(productCreateRequest)), HttpStatus.CREATED);
    }
}