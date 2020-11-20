package com.schedulepilot.core.service.loanprocess;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.CheckInRequest;
import com.schedulepilot.core.request.CheckLogRequest;
import com.schedulepilot.core.request.CheckOutRequest;
import com.schedulepilot.core.response.RequestCheckInResponse;
import com.schedulepilot.core.response.TicketCheckInResponse;
import com.schedulepilot.core.response.TicketCheckOutResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LoanProcessService {

    String createRequestCheckIn(CheckInRequest checkInRequest) throws SchedulePilotException;

    PageResponseDto<RequestCheckInResponse> getRequestCheckIn(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;

    PageResponseDto<TicketCheckInResponse> getAllTicketCheckIn(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;

    PageResponseDto<TicketCheckOutResponse> getAllTicketCheckOut(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;

    String createRequestCheckOut(CheckOutRequest checkOutRequest) throws SchedulePilotException;

    String createRequestCheckLog(CheckLogRequest checkOutRequest) throws SchedulePilotException;
}
