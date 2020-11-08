package com.schedulepilot.core.service.loanprocess;

import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.LoanProcessException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.CheckInProductRequest;
import com.schedulepilot.core.request.CheckInRequest;
import com.schedulepilot.core.service.GlobalListDinamicService;
import com.schedulepilot.core.service.ProductService;
import com.schedulepilot.core.service.RequestCheckInService;
import com.schedulepilot.core.service.UserAccountService;
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
            requestCheckInProductEntity.setCount(checkInProductRequest.getCount());
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


    private LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }
}
