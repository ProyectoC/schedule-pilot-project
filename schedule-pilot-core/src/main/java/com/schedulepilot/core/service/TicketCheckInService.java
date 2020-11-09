package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import org.springframework.stereotype.Service;

@Service
public interface TicketCheckInService {

    Long getTicketCheckInSequence();

    TicketCheckInEntity save(TicketCheckInEntity ticketCheckInEntity);
}
