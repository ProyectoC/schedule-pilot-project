package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.TicketCheckLogEntity;
import com.schedulepilot.core.repository.TicketCheckLogRepository;
import com.schedulepilot.core.service.TicketCheckLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketCheckLogServiceImp implements TicketCheckLogService {

    @Autowired
    private TicketCheckLogRepository ticketCheckLogRepository;

    @Override
    public TicketCheckLogEntity save(TicketCheckLogEntity ticketCheckLogEntity) {
        ticketCheckLogEntity.setIsActive(true);
        return this.ticketCheckLogRepository.save(ticketCheckLogEntity);
    }
}
