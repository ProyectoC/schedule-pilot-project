package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManagePenaltyService {

    PenaltyCheckOut generatePenaltyCheckOut(TicketCheckOutEntity ticketCheckOutEntity);
}
