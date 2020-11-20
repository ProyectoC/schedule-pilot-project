package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.LoanProcessConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.CheckInRequest;
import com.schedulepilot.core.request.CheckLogRequest;
import com.schedulepilot.core.request.CheckOutRequest;
import com.schedulepilot.core.response.RequestCheckInResponse;
import com.schedulepilot.core.response.TicketCheckInResponse;
import com.schedulepilot.core.service.loanprocess.LoanProcessService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = LoanProcessConstants.REST_PATH_DEFAULT_V1)
public class LoanProcessController {

    @Autowired
    private LoanProcessService loanProcessService;

    @ResponseBody
    @PostMapping(LoanProcessConstants.CREATE_REQUEST_CHECK_IN_REST)
    public ResponseEntity<ResponseDto<String>> createRequestCheckIn(@RequestBody @Valid CheckInRequest checkInRequest) throws SchedulePilotException {
        String trackId = this.loanProcessService.createRequestCheckIn(checkInRequest);
        return new ResponseEntity<>(ResponseDto.success("Request CheckIn created successfully. See: " + trackId), HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(LoanProcessConstants.GET_REQUEST_CHECK_IN_REST)
    public ResponseEntity<ResponseDto<PageResponseDto<RequestCheckInResponse>>> getAllRequestCheckIn(
            @PathVariable Long userAccountId,
            @RequestParam Map<String, String> parameters) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.loanProcessService.getRequestCheckIn(parameters, userAccountId)), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(LoanProcessConstants.GET_REQUEST_TICKET_CHECK_IN_REST)
    public ResponseEntity<ResponseDto<PageResponseDto<TicketCheckInResponse>>> getAllTicketCheckIn(
            @PathVariable Long userAccountId,
            @RequestParam Map<String, String> parameters) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.loanProcessService.getAllTicketCheckIn(parameters, userAccountId)), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(LoanProcessConstants.CREATE_REQUEST_CHECK_OUT_REST)
    public ResponseEntity<ResponseDto<String>> createRequestCheckOut(@RequestBody @Valid CheckOutRequest checkOutRequest) throws SchedulePilotException {
        String trackId = this.loanProcessService.createRequestCheckOut(checkOutRequest);
        return new ResponseEntity<>(ResponseDto.success("Request CheckOut created successfully. See: " + trackId), HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping(LoanProcessConstants.CREATE_REQUEST_CHECK_LOG_REST)
    public ResponseEntity<ResponseDto<String>> createRequestCheckLog(@RequestBody @Valid CheckLogRequest checkLogRequest) throws SchedulePilotException {
        String trackId = this.loanProcessService.createRequestCheckLog(checkLogRequest);
        return new ResponseEntity<>(ResponseDto.success("Request CheckLog created successfully. See: " + trackId), HttpStatus.CREATED);
    }
}
