package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.repository.TicketCheckInRepository;
import com.schedulepilot.core.service.TicketCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketCheckInServiceImp implements TicketCheckInService {

    @Autowired
    private TicketCheckInRepository ticketCheckInRepository;

    @Override
    public Long getTicketCheckInSequence() {
        return this.ticketCheckInRepository.getTicketCheckInSequence();
    }

    @Override
    public TicketCheckInEntity save(TicketCheckInEntity ticketCheckInEntity) {
        ticketCheckInEntity.setIsActive(true);
        return this.ticketCheckInRepository.save(ticketCheckInEntity);
    }
}
