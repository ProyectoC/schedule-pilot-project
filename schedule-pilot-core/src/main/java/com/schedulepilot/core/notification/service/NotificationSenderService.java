package com.schedulepilot.core.notification.service;

import com.schedulepilot.core.dto.model.NotificationDto;
import org.springframework.stereotype.Service;

@Service
public interface NotificationSenderService {

    void sendValidationNotification(NotificationDto notificationDto);

    void sendInformationNotification(NotificationDto notificationDto);

    void sendInformationNotificationNotEmail(NotificationDto notificationDto);

    void sendSuccessNotification(NotificationDto notificationDto);

    void sendWarningNotification(NotificationDto notificationDto);

    void sendErrorNotification(NotificationDto notificationDto);

    void sendAlertNotification(NotificationDto notificationDto);
}
