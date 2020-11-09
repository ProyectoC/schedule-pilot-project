package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.repository.TicketCheckOutRepository;
import com.schedulepilot.core.service.TicketCheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketCheckOutServiceImp implements TicketCheckOutService {

    @Autowired
    private TicketCheckOutRepository ticketCheckOutRepository;

    @Override
    public TicketCheckOutEntity save(TicketCheckOutEntity ticketCheckOutEntity) {
        return this.ticketCheckOutRepository.save(ticketCheckOutEntity);
    }
}
