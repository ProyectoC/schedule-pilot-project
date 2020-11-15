package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.TicketCheckLogEntity;
import org.springframework.stereotype.Service;

@Service
public interface TicketCheckLogService {

    TicketCheckLogEntity save(TicketCheckLogEntity ticketCheckLogEntity);
}
