package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.report.LoanProductResponse;
import com.schedulepilot.core.response.report.PenaltySummaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ManageReportService {

    List<LoanProductResponse> getReportLoanProduct(Map<String, String> parameters, Long userAccountId);

    List<PenaltySummaryResponse> getReportPenaltySummary(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;
}
