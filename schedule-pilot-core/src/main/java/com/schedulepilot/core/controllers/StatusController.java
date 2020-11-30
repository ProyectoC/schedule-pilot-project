package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.StatusConstants;
import com.schedulepilot.core.dto.model.ProductRequestStatusDto;
import com.schedulepilot.core.dto.model.TicketCheckStatusDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.ProductRequestStatusService;
import com.schedulepilot.core.service.TicketCheckStatusService;
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
@RequestMapping(value = StatusConstants.REST_PATH_DEFAULT_V1)
public class StatusController {

    @Autowired
    private ProductRequestStatusService productRequestStatusService;

    @Autowired
    private TicketCheckStatusService ticketCheckStatusService;

    @ResponseBody
    @GetMapping(StatusConstants.GET_REQUEST_CHECK_IN_REST)
    public ResponseEntity<ResponseDto<List<ProductRequestStatusDto>>> getAllProductRequestStatus() throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.productRequestStatusService.getAllDto()), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(StatusConstants.GET_TICKET_CHECK_IN_REST)
    public ResponseEntity<ResponseDto<List<TicketCheckStatusDto>>> getAllTicketStatus() throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.ticketCheckStatusService.getAllDto()), HttpStatus.OK);
    }
}
