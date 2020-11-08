package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.ItemConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ItemCreateRequest;
import com.schedulepilot.core.request.ItemUpdateRequest;
import com.schedulepilot.core.service.manage.ManageItemService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = ItemConstants.REST_PATH_DEFAULT_V1)
public class ItemController {

    @Autowired
    private ManageItemService manageItemService;

    @ResponseBody
    @GetMapping()
    public ResponseEntity<ResponseDto<PageResponseDto<ItemDto>>> getAllItems(@RequestParam Map<String, String> parameters) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageItemService.getAllItems(parameters)), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(ItemConstants.CREATE_ITEM_REST)
    public ResponseEntity<ResponseDto<String>> createItem(@RequestBody @Valid ItemCreateRequest itemCreateRequest) throws SchedulePilotException {
        this.manageItemService.createItem(itemCreateRequest);
        return new ResponseEntity<>(ResponseDto.success("Item created successfully."), HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping(ItemConstants.UPDATE_ITEM_REST)
    public ResponseEntity<ResponseDto<String>> updateItem(@RequestBody @Valid ItemUpdateRequest itemUpdateRequest) throws SchedulePilotException {
        this.manageItemService.updateItem(itemUpdateRequest);
        return new ResponseEntity<>(ResponseDto.success("Item updated successfully."), HttpStatus.OK);
    }
}
