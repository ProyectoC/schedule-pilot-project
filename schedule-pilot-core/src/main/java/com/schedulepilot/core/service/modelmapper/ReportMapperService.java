package com.schedulepilot.core.service.modelmapper;

import com.schedulepilot.core.entities.model.ViewChartLoanProduct;
import com.schedulepilot.core.repository.queryresult.IPenaltyReportResult;
import com.schedulepilot.core.response.report.LoanProductResponse;
import com.schedulepilot.core.response.report.PenaltySummaryResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReportMapperService {

    LoanProductResponse convertViewChartLoanProductToResponse(ViewChartLoanProduct viewChartLoanProduct);

    PenaltySummaryResponse convertIPenaltyReportToResponse(IPenaltyReportResult iPenaltyReportResult);
}
