package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.RequestCheckInEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

@Service
public interface RequestCheckInService {

    RequestCheckInEntity save(RequestCheckInEntity requestCheckInProductEntity);
}
