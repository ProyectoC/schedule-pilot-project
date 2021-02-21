package com.schedulepilot.core.service.modelmapper;

import com.schedulepilot.core.entities.model.ViewChartLoanProduct;
import com.schedulepilot.core.repository.queryresult.IPenaltyReportResult;
import com.schedulepilot.core.response.report.LoanProductResponse;
import com.schedulepilot.core.response.report.PenaltySummaryResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReportMapperServiceImp implements ReportMapperService {

    private final ModelMapper modelMapper;

    @Override
    public LoanProductResponse convertViewChartLoanProductToResponse(ViewChartLoanProduct viewChartLoanProduct) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(viewChartLoanProduct, LoanProductResponse.class);
    }

    @Override
    public PenaltySummaryResponse convertIPenaltyReportToResponse(IPenaltyReportResult iPenaltyReportResult) {
        return new PenaltySummaryResponse(iPenaltyReportResult.getUsername(), iPenaltyReportResult.getPenaltyDate(),
                iPenaltyReportResult.getPricePenalty(), iPenaltyReportResult.getItemName(), iPenaltyReportResult.getCreatedDate(),
                iPenaltyReportResult.getReturnDate());
    }
}
