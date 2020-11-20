package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.RolAccountEntity;
import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.entities.model.TicketCheckInEntity_;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.AccountUserRepository;
import com.schedulepilot.core.repository.TicketCheckInRepository;
import com.schedulepilot.core.response.TicketCheckInResponse;
import com.schedulepilot.core.service.TicketCheckInService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
        if (!rolAccountEntity.getName().equals("Super User") || rolAccountEntity.getName().equals("Registro y Control")) {
            userAccountId = null;
        }

        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, LIST_ATTRIBUTES);
        paginationAndOrderTask.execute();

        String propertyName = parameters.getOrDefault("name", "");
        PageResponseDto<TicketCheckInResponse> pageResponse = new PageResponseDto<>();

        List<TicketCheckInResponse> list = new ArrayList<>();
        if (paginationAndOrderTask.getPageData() != null) {
            Page<TicketCheckInEntity> page = this.ticketCheckInRepository.findAllTicketCheckInPage(paginationAndOrderTask.getPageData(), userAccountId);
            page.getContent().forEach(e -> list.add(TicketCheckInService.convertEntityToResponse(e)));
            pageResponse.build(list, page);
        } else {
            List<TicketCheckInEntity> ticketCheckInEntities = this.ticketCheckInRepository.findAllTicketCheckInSort(paginationAndOrderTask.getSortData(), userAccountId);
            ticketCheckInEntities.forEach(e -> list.add(TicketCheckInService.convertEntityToResponse(e)));
            pageResponse.build(list);
        }
        return pageResponse;
    }
}
