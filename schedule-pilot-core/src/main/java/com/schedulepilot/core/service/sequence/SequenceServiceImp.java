package com.schedulepilot.core.service.sequence;

import com.schedulepilot.core.repository.RequestCheckInRepository;
import com.schedulepilot.core.repository.TicketCheckInRepository;
import com.schedulepilot.core.repository.TicketCheckOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceServiceImp implements SequenceService {

    @Autowired
    private RequestCheckInRepository requestCheckInRepository;

    @Autowired
    private TicketCheckInRepository ticketCheckInRepository;

    @Autowired
    private TicketCheckOutRepository ticketCheckOutRepository;

    @Override
    public Long getRequestCheckInSequence() {
        return requestCheckInRepository.getRequestCheckInSequence();
    }

    @Override
    public Long getTicketCheckInSequence() {
        return ticketCheckInRepository.getTicketCheckInSequence();
    }

    @Override
    public Long getTicketCheckOutSequence() {
        return ticketCheckOutRepository.getTicketCheckOutSequence();
    }
}
