package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.message.MessageSenderService;
import com.schedulepilot.core.service.MessageLayerService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessageLayerServiceImp implements MessageLayerService {

    private final MessageSenderService messageSenderService;

    public MessageLayerServiceImp(MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    @Async
    @Override
    public void sendNotificationProductNotEnable(UserAccountEntity userAccountEntity, ProductEntity productEntity, RequestCheckInEntity requestCheckInEntity) {

        String phoneCode = userAccountEntity.getCountryEntity().getPhoneCode();
        String phoneNumber = userAccountEntity.getPhoneNumber();
        String phoneNumberUser = "+" + phoneCode + phoneNumber;

        String message = "Estimado(a) " + userAccountEntity.getFirstName() + " " +
                "dando seguimiento al proceso iniciado con numero de registro: " +
                requestCheckInEntity.getTrackId() +
                ", te informamos que le recurso: " +
                productEntity.getName() + " " +
                "no se encuentra habilitado para realizar el prestamo.";
        this.messageSenderService.sendWarningMessage(message, phoneNumberUser);
    }

    @Async
    @Override
    public void sendNotificationNotFoundProduct(UserAccountEntity userAccountEntity, ProductEntity productEntity, RequestCheckInEntity requestCheckInEntity) {

        String phoneCode = userAccountEntity.getCountryEntity().getPhoneCode();
        String phoneNumber = userAccountEntity.getPhoneNumber();
        String phoneNumberUser = "+" + phoneCode + phoneNumber;

        String message = "Estimado(a) " + userAccountEntity.getFirstName() + " " +
                "dando seguimiento al proceso iniciado con numero de registro: " +
                requestCheckInEntity.getTrackId() +
                ", te informamos que le recurso: " +
                productEntity.getName() + " " +
                "no se encuentra disponible para realizar el prestamo.";
        this.messageSenderService.sendWarningMessage(message, phoneNumberUser);
    }

    @Async
    @Override
    public void sendNotificationGeneratedTicketCheckIn(UserAccountEntity userAccountEntity, TicketCheckInEntity ticketCheckInEntity, RequestCheckInEntity requestCheckInEntity) {

        String phoneCode = userAccountEntity.getCountryEntity().getPhoneCode();
        String phoneNumber = userAccountEntity.getPhoneNumber();
        String phoneNumberUser = "+" + phoneCode + phoneNumber;

        String message = "Estimado(a) " + userAccountEntity.getFirstName() + " " +
                "dando seguimiento al proceso iniciado con numero de registro: " +
                requestCheckInEntity.getTrackId() +
                ", te enviamos el ticket del recurso: " +
                ticketCheckInEntity.getItemEntity().getName() + " " +
                "para que te acerques a la universidad y puedas retirarlo.";
        this.messageSenderService.sendInformationMessage(message, phoneNumberUser);
    }

    @Async
    @Override
    public void sendNotificationGeneratedTicketCheckOut(UserAccountEntity userAccountEntity, TicketCheckOutEntity ticketCheckOutEntity, TicketCheckInEntity ticketCheckInEntity) {
        String message = String.format("Estimado(a) %s dando seguimiento al proceso iniciado para el TICKET-CHECK-IN " +
                        "con numero de registro: %s te enviamos el ticket del recurso %s, para que te acerques a la universidad y " +
                        "puedas realizar la entrega del recurso prestado.", userAccountEntity.getFirstName(),
                ticketCheckInEntity.getTrackId(), ticketCheckInEntity.getItemEntity().getName());

        String phoneCode = userAccountEntity.getCountryEntity().getPhoneCode();
        String phoneNumber = userAccountEntity.getPhoneNumber();
        String phoneNumberUser = "+" + phoneCode + phoneNumber;

        this.messageSenderService.sendInformationMessage(message, phoneNumberUser);
    }

    @Async
    @Override
    public void sendNotificationGeneratedTicketCheckLog(UserAccountEntity userAccountEntity, TicketCheckLogEntity ticketCheckLogEntity, TicketCheckOutEntity ticketCheckOutEntity) {
        String message = String.format("Estimado(a) %s dando seguimiento al proceso iniciado para el TICKET-CHECK-OUT con numero de registro: %s, te enviamos el ticket con el resumen del proceso realizado " +
                        "esperamos que sigas utilizando los recursos que la universidad dispone para ti.", userAccountEntity.getFirstName(),
                ticketCheckOutEntity.getTrackId());

        String phoneCode = userAccountEntity.getCountryEntity().getPhoneCode();
        String phoneNumber = userAccountEntity.getPhoneNumber();
        String phoneNumberUser = "+" + phoneCode + phoneNumber;

        this.messageSenderService.sendInformationMessage(message, phoneNumberUser);
    }
}
