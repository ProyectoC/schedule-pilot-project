package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.service.PenaltyCheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ManagePenaltyServiceImp implements ManagePenaltyService {

    private static final Long PENALTY_PRICE = 4000L;

    private final PenaltyCheckOutService penaltyCheckOutService;

    @Override
    public PenaltyCheckOut generatePenaltyCheckOut(TicketCheckOutEntity ticketCheckOutEntity) {
        PenaltyCheckOut penaltyCheckOut = new PenaltyCheckOut();
        penaltyCheckOut.setTicketCheckOutEntity(ticketCheckOutEntity);
        penaltyCheckOut.setPricePenalty(PENALTY_PRICE);

        return this.penaltyCheckOutService.save(penaltyCheckOut);
    }
}
