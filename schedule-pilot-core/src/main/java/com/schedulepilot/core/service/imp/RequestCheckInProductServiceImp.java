package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.constants.LoanProcessConstants;
import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.RequestCheckInProductRepository;
import com.schedulepilot.core.service.GlobalListDinamicService;
import com.schedulepilot.core.service.NotificationLayerService;
import com.schedulepilot.core.service.RequestCheckInProductService;
import com.schedulepilot.core.tasks.GenerateTicketCheckInTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class RequestCheckInProductServiceImp implements RequestCheckInProductService {

    @Autowired
    private RequestCheckInProductRepository requestCheckInProductRepository;

    @Autowired
    private GlobalListDinamicService globalListDinamicService;

    @Autowired
    private NotificationLayerService notificationLayerService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<RequestCheckInProductEntity> getRequestCheckInProductForProcess() throws SchedulePilotException {
        final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        final ZonedDateTime startOfDay =
                now.toLocalDate().atStartOfDay(ZoneId.of("America/Bogota"));
        final ZonedDateTime endOfDay =
                now.toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.of("America/Bogota"));
        LocalDateTime startDate = startOfDay.toLocalDateTime();
        LocalDateTime endDate = endOfDay.toLocalDateTime();
        return this.requestCheckInProductRepository.findByPriorityDate(startDate, endDate);
    }

    @Override
    public RequestCheckInProductEntity save(RequestCheckInProductEntity requestCheckInProductEntity) {
        return this.requestCheckInProductRepository.save(requestCheckInProductEntity);
    }

    @Async
    @Override
    public void processRequestCheckInProduct(RequestCheckInProductEntity requestCheckInProductEntity) throws SchedulePilotException {
        GenerateTicketCheckInTask generateTicketCheckInTask = this.applicationContext.getBean(GenerateTicketCheckInTask.class,
                requestCheckInProductEntity);
        generateTicketCheckInTask.execute();
        if (generateTicketCheckInTask.isNotEnableProduct()) {
            this.manageNotificationProductNotEnable(requestCheckInProductEntity);
            return;
        }
        if (generateTicketCheckInTask.isNotFoundItem()) {
            this.updateAttempts(requestCheckInProductEntity);
            return;
        }
        if (generateTicketCheckInTask.getTicketCheckInEntity() != null) {
            requestCheckInProductEntity.setProductRequestStatusEntity(this.globalListDinamicService.getProductRequestStatusOrException(LoanProcessConstants.FOUND_STATUS));
            this.save(requestCheckInProductEntity);
            this.manageNotificationGeneratedTicketCheckIn(requestCheckInProductEntity, generateTicketCheckInTask.getTicketCheckInEntity());
        }
    }

    private void manageNotificationProductNotEnable(RequestCheckInProductEntity requestCheckInProductEntity) throws SchedulePilotException {
        this.notificationLayerService.sendNotificationProductNotEnable(requestCheckInProductEntity
                        .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(),
                requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity(),
                requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());
        requestCheckInProductEntity.setProductRequestStatusEntity(this.globalListDinamicService.getProductRequestStatusOrException(LoanProcessConstants.NOT_FOUND_STATUS));
        this.save(requestCheckInProductEntity);
    }

    private void manageNotificationGeneratedTicketCheckIn(RequestCheckInProductEntity requestCheckInProductEntity, TicketCheckInEntity ticketCheckInEntity) {
        this.notificationLayerService.sendNotificationGeneratedTicketCheckIn(requestCheckInProductEntity
                        .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(), ticketCheckInEntity,
                requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());
    }

    private void updateAttempts(RequestCheckInProductEntity requestCheckInProductEntity) throws SchedulePilotException {
        int attemptsActual = requestCheckInProductEntity.getAttempts();
        if (attemptsActual >= 3) {
            requestCheckInProductEntity.setProductRequestStatusEntity(this.globalListDinamicService.getProductRequestStatusOrException(LoanProcessConstants.NOT_FOUND_STATUS));
            this.notificationLayerService.sendNotificationNotFoundProduct(requestCheckInProductEntity
                            .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(),
                    requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity(),
                    requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());
        } else {
            requestCheckInProductEntity.setAttempts(attemptsActual + 1);
        }
        this.save(requestCheckInProductEntity);
    }
}
