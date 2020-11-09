package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.LoanProcessConstants;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.CheckInRequest;
import com.schedulepilot.core.request.CheckOutRequest;
import com.schedulepilot.core.service.loanprocess.LoanProcessService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

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
    @PostMapping(LoanProcessConstants.CREATE_REQUEST_CHECK_OUT_REST)
    public ResponseEntity<ResponseDto<String>> createRequestCheckOut(@RequestBody @Valid CheckOutRequest checkOutRequest) throws SchedulePilotException {
        String trackId = this.loanProcessService.createRequestCheckOut(checkOutRequest);
        return new ResponseEntity<>(ResponseDto.success("Request CheckOut created successfully. See: " + trackId), HttpStatus.CREATED);
    }
}
