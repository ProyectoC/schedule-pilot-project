package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.DashboardConstants;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.dashboards.GeneralChart;
import com.schedulepilot.core.service.manage.ManageDashboardService;
import com.schedulepilot.core.util.SecurityUtil;
import com.schedulepilot.core.util.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = DashboardConstants.REST_PATH_DEFAULT_V1)
@AllArgsConstructor
public class DashboardController {

    private final ManageDashboardService manageDashboardService;

    @GetMapping(DashboardConstants.STATUS_OPERATIONS_REST)
    public ResponseEntity<ResponseDto<GeneralChart>> getStatusOperations(@RequestHeader(SecurityUtil.USER_NAME_ID_KEY) Long userAccountId) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageDashboardService.getDashboardStatusOperation(userAccountId)), HttpStatus.OK);
    }

    @GetMapping(DashboardConstants.STATUS_LOAN_MADE_REST)
    public ResponseEntity<ResponseDto<GeneralChart>> getStatusLoanMade(@RequestHeader(SecurityUtil.USER_NAME_ID_KEY) Long userAccountId) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageDashboardService.getDashboardStatusLoanMade(userAccountId)), HttpStatus.OK);
    }

    @GetMapping(DashboardConstants.STATUS_RETURN_MADE_REST)
    public ResponseEntity<ResponseDto<GeneralChart>> getStatusReturnMade(@RequestHeader(SecurityUtil.USER_NAME_ID_KEY) Long userAccountId) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageDashboardService.getDashboardStatusReturnMade(userAccountId)), HttpStatus.OK);
    }

    @GetMapping(DashboardConstants.PRINCIPAL_REST)
    public ResponseEntity<ResponseDto<GeneralChart>> getPrincipal(@RequestHeader(SecurityUtil.USER_NAME_ID_KEY) Long userAccountId) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageDashboardService.getDashboardPrincipal(userAccountId)), HttpStatus.OK);
    }

}
