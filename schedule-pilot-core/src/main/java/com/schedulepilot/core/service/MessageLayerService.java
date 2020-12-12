package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.*;
import org.springframework.stereotype.Service;

@Service
public interface MessageLayerService {

    void sendNotificationProductNotEnable(UserAccountEntity userAccountEntity, ProductEntity productEntity,
                                          RequestCheckInEntity requestCheckInEntity);
    
    void sendNotificationNotFoundProduct(UserAccountEntity userAccountEntity, ProductEntity productEntity,
                                         RequestCheckInEntity requestCheckInEntity);

    void sendNotificationGeneratedTicketCheckIn(UserAccountEntity userAccountEntity, TicketCheckInEntity ticketCheckInEntity,
                                                RequestCheckInEntity requestCheckInEntity);

    void sendNotificationGeneratedTicketCheckOut(UserAccountEntity userAccountEntity, TicketCheckOutEntity ticketCheckOutEntity,
                                                 TicketCheckInEntity ticketCheckInEntity);

    void sendNotificationGeneratedTicketCheckLog(UserAccountEntity userAccountEntity, TicketCheckLogEntity ticketCheckLogEntity,
                                                 TicketCheckOutEntity ticketCheckOutEntity);
}
