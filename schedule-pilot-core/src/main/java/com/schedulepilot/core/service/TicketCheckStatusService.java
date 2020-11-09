package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.TicketCheckStatusEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketCheckStatusService {

    List<TicketCheckStatusEntity> getAll();
}
