package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.ItemConstants;
import com.schedulepilot.core.dto.model.ItemStatusDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.ItemStatusService;
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
@RequestMapping(value = ItemConstants.REST_PATH_DEFAULT_V1)
public class ItemStatusController {

    @Autowired
    private ItemStatusService itemStatusService;

    @ResponseBody
    @GetMapping(ItemConstants.STATUS_ITEM_REST)
    public ResponseEntity<ResponseDto<List<ItemStatusDto>>> getAllItemsStatus() throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.itemStatusService.getAllDto()), HttpStatus.OK);
    }
}
