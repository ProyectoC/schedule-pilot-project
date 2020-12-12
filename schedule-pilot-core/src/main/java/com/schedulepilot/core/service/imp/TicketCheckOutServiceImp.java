package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.RolAccountEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity_;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.AccountUserRepository;
import com.schedulepilot.core.repository.TicketCheckOutRepository;
import com.schedulepilot.core.response.TicketCheckOutResponse;
import com.schedulepilot.core.service.TicketCheckOutService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import com.schedulepilot.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TicketCheckOutServiceImp implements TicketCheckOutService {

    private static final List<String> LIST_ATTRIBUTES = Arrays.asList(TicketCheckOutEntity_.trackId.getName());

    @Autowired
    private TicketCheckOutRepository ticketCheckOutRepository;

    @Autowired
    private AccountUserRepository accountUserRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public TicketCheckOutEntity save(TicketCheckOutEntity ticketCheckOutEntity) {
        return this.ticketCheckOutRepository.save(ticketCheckOutEntity);
    }

    @Override
    public TicketCheckOutEntity getByTrackIdentification(String trackIdLong) throws SchedulePilotException {
        Optional<TicketCheckOutEntity> ticketCheckOutEntityOptional = this.ticketCheckOutRepository.findByTrackId(trackIdLong);
        if (ticketCheckOutEntityOptional.isPresent()) {
            return ticketCheckOutEntityOptional.get();
        }
        throw new SchedulePilotException("Ticket Check-Out with trackId:" + trackIdLong + " Not Found");
    }

    @Override
    public PageResponseDto<TicketCheckOutResponse> getAllTicketCheckOut(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        UserAccountEntity userAccountEntity = accountUserRepository.getOne(userAccountId);
        RolAccountEntity rolAccountEntity = userAccountEntity.getRolAccountEntity();
        if (rolAccountEntity.getName().equals("Super User") || rolAccountEntity.getName().equals("Registro y Control")) {
            userAccountId = null;
        }

        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, LIST_ATTRIBUTES);
        paginationAndOrderTask.execute();

        String trackIdOut = parameters.getOrDefault("track_id_out", null);
        String trackIdIn = parameters.getOrDefault("track_id_in", null);
        LocalDateTime deliveryDateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("delivery_date_start", null));
        LocalDateTime deliveryDateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("delivery_date_end", null));
        LocalDateTime returnDateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("return_date_start", null));
        LocalDateTime returnDateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("return_date_end", null));
        String itemName = parameters.getOrDefault("item_name", "");
        String status = parameters.getOrDefault("status", "");

        PageResponseDto<TicketCheckOutResponse> pageResponse = new PageResponseDto<>();

        List<TicketCheckOutResponse> list = new ArrayList<>();
        if (paginationAndOrderTask.getPageData() != null) {
            Page<TicketCheckOutEntity> page = this.ticketCheckOutRepository.findAllTicketCheckOutPage(paginationAndOrderTask.getPageData(), userAccountId,
                    trackIdOut, trackIdIn, deliveryDateStart, deliveryDateEnd, returnDateStart, returnDateEnd, itemName, status);
            page.getContent().forEach(e -> list.add(TicketCheckOutService.convertEntityToResponse(e)));
            pageResponse.build(list, page);
        } else {
            List<TicketCheckOutEntity> ticketCheckOutEntities = this.ticketCheckOutRepository.findAllTicketCheckOutSort(paginationAndOrderTask.getSortData(), userAccountId,
                    trackIdOut, trackIdIn, deliveryDateStart, deliveryDateEnd, returnDateStart, returnDateEnd, itemName, status);
            ticketCheckOutEntities.forEach(e -> list.add(TicketCheckOutService.convertEntityToResponse(e)));
            pageResponse.build(list);
        }
        return pageResponse;
    }
}
