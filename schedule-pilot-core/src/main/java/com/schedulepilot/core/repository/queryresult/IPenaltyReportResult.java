package com.schedulepilot.core.repository.queryresult;

import java.time.LocalDateTime;

public interface IPenaltyReportResult {

    String getUsername();

    LocalDateTime getPenaltyDate();

    Long getPricePenalty();

    String getItemName();

    LocalDateTime getCreatedDate();

    LocalDateTime getReturnDate();
}
