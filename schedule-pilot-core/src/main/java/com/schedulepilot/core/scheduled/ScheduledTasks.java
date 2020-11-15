package com.schedulepilot.core.scheduled;

import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.RequestCheckInProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private RequestCheckInProductService requestCheckInProductService;

    /**
     * <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year> <command>
     */
    @Scheduled(fixedDelay = 1 * 60000, initialDelay = 1000)
    public void scheduleManageRequestCheckInProducts() {
        LOGGER.info("SCHEDULING REQUEST-CHECK-IN ---> GO STARTED! AT: {}", LocalDateTime.now());
        try {
            List<RequestCheckInProductEntity> listRequestCheckInProducts = this.requestCheckInProductService.getRequestCheckInProductForProcess();
            if (listRequestCheckInProducts.isEmpty()) {
                LOGGER.info("SCHEDULING REQUEST-CHECK-IN ---> GO FINISHED! AT: {}", LocalDateTime.now());
                return;
            }
            for (RequestCheckInProductEntity temp : listRequestCheckInProducts) {
                this.requestCheckInProductService.processRequestCheckInProduct(temp);
            }
        } catch (SchedulePilotException ex) {
            LOGGER.error("SCHEDULING REQUEST-CHECK-IN ---> GO WITH ERRORS! AT: {}, ERROR DESCRIPTION: {}",
                    LocalDateTime.now(), ex.getMessage());
        }
        LOGGER.info("SCHEDULING REQUEST-CHECK-IN ---> GO FINISHED! AT: {}", LocalDateTime.now());
    }

//    @Scheduled(cron = "${cron.expression}")
//    public void scheduleActivities() {
//        LOGGER.info("SCHEDULING TASKS ---> ACTIVITIES LEFT-DAYS TO GO STARTED! AT: {}", LocalDateTime.now());
//        try {
//            List<AlertActivityDto> resultActivities = this.activityService.getActivityWithDaysToGo(DAYS_LEFT_NOTIFICATION);
//            if (resultActivities.isEmpty()) {
//                LOGGER.info("SCHEDULING TASKS ---> ACTIVITIES LEFT-DAYS TO GO FINISHED! AT: {}", LocalDateTime.now());
//                return;
//            }
//            for (AlertActivityDto temp : resultActivities) {
//                this.notificationLayerService.sendNotificationActivityDaysLeft(temp);
//            }
//        } catch (AcquaBoardException ex) {
//            LOGGER.error("SCHEDULING TASKS ---> ACTIVITIES LEFT-DAYS TO GO WITH ERRORS! AT: {}, ERROR DESCRIPTION: {}",
//                    LocalDateTime.now(), ex.getMessage());
//        }
//        LOGGER.info("SCHEDULING TASKS ---> ACTIVITIES LEFT-DAYS TO GO FINISHED! AT: {}", LocalDateTime.now());
//    }

}
