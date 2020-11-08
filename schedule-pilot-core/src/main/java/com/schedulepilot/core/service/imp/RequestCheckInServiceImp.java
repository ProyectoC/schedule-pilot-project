package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.RequestCheckInEntity;
import com.schedulepilot.core.repository.RequestCheckInRepository;
import com.schedulepilot.core.service.RequestCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestCheckInServiceImp implements RequestCheckInService {

    @Autowired
    private RequestCheckInRepository requestCheckInRepository;

    @Override
    public RequestCheckInEntity save(RequestCheckInEntity requestCheckInEntity) {
        return this.requestCheckInRepository.save(requestCheckInEntity);
    }
}
