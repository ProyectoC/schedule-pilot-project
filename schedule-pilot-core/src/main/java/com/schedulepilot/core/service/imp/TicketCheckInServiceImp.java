package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.TicketCheckInRepository;
import com.schedulepilot.core.service.TicketCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketCheckInServiceImp implements TicketCheckInService {

    @Autowired
    private TicketCheckInRepository ticketCheckInRepository;
    
    @Override
    public TicketCheckInEntity save(TicketCheckInEntity ticketCheckInEntity) {
        ticketCheckInEntity.setIsActive(true);
        return this.ticketCheckInRepository.save(ticketCheckInEntity);
    }

    @Override
    public TicketCheckInEntity getByTrackIdentification(String trackIdLong) throws SchedulePilotException {
        Optional<TicketCheckInEntity> ticketCheckInEntityOptional = this.ticketCheckInRepository.findByTrackId(trackIdLong);
        if (ticketCheckInEntityOptional.isPresent()) {
            return ticketCheckInEntityOptional.get();
        }
        throw new SchedulePilotException("Ticket Check-In with trackId:" + trackIdLong + " Not Found");
    }
}
