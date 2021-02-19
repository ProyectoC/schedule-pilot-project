package com.schedulepilot.core.scheduled;

import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.RequestCheckInProductService;
import com.schedulepilot.core.service.TicketCheckInService;
import com.schedulepilot.core.service.TicketCheckOutService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private final RequestCheckInProductService requestCheckInProductService;

    private final TicketCheckInService ticketCheckInService;

    private final TicketCheckOutService ticketCheckOutService;

    @Scheduled(fixedDelay = 1 * 60000, initialDelay = 1000)
    public void scheduleManageRequestCheckInProducts() {
        LOGGER.info("SCHEDULING REQUEST-CHECK-IN ---> GO STARTED! AT: {}", LocalDateTime.now());
        try {
            List<RequestCheckInProductEntity> listRequestCheckInProducts = this.requestCheckInProductService.getRequestCheckInProductForProcess();
            if (listRequestCheckInProducts.isEmpty()) {
                LOGGER.info("SCHEDULING REQUEST-CHECK-IN ---> GO FINISHED! AT: {}", LocalDateTime.now());
                return;
            }
            for (RequestCheckInProductEntity temp : listRequestCheckInProducts) {
                this.requestCheckInProductService.processRequestCheckInProduct(temp);
            }
        } catch (SchedulePilotException ex) {
            LOGGER.error("SCHEDULING REQUEST-CHECK-IN ---> GO WITH ERRORS! AT: {}, ERROR DESCRIPTION: {}",
                    LocalDateTime.now(), ex.getMessage());
        }
        LOGGER.info("SCHEDULING REQUEST-CHECK-IN ---> GO FINISHED! AT: {}", LocalDateTime.now());
    }


    @Scheduled(fixedDelay = 5 * 60000, initialDelay = 5000)
    public void scheduleManageExpiredTicketCheckInProducts() {
        LOGGER.info("SCHEDULING EXPIRED TICKET-CHECK-IN ---> GO STARTED! AT: {}", LocalDateTime.now());
        try {
            List<TicketCheckInEntity> listTicketChekIn = this.ticketCheckInService.getAllExpiredTicketCheckIn();
            if (listTicketChekIn.isEmpty()) {
                LOGGER.info("SCHEDULING EXPIRED TICKET-CHECK-IN ---> GO FINISHED! AT: {}", LocalDateTime.now());
                return;
            }
            for (TicketCheckInEntity temp : listTicketChekIn) {
                this.ticketCheckInService.processExpiredTicketCheckIn(temp);
            }
        } catch (SchedulePilotException ex) {
            LOGGER.error("SCHEDULING EXPIRED TICKET-CHECK-IN ---> GO WITH ERRORS! AT: {}, ERROR DESCRIPTION: {}",
                    LocalDateTime.now(), ex.getMessage());
        }
        LOGGER.info("SCHEDULING EXPIRED TICKET-CHECK-IN ---> GO FINISHED! AT: {}", LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 6 * 60000, initialDelay = 5000)
    public void scheduleManageExpiredTicketCheckOutProducts() {
        LOGGER.info("SCHEDULING EXPIRED TICKET-CHECK-OUT ---> GO STARTED! AT: {}", LocalDateTime.now());
        try {
            List<TicketCheckOutEntity> listTicketChekOut = this.ticketCheckOutService.getAllExpiredTicketCheckOut();
            if (listTicketChekOut.isEmpty()) {
                LOGGER.info("SCHEDULING EXPIRED TICKET-CHECK-OUT ---> GO FINISHED! AT: {}", LocalDateTime.now());
                return;
            }
            for (TicketCheckOutEntity temp : listTicketChekOut) {
                this.ticketCheckOutService.processExpiredTicketCheckOut(temp);
            }
        } catch (SchedulePilotException ex) {
            LOGGER.error("SCHEDULING EXPIRED TICKET-CHECK-OUT ---> GO WITH ERRORS! AT: {}, ERROR DESCRIPTION: {}",
                    LocalDateTime.now(), ex.getMessage());
        }
        LOGGER.info("SCHEDULING EXPIRED TICKET-CHECK-OUT ---> GO FINISHED! AT: {}", LocalDateTime.now());
    }


}
