package com.schedulepilot.core.tasks;

import com.schedulepilot.core.constants.ItemConstants;
import com.schedulepilot.core.constants.LoanProcessConstants;
import com.schedulepilot.core.entities.model.ItemEntity;
import com.schedulepilot.core.entities.model.TicketCheckLogEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.LoanProcessException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.GlobalListDynamicService;
import com.schedulepilot.core.service.ItemService;
import com.schedulepilot.core.service.TicketCheckLogService;
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
public class GenerateTicketCheckLogTask {

    // Constants
    private static final Logger CLASS_LOGGER = LogManager.getLogger(GenerateTicketCheckLogTask.class);

    @Autowired
    private TicketCheckOutService ticketCheckOutService;

    @Autowired
    private TicketCheckLogService ticketCheckLogService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private SequenceService sequenceService;

    private TicketCheckOutEntity ticketCheckOutEntity;
    private final UserAccountEntity userAccountEntity;

    @Getter
    private TicketCheckLogEntity ticketCheckLogEntity;

    public GenerateTicketCheckLogTask(TicketCheckOutEntity ticketCheckOutEntity, UserAccountEntity userAccountEntity) {
        this.ticketCheckOutEntity = ticketCheckOutEntity;
        this.userAccountEntity = userAccountEntity;
    }

    public void execute() throws SchedulePilotException {
        CLASS_LOGGER.info("Init Task");

        this.validateTicketCheckOut(ticketCheckOutEntity);

        this.ticketCheckOutEntity.setTicketCheckStatusEntity(this.globalListDynamicService.getTicketCheckStatusOrException(LoanProcessConstants.USED_STATUS));
        this.ticketCheckOutEntity = this.ticketCheckOutService.save(ticketCheckOutEntity);

        ItemEntity itemEntity = this.ticketCheckOutEntity.getTicketCheckInEntity().getItemEntity();
        itemEntity.setItemStatusEntity(this.globalListDynamicService.getItemStatusOrException(ItemConstants.ENABLE_STATUS));
        this.itemService.save(itemEntity);

        this.ticketCheckLogEntity = new TicketCheckLogEntity();
        this.ticketCheckLogEntity.setTicketCheckOutEntity(ticketCheckOutEntity);
        this.ticketCheckLogEntity.setUserAccountEntity(userAccountEntity);
        this.ticketCheckLogEntity = this.ticketCheckLogService.save(ticketCheckLogEntity);

        CLASS_LOGGER.info("End Task");
    }

    private void validateTicketCheckOut(TicketCheckOutEntity ticketCheckOutEntity) throws SchedulePilotException {
        LocalDateTime dateReturn = ticketCheckOutEntity.getTicketCheckInEntity().getReturnDate();
        dateReturn = dateReturn.plusMinutes(15);

        if (ticketCheckOutEntity.getTicketCheckStatusEntity().getName().equals(LoanProcessConstants.USED_STATUS)) {
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_TICKET_CHECK_OUT_ALREADY_USED, "TicketCheckOut TrackID: " +
                    ticketCheckOutEntity.getTrackId() + " has already used on: " + ticketCheckOutEntity.getLastModifiedDate());
        }

        if (ticketCheckOutEntity.getTicketCheckStatusEntity().getName().equals(LoanProcessConstants.EXPIRED_STATUS)) {
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_TICKET_CHECK_OUT_HAS_EXPIRED, "TicketCheckOut TrackID: " +
                    ticketCheckOutEntity.getTrackId() + " has expired on: " + ticketCheckOutEntity.getTicketCheckInEntity().getReturnDate());
        }

        if (dateReturn.isBefore(LocalDateTime.now())) {
            ticketCheckOutEntity.setTicketCheckStatusEntity(this.globalListDynamicService.getTicketCheckStatusOrException(LoanProcessConstants.EXPIRED_STATUS));
            this.ticketCheckOutService.save(ticketCheckOutEntity);
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_TICKET_CHECK_OUT_HAS_EXPIRED, "TicketCheckOut TrackID: " +
                    ticketCheckOutEntity.getTrackId() + " has expired on: " + ticketCheckOutEntity.getTicketCheckInEntity().getReturnDate());
        }
    }
}
