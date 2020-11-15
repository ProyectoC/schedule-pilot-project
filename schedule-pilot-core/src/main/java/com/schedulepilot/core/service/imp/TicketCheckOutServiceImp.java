package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.TicketCheckOutRepository;
import com.schedulepilot.core.service.TicketCheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketCheckOutServiceImp implements TicketCheckOutService {

    @Autowired
    private TicketCheckOutRepository ticketCheckOutRepository;

    @Override
    public TicketCheckOutEntity save(TicketCheckOutEntity ticketCheckOutEntity) {
        return this.ticketCheckOutRepository.save(ticketCheckOutEntity);
    }

    @Override
    public TicketCheckOutEntity getByTrackIdentification(String trackIdLong) throws SchedulePilotException {
        Optional<TicketCheckOutEntity> ticketCheckOutEntityOptional = this.ticketCheckOutRepository.findByTrackId(trackIdLong);
        if (ticketCheckOutEntityOptional.isPresent()) {
            return ticketCheckOutEntityOptional.get();
        }
        throw new SchedulePilotException("Ticket Check-Out with trackId:" + trackIdLong + " Not Found");
    }
}
