package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import com.schedulepilot.core.repository.PenaltyCheckOutRepository;
import com.schedulepilot.core.repository.queryresult.IPenaltyReportResult;
import com.schedulepilot.core.repository.queryresult.IPenaltySummaryResult;
import com.schedulepilot.core.service.PenaltyCheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class PenaltyCheckOutServiceImp implements PenaltyCheckOutService {

    private final PenaltyCheckOutRepository penaltyCheckOutRepository;

    @Override
    public PenaltyCheckOut save(PenaltyCheckOut penaltyCheckOut) {
        return this.penaltyCheckOutRepository.save(penaltyCheckOut);
    }

    @Override
    public List<IPenaltyReportResult> getReport(Date startDate, Date endDate) {
        return this.penaltyCheckOutRepository.findReport(startDate, endDate);
    }

    @Override
    public List<IPenaltyReportResult> getReportByUserAccount(Long userAccount, Date startDate, Date endDate) {
        return this.penaltyCheckOutRepository.findReportByUserAccount(userAccount, startDate, endDate);
    }

    @Override
    public List<IPenaltySummaryResult> getSummary(Date startDate, Date endDate) {
        return this.penaltyCheckOutRepository.findSummary(startDate, endDate);
    }

    @Override
    public List<IPenaltySummaryResult> getSummaryByUserAccount(Long userAccount, Date startDate, Date endDate) {
        return this.penaltyCheckOutRepository.findSummaryByUserAccount(userAccount, startDate, endDate);
    }
}
