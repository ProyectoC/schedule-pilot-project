package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import com.schedulepilot.core.repository.queryresult.IPenaltyReportResult;
import com.schedulepilot.core.repository.queryresult.IPenaltySummaryResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public interface PenaltyCheckOutService {

    PenaltyCheckOut save(PenaltyCheckOut penaltyCheckOut);

    List<IPenaltyReportResult> getReport(Date startDate, Date endDate);

    List<IPenaltyReportResult> getReportByUserAccount(Long userAccount, Date startDate, Date endDate);

    List<IPenaltySummaryResult> getSummary(Date startDate, Date endDate);

    List<IPenaltySummaryResult> getSummaryByUserAccount(Long userAccount, Date startDate, Date endDate);
}
