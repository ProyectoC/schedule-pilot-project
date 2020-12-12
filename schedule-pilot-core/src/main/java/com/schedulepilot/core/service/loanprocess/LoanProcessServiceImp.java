package com.schedulepilot.core.service.loanprocess;

import com.schedulepilot.core.constants.ProductConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.LoanProcessException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.CheckInProductRequest;
import com.schedulepilot.core.request.CheckInRequest;
import com.schedulepilot.core.request.CheckLogRequest;
import com.schedulepilot.core.request.CheckOutRequest;
import com.schedulepilot.core.response.RequestCheckInResponse;
import com.schedulepilot.core.response.TicketCheckInResponse;
import com.schedulepilot.core.response.TicketCheckOutResponse;
import com.schedulepilot.core.service.*;
import com.schedulepilot.core.service.sequence.SequenceService;
import com.schedulepilot.core.tasks.GenerateTicketCheckLogTask;
import com.schedulepilot.core.tasks.GenerateTicketCheckOutTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class LoanProcessServiceImp implements LoanProcessService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private RequestCheckInService requestCheckInService;

    @Autowired
    private TicketCheckInService ticketCheckInService;

    @Autowired
    private TicketCheckOutService ticketCheckOutService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NotificationLayerService notificationLayerService;

    @Autowired
    private MessageLayerService messageLayerService;

    @Autowired
    private RequestCheckInProductService requestCheckInProductService;

    @Override
    public String createRequestCheckIn(CheckInRequest checkInRequest) throws SchedulePilotException {
        // Search user request
        UserAccountEntity userAccountEntity = userAccountService.getByIdOrException(checkInRequest.getUserAccountId());
        // Generate track Identificator.
        Long trackId = this.sequenceService.getRequestCheckInSequence();

        RequestCheckInEntity requestCheckInEntity = new RequestCheckInEntity();
        requestCheckInEntity.setTrackId(trackId.toString());
        requestCheckInEntity.setUserAccountEntity(userAccountEntity);
        requestCheckInEntity.setRequestCheckInProductEntities(new ArrayList<>());

        List<CheckInProductRequest> listProducts = checkInRequest.getCheckInProductRequest();
        if (listProducts.isEmpty()) {
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_PRODUCTS_ARE_EMPTY, "");
        }

        for (CheckInProductRequest checkInProductRequest : listProducts) {
            ProductEntity productEntity = productService.getByIdOrException(checkInProductRequest.getProductId());

            RequestCheckInProductEntity requestCheckInProductEntity = new RequestCheckInProductEntity();
            requestCheckInProductEntity.setAttempts(0);
            LocalDateTime localDateTime = this.convertToLocalDateTimeViaSqlTimestamp(checkInProductRequest.getLoanDate()).plusHours(5);
            if (localDateTime.isBefore(LocalDateTime.now()))
                throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_DATE_NOT_VALID, "Date loan: " +
                        localDateTime + " not valid.");
            requestCheckInProductEntity.setLoanDate(localDateTime);

            ProductRequestStatusEntity productRequestStatusEntity = this.globalListDynamicService.getProductRequestStatusOrException(ProductConstants.REQUESTED_STATUS);
            requestCheckInProductEntity.setProductRequestStatusEntity(productRequestStatusEntity);
            RequestCheckInProductId requestCheckInProductId = new RequestCheckInProductId(productEntity);
            requestCheckInProductEntity.setRequestCheckInProductId(requestCheckInProductId);

            requestCheckInEntity.addProductRole(requestCheckInProductEntity);
        }
        this.requestCheckInService.save(requestCheckInEntity);
        return "TrackID: " + trackId;
    }

    @Override
    public PageResponseDto<RequestCheckInResponse> getRequestCheckIn(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        return this.requestCheckInProductService.getRequestCheckInProductResponse(parameters, userAccountId);
    }

    @Override
    public PageResponseDto<TicketCheckInResponse> getAllTicketCheckIn(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        return this.ticketCheckInService.getAllTicketCheckIn(parameters, userAccountId);
    }

    @Override
    public PageResponseDto<TicketCheckOutResponse> getAllTicketCheckOut(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        return this.ticketCheckOutService.getAllTicketCheckOut(parameters, userAccountId);
    }

    @Override
    public String createRequestCheckOut(CheckOutRequest checkOutRequest) throws SchedulePilotException {
        // Search ticketCheckIn
        TicketCheckInEntity ticketCheckInEntity = this.ticketCheckInService.getByTrackIdentification(checkOutRequest.getTrackIdentificationCheckIn());
        // Search UserAccount
        UserAccountEntity userAccountEntity = userAccountService.getByIdOrException(checkOutRequest.getUserAccountId());
        RolAccountEntity rolAccountEntity = userAccountEntity.getRolAccountEntity();
        if (!rolAccountEntity.getName().equals("Super User") || !rolAccountEntity.getName().equals("Registro y Control")) {
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_USER_ACCOUNT_GENERATE_TICKET_CHECK_OUT_NOT_VALID, "Rol: " +
                    rolAccountEntity.getName() + " not valid.");
        }

        GenerateTicketCheckOutTask generateTicketCheckOutTask = this.applicationContext.getBean(GenerateTicketCheckOutTask.class,
                ticketCheckInEntity, userAccountEntity);
        generateTicketCheckOutTask.execute();

        this.notificationLayerService.sendNotificationGeneratedTicketCheckOut(ticketCheckInEntity.getRequestCheckInEntity().getUserAccountEntity(),
                generateTicketCheckOutTask.getTicketCheckOutEntity(), ticketCheckInEntity);

        this.messageLayerService.sendNotificationGeneratedTicketCheckOut(ticketCheckInEntity.getRequestCheckInEntity().getUserAccountEntity(),
                generateTicketCheckOutTask.getTicketCheckOutEntity(), ticketCheckInEntity);

        return "TrackID: " + generateTicketCheckOutTask.getTicketCheckOutEntity().getTrackId();
    }

    @Override
    public String createRequestCheckLog(CheckLogRequest checkOutRequest) throws SchedulePilotException {
        // Search ticketCheckIn
        TicketCheckOutEntity ticketCheckOutEntity = this.ticketCheckOutService.getByTrackIdentification(checkOutRequest.getTrackIdentificationCheckOut());
        // Search UserAccount
        UserAccountEntity userAccountEntity = userAccountService.getByIdOrException(checkOutRequest.getUserAccountId());
        RolAccountEntity rolAccountEntity = userAccountEntity.getRolAccountEntity();
        if (!rolAccountEntity.getName().equals("Super User") || !rolAccountEntity.getName().equals("Registro y Control")) {
            throw new LoanProcessException(ExceptionCode.ERROR_LOAN_PROCESS_USER_ACCOUNT_GENERATE_TICKET_CHECK_LOG_NOT_VALID, "Rol: " +
                    rolAccountEntity.getName() + " not valid.");
        }

        GenerateTicketCheckLogTask generateTicketCheckLogTask = this.applicationContext.getBean(GenerateTicketCheckLogTask.class,
                ticketCheckOutEntity, userAccountEntity);
        generateTicketCheckLogTask.execute();

        this.notificationLayerService.sendNotificationGeneratedTicketCheckLog(ticketCheckOutEntity.getTicketCheckInEntity().getRequestCheckInEntity().getUserAccountEntity(),
                generateTicketCheckLogTask.getTicketCheckLogEntity(), ticketCheckOutEntity);

        this.messageLayerService.sendNotificationGeneratedTicketCheckLog(ticketCheckOutEntity.getTicketCheckInEntity().getRequestCheckInEntity().getUserAccountEntity(),
                generateTicketCheckLogTask.getTicketCheckLogEntity(), ticketCheckOutEntity);

        return "TrackID validated: " + generateTicketCheckLogTask.getTicketCheckLogEntity().getTicketCheckOutEntity().getTrackId();
    }

    private LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }
}
