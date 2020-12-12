package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.constants.LoanProcessConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.AccountUserRepository;
import com.schedulepilot.core.repository.RequestCheckInProductRepository;
import com.schedulepilot.core.response.RequestCheckInResponse;
import com.schedulepilot.core.service.GlobalListDynamicService;
import com.schedulepilot.core.service.MessageLayerService;
import com.schedulepilot.core.service.NotificationLayerService;
import com.schedulepilot.core.service.RequestCheckInProductService;
import com.schedulepilot.core.tasks.GenerateTicketCheckInTask;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import com.schedulepilot.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class RequestCheckInProductServiceImp implements RequestCheckInProductService {

    private static final List<String> LIST_ATTRIBUTES = Arrays.asList(RequestCheckInProductEntity_.loanDate.getName());

    @Autowired
    private RequestCheckInProductRepository requestCheckInProductRepository;

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private NotificationLayerService notificationLayerService;

    @Autowired
    private MessageLayerService messageLayerService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AccountUserRepository userRepository;

    @Override
    public List<RequestCheckInProductEntity> getRequestCheckInProductForProcess() throws SchedulePilotException {
        final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        final ZonedDateTime startOfDay =
                now.toLocalDate().atStartOfDay(ZoneId.of("America/Bogota"));
        final ZonedDateTime endOfDay =
                now.toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.of("America/Bogota"));
        LocalDateTime startDate = startOfDay.toLocalDateTime();
        LocalDateTime endDate = endOfDay.toLocalDateTime();
        return this.requestCheckInProductRepository.findByPriorityDate(startDate, endDate, now.toLocalDateTime());
    }

    @Override
    public PageResponseDto<RequestCheckInResponse> getRequestCheckInProductResponse(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        UserAccountEntity userAccountEntity = userRepository.getOne(userAccountId);
        RolAccountEntity rolAccountEntity = userAccountEntity.getRolAccountEntity();
        if (rolAccountEntity.getName().equals("Super User") || rolAccountEntity.getName().equals("Registro y Control")) {
            userAccountId = null;
        }

        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, LIST_ATTRIBUTES);
        paginationAndOrderTask.execute();

        String productName = parameters.getOrDefault("product_name", "");
        String trackId = parameters.getOrDefault("track_id", null);
        LocalDateTime loanDateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("loan_date_start", null));
        LocalDateTime loanDateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("loan_date_end", null));
        Date requestDateStart = CommonUtil.convertStringToDate(parameters.getOrDefault("request_date_start", null));
        Date requestDateEnd = CommonUtil.convertStringToDate(parameters.getOrDefault("request_date_end", null));
        String status = parameters.getOrDefault("status", "");

        PageResponseDto<RequestCheckInResponse> pageResponse = new PageResponseDto<>();

        List<RequestCheckInResponse> list = new ArrayList<>();
        if (paginationAndOrderTask.getPageData() != null) {
            Page<RequestCheckInProductEntity> page = this.requestCheckInProductRepository.findAllByUserAccountPage(paginationAndOrderTask.getPageData(), userAccountId,
                    productName, trackId, loanDateStart, loanDateEnd, requestDateStart, requestDateEnd, status);
            page.getContent().forEach(e -> list.add(RequestCheckInProductService.convertEntityToResponse(e)));
            pageResponse.build(list, page);
        } else {
            List<RequestCheckInProductEntity> requestCheckInProductEntities = this.requestCheckInProductRepository.findAllByUserAccountSort(paginationAndOrderTask.getSortData(), userAccountId,
                    productName, trackId, loanDateStart, loanDateEnd, requestDateStart, requestDateEnd, status);
            requestCheckInProductEntities.forEach(e -> list.add(RequestCheckInProductService.convertEntityToResponse(e)));
            pageResponse.build(list);
        }
        return pageResponse;
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
            requestCheckInProductEntity.setProductRequestStatusEntity(this.globalListDynamicService.getProductRequestStatusOrException(LoanProcessConstants.FOUND_STATUS));
            this.save(requestCheckInProductEntity);
            this.manageNotificationGeneratedTicketCheckIn(requestCheckInProductEntity, generateTicketCheckInTask.getTicketCheckInEntity());
        }
    }

    private void manageNotificationProductNotEnable(RequestCheckInProductEntity requestCheckInProductEntity) throws SchedulePilotException {
        this.notificationLayerService.sendNotificationProductNotEnable(requestCheckInProductEntity
                        .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(),
                requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity(),
                requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());

        this.messageLayerService.sendNotificationProductNotEnable(requestCheckInProductEntity
                .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(),
                requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity(),
                requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());

        requestCheckInProductEntity.setProductRequestStatusEntity(this.globalListDynamicService.getProductRequestStatusOrException(LoanProcessConstants.NOT_FOUND_STATUS));
        this.save(requestCheckInProductEntity);
    }

    private void manageNotificationGeneratedTicketCheckIn(RequestCheckInProductEntity requestCheckInProductEntity, TicketCheckInEntity ticketCheckInEntity) {
        this.notificationLayerService.sendNotificationGeneratedTicketCheckIn(requestCheckInProductEntity
                        .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(), ticketCheckInEntity,
                requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());

        this.messageLayerService.sendNotificationGeneratedTicketCheckIn(requestCheckInProductEntity
                        .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(), ticketCheckInEntity,
                requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());
    }

    private void updateAttempts(RequestCheckInProductEntity requestCheckInProductEntity) throws SchedulePilotException {
        int attemptsActual = requestCheckInProductEntity.getAttempts();
        if (attemptsActual >= 3) {
            requestCheckInProductEntity.setProductRequestStatusEntity(this.globalListDynamicService.getProductRequestStatusOrException(LoanProcessConstants.NOT_FOUND_STATUS));
            this.notificationLayerService.sendNotificationNotFoundProduct(requestCheckInProductEntity
                            .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(),
                    requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity(),
                    requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());
            this.messageLayerService.sendNotificationNotFoundProduct(requestCheckInProductEntity
                            .getRequestCheckInProductId().getRequestCheckInEntity().getUserAccountEntity(),
                    requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity(),
                    requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());
        } else {
            requestCheckInProductEntity.setAttempts(attemptsActual + 1);
        }
        this.save(requestCheckInProductEntity);
    }


}
