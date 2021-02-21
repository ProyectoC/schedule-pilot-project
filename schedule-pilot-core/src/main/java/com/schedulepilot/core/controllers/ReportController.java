package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.ReportConstants;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.report.LoanProductResponse;
import com.schedulepilot.core.response.report.PenaltySummaryResponse;
import com.schedulepilot.core.service.manage.ManageReportService;
import com.schedulepilot.core.util.SecurityUtil;
import com.schedulepilot.core.util.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = ReportConstants.REST_PATH_DEFAULT_V1)
@AllArgsConstructor
public class ReportController {

    private final ManageReportService manageReportService;

    @GetMapping(ReportConstants.REPORT_LOAN_PRODUCTS_REST)
    public ResponseEntity<ResponseDto<List<LoanProductResponse>>> getReportLoanProduct(@RequestParam Map<String, String> parameters, @RequestHeader(SecurityUtil.USER_NAME_ID_KEY) Long userAccountId) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageReportService.getReportLoanProduct(parameters, userAccountId)), HttpStatus.OK);
    }

    @GetMapping(ReportConstants.REPORT_PENALTY_SUMMARY_REST)
    public ResponseEntity<ResponseDto<List<PenaltySummaryResponse>>> getReportPenaltySummary(@RequestParam Map<String, String> parameters, @RequestHeader(SecurityUtil.USER_NAME_ID_KEY) Long userAccountId) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageReportService.getReportPenaltySummary(parameters, userAccountId)), HttpStatus.OK);
    }
}
