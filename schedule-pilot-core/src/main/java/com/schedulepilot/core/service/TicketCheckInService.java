package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.RequestCheckInResponse;
import com.schedulepilot.core.response.TicketCheckInResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TicketCheckInService {

    ModelMapper modelMapper = new ModelMapper();

    static TicketCheckInResponse convertEntityToResponse(TicketCheckInEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        TicketCheckInResponse ticketCheckInResponse = modelMapper.map(entity, TicketCheckInResponse.class);
        ticketCheckInResponse.setTrackIdTicket(entity.getTrackId());
        ticketCheckInResponse.setTrackIdRequestOrigin(entity.getRequestCheckInEntity().getTrackId());
        return ticketCheckInResponse;
    }

    TicketCheckInEntity save(TicketCheckInEntity ticketCheckInEntity);

    TicketCheckInEntity getByTrackIdentification(String trackIdLong) throws SchedulePilotException;

    PageResponseDto<TicketCheckInResponse> getAllTicketCheckIn(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;
}
