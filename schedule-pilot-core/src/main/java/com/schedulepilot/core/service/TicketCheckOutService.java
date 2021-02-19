package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.TicketCheckOutResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TicketCheckOutService {

    ModelMapper modelMapper = new ModelMapper();

    static TicketCheckOutResponse convertEntityToResponse(TicketCheckOutEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        TicketCheckOutResponse ticketCheckOutResponse = modelMapper.map(entity, TicketCheckOutResponse.class);
        ticketCheckOutResponse.setTrackIdTicketIn(entity.getTicketCheckInEntity().getTrackId());
        ticketCheckOutResponse.setTrackIdTicketOut(entity.getTrackId());
        ticketCheckOutResponse.setDeliveryDate(entity.getTicketCheckInEntity().getDeliveryDate());
        ticketCheckOutResponse.setReturnDate(entity.getTicketCheckInEntity().getReturnDate());
        return ticketCheckOutResponse;
    }

    TicketCheckOutEntity save(TicketCheckOutEntity ticketCheckOutEntity);

    TicketCheckOutEntity getByTrackIdentification(String trackIdLong) throws SchedulePilotException;

    PageResponseDto<TicketCheckOutResponse> getAllTicketCheckOut(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;

    List<TicketCheckOutEntity> getAllExpiredTicketCheckOut();

    void processExpiredTicketCheckOut(TicketCheckOutEntity ticketCheckOutEntity) throws SchedulePilotException;
}
