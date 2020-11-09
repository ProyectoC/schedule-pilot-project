package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestCheckInProductService {

    List<RequestCheckInProductEntity> getRequestCheckInProductForProcess() throws SchedulePilotException;

    RequestCheckInProductEntity save(RequestCheckInProductEntity requestCheckInProductEntity);

    void processRequestCheckInProduct(RequestCheckInProductEntity requestCheckInProductEntity) throws SchedulePilotException;
}
