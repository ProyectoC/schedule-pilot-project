package com.schedulepilot.core.tasks;

import com.schedulepilot.core.constants.LoanProcessConstants;
import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.LoanProcessException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.GlobalListDynamicService;
import com.schedulepilot.core.service.TicketCheckInService;
import com.schedulepilot.core.service.TicketCheckOutService;
import com.schedulepilot.core.service.sequence.SequenceService;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Scope("prototype")
public class GenerateTicketCheckOutTask {

    // Constants
    private static final Logger CLASS_LOGGER = LogManager.getLogger(GenerateTicketCheckOutTask.class);

    @Autowired
    private TicketCheckOutService ticketCheckOutService;

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private TicketCheckInService ticketCheckInService;

    @Autowired
    private SequenceService sequenceService;

    private TicketCheckInEntity ticketCheckInEntity;
    private final UserAccountEntity userAccountEntity;

    @Getter
    private TicketCheckOutEntity ticketCheckOutEntity;

    public GenerateTicketCheckOutTask(TicketCheckInEntity ticketCheckInEntity, UserAccountEntity userAccountEntity) {
        this.ticketCheckInEntity = ticketCheckInEntity;
        this.userAccountEntity = userAccountEntity;
    }

    public void execute() throws SchedulePilotException {
        CLASS_LOGGER.info("Init Task");

        this.validateTicketCheckIn(ticketCheckInEntity);

        this.ticketCheckInEntity.setTicketCheckStatusEntity(this.globalListDynamicService.getTicketCheckStatusOrException(LoanProcessConstants.USED_STATUS));
        this.ticketCheckInEntity = this.ticketCheckInService.save(ticketCheckInEntity);

        Long trackId = this.sequenceService.getTicketCheckOutSequence();
        this.ticketCheckOutEntity = new TicketCheckOutEntity();
        this.ticketCheckOutEntity.setTrackId(trackId.toString());
        this.ticketCheckOutEntity.setTicketCheckInEntity(ticketCheckInEntity);
        this.ticketCheckOutEntity.setTicketCheckStatusEntity(this.globalListDynamicService.getTicketCheckStatusOrException(LoanProcessConstants.GENERATED_STATUS));
        this.ticketCheckOutEntity.setUserAccountEntity(userAccountEntity);
        this.ticketCheckOutEntity = this.ticketCheckOutService.save(ticketCheckOutEntity);
        
        CLASS_LOGGER.info("End Task");
    }

    private void validateTicketCheckIn(TicketCheckInEntity ticketCheckInEntity) throws SchedulePilotException {
        LocalDateTime dateDelivery = ticketCheckInEntity.getDeliveryDate();
        dateDelivery = dateDelivery.plusMinutes(15);

        if (ticketCheckInEntity.getTicketCheckStatusEntity().getName().equals(LoanProcessConstants.USED_STATUS)) {
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_TICKET_CHECK_IN_ALREADY_USED, "TicketCheckIn TrackID: " +
                    ticketCheckInEntity.getTrackId() + " has already used on: " + ticketCheckInEntity.getLastModifiedDate());
        }

        if (ticketCheckInEntity.getTicketCheckStatusEntity().getName().equals(LoanProcessConstants.EXPIRED_STATUS)) {
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_TICKET_CHECK_IN_HAS_EXPIRED, "TicketCheckIn TrackID: " +
                    ticketCheckInEntity.getTrackId() + " has expired on: " + ticketCheckInEntity.getDeliveryDate());
        }

        if (dateDelivery.isBefore(LocalDateTime.now())) {
            ticketCheckInEntity.setTicketCheckStatusEntity(this.globalListDynamicService.getTicketCheckStatusOrException(LoanProcessConstants.EXPIRED_STATUS));
            this.ticketCheckInService.save(ticketCheckInEntity);
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_TICKET_CHECK_IN_HAS_EXPIRED, "TicketCheckIn TrackID: " +
                    ticketCheckInEntity.getTrackId() + " has expired on: " + ticketCheckInEntity.getDeliveryDate());
        }
    }
}
