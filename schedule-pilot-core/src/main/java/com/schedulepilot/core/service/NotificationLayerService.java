package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.dto.model.UserNotificationDto;
import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public interface NotificationLayerService {

    Logger LOGGER = LoggerFactory.getLogger(NotificationLayerService.class);

    // USERS
    void sendNotificationCreateUserAccount(UserAccountDto userAccountDto);

    void sendNotificationActivationUserAccount(UserAccountDto userAccountDto);

    void sendNotificationForgotPasswordUserAccount(UserAccountDto userAccountDto, String newPassword);

    void sendNotificationChangePasswordUserAccount(UserAccountDto userAccountDto);

    // Ticket Check In
    void sendNotificationProductNotEnable(UserAccountEntity userAccountEntity, ProductEntity productEntity, RequestCheckInEntity requestCheckInEntity);

    void sendNotificationNotFoundProduct(UserAccountEntity userAccountEntity, ProductEntity productEntity, RequestCheckInEntity requestCheckInEntity);

    void sendNotificationGeneratedTicketCheckIn(UserAccountEntity userAccountEntity, TicketCheckInEntity ticketCheckInEntity, RequestCheckInEntity requestCheckInEntity);

    void sendNotificationExpiredTicketCheckIn(TicketCheckInEntity ticketCheckInEntity);

    void sendNotificationGeneratedTicketCheckOut(UserAccountEntity userAccountEntity, TicketCheckOutEntity ticketCheckOutEntity, TicketCheckInEntity ticketCheckInEntity);

    void sendNotificationExpiredTicketCheckOut(TicketCheckOutEntity ticketCheckOutEntity, PenaltyCheckOut penaltyCheckOut);

    void sendNotificationGeneratedTicketCheckLog(UserAccountEntity userAccountEntity, TicketCheckLogEntity ticketCheckLogEntity, TicketCheckOutEntity ticketCheckOutEntity);

//    void sendNotificationVerificationUser(com.acqua.board.coremodule.dto.UserSecurityDTO userSecurityDTO);
//
//    void sendNotificationRestorePasswordUser(com.acqua.board.coremodule.dto.UserSecurityDTO userSecurityDTO,
//                                             String newPassword);
//
//    void sendNotificationChangePasswordUser(com.acqua.board.coremodule.dto.UserSecurityDTO userSecurityDTO);
//
//    // PROJECTS
//    void sendNotificationProjectCreation(UserSecurityDTO userCreator, ProjectDto project, NotificationType type);
//
//    void sendNotificationProjectUpdate(ProjectDto project, List<UserSecurityDTO> listUsers);
//
//    // SPRINTS
//    void sendNotificationSprintCreation(UserSecurityDTO userCreator, SprintDto sprintDto);
//
//    void sendNotificationSprintUpdate(UserSecurityDTO userCreator, SprintDto sprintDto);
//
//    void sendNotificationSprintDaysLeft(AlertSprintDto alertSprintDto);
//
//    // REQUEST
//    void sendNotificationRequestCreation(UserSecurityDTO userCreator, RequestDto requestDto);
//
//    void sendNotificationRequestUpdate(UserSecurityDTO userCreator, RequestDto requestDto);
//
//    // ACTIVITIES
//    void sendNotificationActivityCreation(ActivityDto activityDto);
//
//    void sendNotificationAssignedActivity(ActivityDto activityDto);
//
//    void sendNotificationChangeStatusActivity(StatusDto actualStatus, ActivityStatusLogDto activityStatus);
//
//    void sendNotificationActivityDaysLeft(AlertActivityDto alertActivityDto);
//
//    // ERROR - ACTIVITIES
//    void sendNotificationErrorActivity(ErrorActivityDto errorActivityDto);
//
//    // GET NOTIFICATIONS
//    GroupNotificationLightDto getNotificationDontReceivedByUser(Long idUser) throws AcquaBoardException;
//
//    List<NotificationLightDto> getNotificationDontNotifiedByUser(Long idUser) throws AcquaBoardException;
//
//    String setListNotificationsReceived(List<NotificationLightDto> listNotifications) throws AcquaBoardException;

    static String matchParametersToFileTemplate(String urlTemplate, Map<String, String> parameters) throws SchedulePilotException {
        try {
            String emailTemplate = CommonUtil.readFile(urlTemplate);
            for (Map.Entry<String, String> pair : parameters.entrySet()) {
                emailTemplate = emailTemplate.replace(pair.getKey(), pair.getValue());
            }
            return emailTemplate;
        } catch (IOException e) {
            throw new SchedulePilotException("The email template could not be read.");
        }
    }

    static String matchParametersToTemplate(String template, Map<String, String> parameters) {
        for (Map.Entry<String, String> pair : parameters.entrySet()) {
            template = template.replace(pair.getKey(), pair.getValue());
        }
        return template;
    }

    static List<UserNotificationDto> getUsersNotifications(UserAccountDto user) {
        List<UserNotificationDto> list = new ArrayList<>();
        UserNotificationDto userNotification = new UserNotificationDto();
        userNotification.setAccountUserEntity(user);
        list.add(userNotification);
        return list;
    }

    static List<UserNotificationDto> getUsersNotifications(List<UserAccountDto> users) {
        List<UserNotificationDto> list = new ArrayList<>();
        for (UserAccountDto user : users) {
            UserNotificationDto userNotification = new UserNotificationDto();
            userNotification.setAccountUserEntity(user);
            list.add(userNotification);
        }
        return list;
    }
}
