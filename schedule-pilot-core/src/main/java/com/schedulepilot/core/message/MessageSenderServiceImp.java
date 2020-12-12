package com.schedulepilot.core.message;

import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.NotificationLayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderServiceImp implements MessageSenderService {

    private final Logger LOGGER = LoggerFactory.getLogger(MessageSenderServiceImp.class);
    private final MessageService messageService;

    public MessageSenderServiceImp(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void sendInformationMessage(String message, String phone) {
        try {
            this.messageService.sendMessage(phone, "SchedulePilot - Aviso: " + message);
        } catch (SchedulePilotException ex) {
            LOGGER.error("Could not send information message. Error: {}", ex.getMessage());
        }

    }

    @Override
    public void sendWarningMessage(String message, String phone) {
        try {
            this.messageService.sendMessage(phone, "SchedulePilot - Atención: " + message);
        } catch (SchedulePilotException ex) {
            LOGGER.error("Could not send information warning. Error: {}", ex.getMessage());
        }
    }
}
