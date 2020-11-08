package com.schedulepilot.core.service.loanprocess;

import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.CheckInRequest;
import org.springframework.stereotype.Service;

@Service
public interface LoanProcessService {

    String createRequestCheckIn(CheckInRequest checkInRequest) throws SchedulePilotException;
}
