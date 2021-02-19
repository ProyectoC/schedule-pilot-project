package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.constants.LoanProcessConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.AccountUserRepository;
import com.schedulepilot.core.repository.TicketCheckInRepository;
import com.schedulepilot.core.response.TicketCheckInResponse;
import com.schedulepilot.core.service.GlobalListDynamicService;
import com.schedulepilot.core.service.ItemService;
import com.schedulepilot.core.service.NotificationLayerService;
import com.schedulepilot.core.service.TicketCheckInService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import com.schedulepilot.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TicketCheckInServiceImp implements TicketCheckInService {

    private static final List<String> LIST_ATTRIBUTES = Arrays.asList(TicketCheckInEntity_.trackId.getName());

    @Autowired
    private TicketCheckInRepository ticketCheckInRepository;

    @Autowired
    private AccountUserRepository accountUserRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ItemService itemService;

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private NotificationLayerService notificationLayerService;

    @Override
    public TicketCheckInEntity save(TicketCheckInEntity ticketCheckInEntity) {
        ticketCheckInEntity.setIsActive(true);
        return this.ticketCheckInRepository.save(ticketCheckInEntity);
    }

    @Override
    public TicketCheckInEntity getByTrackIdentification(String trackIdLong) throws SchedulePilotException {
        Optional<TicketCheckInEntity> ticketCheckInEntityOptional = this.ticketCheckInRepository.findByTrackId(trackIdLong);
        if (ticketCheckInEntityOptional.isPresent()) {
            return ticketCheckInEntityOptional.get();
        }
        throw new SchedulePilotException("Ticket Check-In with trackId:" + trackIdLong + " Not Found");
    }

    @Override
    public PageResponseDto<TicketCheckInResponse> getAllTicketCheckIn(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        UserAccountEntity userAccountEntity = accountUserRepository.getOne(userAccountId);
        RolAccountEntity rolAccountEntity = userAccountEntity.getRolAccountEntity();
        if (rolAccountEntity.getName().equals("Super User") || rolAccountEntity.getName().equals("Registro y Control")) {
            userAccountId = null;
        }

        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, LIST_ATTRIBUTES);
        paginationAndOrderTask.execute();

        String trackIdTicket = parameters.getOrDefault("track_id_ticket", null);
        String trackIdRequest = parameters.getOrDefault("track_id_request", null);
        LocalDateTime deliveryDateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("delivery_date_start", null));
        LocalDateTime deliveryDateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("delivery_date_end", null));
        LocalDateTime returnDateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("return_date_start", null));
        LocalDateTime returnDateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("return_date_end", null));
        String itemName = parameters.getOrDefault("item_name", "");
        String status = parameters.getOrDefault("status", "");

        PageResponseDto<TicketCheckInResponse> pageResponse = new PageResponseDto<>();

        List<TicketCheckInResponse> list = new ArrayList<>();
        if (paginationAndOrderTask.getPageData() != null) {
            Page<TicketCheckInEntity> page = this.ticketCheckInRepository.findAllTicketCheckInPage(paginationAndOrderTask.getPageData(), userAccountId,
                    trackIdTicket, trackIdRequest, deliveryDateStart, deliveryDateEnd, returnDateStart, returnDateEnd, itemName, status);
            page.getContent().forEach(e -> list.add(TicketCheckInService.convertEntityToResponse(e)));
            pageResponse.build(list, page);
        } else {
            List<TicketCheckInEntity> ticketCheckInEntities = this.ticketCheckInRepository.findAllTicketCheckInSort(paginationAndOrderTask.getSortData(), userAccountId,
                    trackIdTicket, trackIdRequest, deliveryDateStart, deliveryDateEnd, returnDateStart, returnDateEnd, itemName, status);
            ticketCheckInEntities.forEach(e -> list.add(TicketCheckInService.convertEntityToResponse(e)));
            pageResponse.build(list);
        }
        return pageResponse;
    }

    @Override
    public List<TicketCheckInEntity> getAllExpiredTicketCheckIn() {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-15);
        return this.ticketCheckInRepository.findAllExpiredTicketCheckIn("GENERADO", localDateTime);
    }

    @Override
    @Async
    public void processExpiredTicketCheckIn(TicketCheckInEntity ticketCheckInEntity) throws SchedulePilotException {
        ItemEntity item = ticketCheckInEntity.getItemEntity();
        this.itemService.setEnable(item);

        ticketCheckInEntity.setTicketCheckStatusEntity(this.globalListDynamicService.getTicketCheckStatusOrException(LoanProcessConstants.EXPIRED_STATUS));
        this.save(ticketCheckInEntity);

        this.notificationLayerService.sendNotificationExpiredTicketCheckIn(ticketCheckInEntity);
    }
}
