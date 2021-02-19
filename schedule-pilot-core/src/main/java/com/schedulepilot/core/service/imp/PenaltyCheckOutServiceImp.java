package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import com.schedulepilot.core.repository.PenaltyCheckOutRepository;
import com.schedulepilot.core.service.PenaltyCheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PenaltyCheckOutServiceImp implements PenaltyCheckOutService {

    private final PenaltyCheckOutRepository penaltyCheckOutRepository;

    @Override
    public PenaltyCheckOut save(PenaltyCheckOut penaltyCheckOut) {
        return this.penaltyCheckOutRepository.save(penaltyCheckOut);
    }
}
