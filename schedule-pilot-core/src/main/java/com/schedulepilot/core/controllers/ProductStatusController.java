package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.ProductConstants;
import com.schedulepilot.core.dto.model.ProductStatusDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.ProductStatusService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = ProductConstants.REST_PATH_DEFAULT_V1)
public class ProductStatusController {

    @Autowired
    private ProductStatusService productStatusService;

    @ResponseBody
    @GetMapping(ProductConstants.STATUS_PRODUCT_REST)
    public ResponseEntity<ResponseDto<List<ProductStatusDto>>> getAllProductStatus() throws SchedulePilotException {
        return new ResponseEntity<ResponseDto<List<ProductStatusDto>>>(ResponseDto.success(this.productStatusService.getAll()), HttpStatus.OK);
    }
}
