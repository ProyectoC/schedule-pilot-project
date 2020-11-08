package com.schedulepilot.core.service.sequence;

import com.schedulepilot.core.repository.RequestCheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceServiceImp implements SequenceService {

    @Autowired
    private RequestCheckInRepository requestCheckInRepository;

    @Override
    public Long getRequestCheckInSequence() {
        return requestCheckInRepository.getRequestCheckInSequence();
    }
}
