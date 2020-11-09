package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface TicketCheckInService {

    TicketCheckInEntity save(TicketCheckInEntity ticketCheckInEntity);

    TicketCheckInEntity getByTrackIdentification(Long trackIdLong) throws SchedulePilotException;
}
