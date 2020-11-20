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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
        if (!rolAccountEntity.getName().equals("Super User") || rolAccountEntity.getName().equals("Registro y Control")) {
            userAccountId = null;
        }

        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, LIST_ATTRIBUTES);
        paginationAndOrderTask.execute();

        String propertyName = parameters.getOrDefault("name", "");
        PageResponseDto<TicketCheckOutResponse> pageResponse = new PageResponseDto<>();

        List<TicketCheckOutResponse> list = new ArrayList<>();
        if (paginationAndOrderTask.getPageData() != null) {
            Page<TicketCheckOutEntity> page = this.ticketCheckOutRepository.findAllTicketCheckOutPage(paginationAndOrderTask.getPageData(), userAccountId);
            page.getContent().forEach(e -> list.add(TicketCheckOutService.convertEntityToResponse(e)));
            pageResponse.build(list, page);
        } else {
            List<TicketCheckOutEntity> ticketCheckOutEntities = this.ticketCheckOutRepository.findAllTicketCheckOutSort(paginationAndOrderTask.getSortData(), userAccountId);
            ticketCheckOutEntities.forEach(e -> list.add(TicketCheckOutService.convertEntityToResponse(e)));
            pageResponse.build(list);
        }
        return pageResponse;
    }
}
