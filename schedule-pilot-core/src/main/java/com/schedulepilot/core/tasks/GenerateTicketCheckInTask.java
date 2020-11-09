package com.schedulepilot.core.tasks;

import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.GlobalListDinamicService;
import com.schedulepilot.core.service.ItemService;
import com.schedulepilot.core.service.NotificationLayerService;
import com.schedulepilot.core.service.TicketCheckInService;
import com.schedulepilot.core.service.sequence.SequenceService;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Scope("prototype")
public class GenerateTicketCheckInTask {

    // Constants
    private static final Logger CLASS_LOGGER = LogManager.getLogger(GenerateTicketCheckInTask.class);
    private static final String GENERATED_STATUS = "GENERADO";

    @Autowired
    private ItemService itemService;

    @Autowired
    private TicketCheckInService ticketCheckInService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private GlobalListDinamicService globalListDinamicService;

    private final RequestCheckInProductEntity requestCheckInProductEntity;
    private ItemEntity item;
    private ProductRolEntity productRolEntity;

    @Getter
    private TicketCheckInEntity ticketCheckInEntity;

    @Getter
    private boolean notFoundItem;

    @Getter
    private boolean notEnableProduct;

    public GenerateTicketCheckInTask(RequestCheckInProductEntity requestCheckInProductEntity) {
        this.requestCheckInProductEntity = requestCheckInProductEntity;
    }

    public void execute() throws SchedulePilotException {
        CLASS_LOGGER.info("Init Task");
        UserAccountEntity userAccountEntity = requestCheckInProductEntity.getRequestCheckInProductId()
                .getRequestCheckInEntity().getUserAccountEntity();

        RolAccountEntity rolAccountFromUserRequest = userAccountEntity.getRolAccountEntity();
        ProductEntity productEntity = requestCheckInProductEntity.getRequestCheckInProductId().getProductEntity();

        this.productRolEntity = this.validateProductRol(productEntity.getProductRolEntityList(), rolAccountFromUserRequest);
        if (productRolEntity != null) {
            List<ItemEntity> listItems = this.itemService.getItemsEnable(productEntity.getId());
            if (listItems.isEmpty()) {
                this.notFoundItem = true;
            } else {
                this.item = listItems.get(0);
                this.generateTicketCheckIn();
                this.item = this.itemService.setOnLoan((this.item));
            }
        } else {
            this.notEnableProduct = true;
        }
        CLASS_LOGGER.info("End Task");
    }

    private void generateTicketCheckIn() throws SchedulePilotException {
        this.ticketCheckInEntity = new TicketCheckInEntity();
        Long trackId = this.sequenceService.getTicketCheckInSequence();
        ticketCheckInEntity.setTrackId(trackId.toString());
        ticketCheckInEntity.setItemEntity(item);
        ticketCheckInEntity.setRequestCheckInEntity(requestCheckInProductEntity.getRequestCheckInProductId().getRequestCheckInEntity());
        ticketCheckInEntity.setTicketCheckStatusEntity(this.globalListDinamicService.getTicketCheckStatusOrException(GENERATED_STATUS));
        LocalDateTime loanDate = requestCheckInProductEntity.getLoanDate();
        ticketCheckInEntity.setDeliveryDate(loanDate);
        ticketCheckInEntity.setReturnDate(loanDate.plusSeconds(productRolEntity.getLoanTime()));
        this.ticketCheckInService.save(ticketCheckInEntity);
    }

    private ProductRolEntity validateProductRol(List<ProductRolEntity> listProductRoles, RolAccountEntity rolAccountFromUserRequest) {
        ProductRolEntity productRolEntity = null;
        for (ProductRolEntity productRolEntityTemp : listProductRoles) {
            if (productRolEntityTemp.getProductRolId().getRolAccountEntity().getId().equals(rolAccountFromUserRequest.getId())) {
                productRolEntity = productRolEntityTemp;
                break;
            }
        }
        return productRolEntity;
    }


}
