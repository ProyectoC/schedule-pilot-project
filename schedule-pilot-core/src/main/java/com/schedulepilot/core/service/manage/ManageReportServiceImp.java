package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.report.LoanProductResponse;
import com.schedulepilot.core.response.report.PenaltySummaryResponse;
import com.schedulepilot.core.service.PenaltyCheckOutService;
import com.schedulepilot.core.service.UserAccountService;
import com.schedulepilot.core.service.ViewChartLoanProductService;
import com.schedulepilot.core.service.modelmapper.ReportMapperService;
import com.schedulepilot.core.util.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ManageReportServiceImp implements ManageReportService {

    private final ViewChartLoanProductService viewChartLoanProductService;
    private final PenaltyCheckOutService penaltyCheckOutService;

    private final ReportMapperService reportMapperService;
    private final UserAccountService userAccountService;

    @Override
    public List<LoanProductResponse> getReportLoanProduct(Map<String, String> parameters, Long userAccountId) {
        LocalDateTime dateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("date_start", null));
        LocalDateTime dateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("date_end", null));
        List<LoanProductResponse> list = new ArrayList<>();
        if (this.userAccountService.isAdminUser(userAccountId)) {
            this.viewChartLoanProductService.getLoanProduct(dateStart, dateEnd)
                    .forEach(e -> list.add(reportMapperService.convertViewChartLoanProductToResponse(e)));
        } else {
            this.viewChartLoanProductService.getLoanProductByUserAccount(userAccountId, dateStart, dateEnd)
                    .forEach(e -> list.add(reportMapperService.convertViewChartLoanProductToResponse(e)));
        }

        return list;
    }

    @Override
    public List<PenaltySummaryResponse> getReportPenaltySummary(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        Date dateStart = CommonUtil.convertStringToDate(parameters.getOrDefault("date_start", null));
        Date dateEnd = CommonUtil.convertStringToDate(parameters.getOrDefault("date_end", null));
        List<PenaltySummaryResponse> list = new ArrayList<>();
        if (this.userAccountService.isAdminUser(userAccountId)) {
            this.penaltyCheckOutService.getReport(dateStart, dateEnd)
                    .forEach(e -> list.add(reportMapperService.convertIPenaltyReportToResponse(e)));
        } else {
            this.penaltyCheckOutService.getReportByUserAccount(userAccountId, dateStart, dateEnd)
                    .forEach(e -> list.add(reportMapperService.convertIPenaltyReportToResponse(e)));
        }

        return list;
    }
}
