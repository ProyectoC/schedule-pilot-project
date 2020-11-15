package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface TicketCheckOutService {

    TicketCheckOutEntity save(TicketCheckOutEntity ticketCheckOutEntity);

    TicketCheckOutEntity getByTrackIdentification(String trackIdLong) throws SchedulePilotException;
}
