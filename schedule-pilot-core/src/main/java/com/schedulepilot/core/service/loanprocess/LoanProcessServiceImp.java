package com.schedulepilot.core.service.loanprocess;

import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.LoanProcessException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.CheckInProductRequest;
import com.schedulepilot.core.request.CheckInRequest;
import com.schedulepilot.core.request.CheckOutRequest;
import com.schedulepilot.core.service.*;
import com.schedulepilot.core.service.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LoanProcessServiceImp implements LoanProcessService {

    private static final String REQUESTED_STATUS = "SOLICITADO";
    private static final String GENERATED_STATUS = "GENERADO";

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private GlobalListDinamicService globalListDinamicService;

    @Autowired
    private RequestCheckInService requestCheckInService;

    @Autowired
    private TicketCheckInService ticketCheckInService;

    @Autowired
    private TicketCheckOutService ticketCheckOutService;

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

            ProductRequestStatusEntity productRequestStatusEntity = this.globalListDinamicService.getProductRequestStatusOrException(REQUESTED_STATUS);
            requestCheckInProductEntity.setProductRequestStatusEntity(productRequestStatusEntity);
            RequestCheckInProductId requestCheckInProductId = new RequestCheckInProductId(productEntity);
            requestCheckInProductEntity.setRequestCheckInProductId(requestCheckInProductId);

            requestCheckInEntity.addProductRole(requestCheckInProductEntity);
        }
        this.requestCheckInService.save(requestCheckInEntity);
        return "TrackID: " + trackId;
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

        // TODO: Validar fecha actual de retiro debe ser igual o maximo pasarse por 15 minutos de la fecha acordada. De lo contrario
        /**
         * Debe cambiar el estado del ticket check int a VENCIDO. El job debera encontrar este ticket vencido y multarlo.
         */


        // TODO: Actualizar el ticket check in cambiarlo de estado a REDIMIDO


        // Generate track Identificator.
        Long trackId = this.sequenceService.getTicketCheckOutSequence();

        TicketCheckOutEntity ticketCheckOutEntity = new TicketCheckOutEntity();
        ticketCheckOutEntity.setTrackId(trackId);
        ticketCheckOutEntity.setTicketCheckInEntity(ticketCheckInEntity);
        ticketCheckOutEntity.setTicketCheckStatusEntity(this.globalListDinamicService.getTicketCheckStatusOrException(GENERATED_STATUS));
        ticketCheckOutEntity.setUserAccountEntity(userAccountEntity);

        this.ticketCheckOutService.save(ticketCheckOutEntity);
        return "TrackID: " + trackId;
    }


    private LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }
}
