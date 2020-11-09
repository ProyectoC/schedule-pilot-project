package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import org.springframework.stereotype.Service;

@Service
public interface TicketCheckOutService {

    TicketCheckOutEntity save(TicketCheckOutEntity ticketCheckOutEntity);
}
