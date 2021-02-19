package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import org.springframework.stereotype.Service;

@Service
public interface PenaltyCheckOutService {

    PenaltyCheckOut save(PenaltyCheckOut penaltyCheckOut);
}
