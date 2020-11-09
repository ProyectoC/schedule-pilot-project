package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.TicketCheckStatusEntity;
import com.schedulepilot.core.repository.TicketCheckStatusRepository;
import com.schedulepilot.core.service.TicketCheckStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketCheckStatusServiceImp implements TicketCheckStatusService {

    @Autowired
    private TicketCheckStatusRepository ticketCheckStatusRepository;

    @Override
    public List<TicketCheckStatusEntity> getAll() {
        return this.ticketCheckStatusRepository.findAll();
    }
}
