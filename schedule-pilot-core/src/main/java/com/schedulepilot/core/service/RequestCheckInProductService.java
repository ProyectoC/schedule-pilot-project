package com.schedulepilot.core.service;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.RequestCheckInResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RequestCheckInProductService {

    ModelMapper modelMapper = new ModelMapper();

    static RequestCheckInResponse convertEntityToResponse(RequestCheckInProductEntity entity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        RequestCheckInResponse requestCheckInResponse = modelMapper.map(entity, RequestCheckInResponse.class);
        requestCheckInResponse.setProductName(entity.getRequestCheckInProductId().getProductEntity().getName());
        ;
        requestCheckInResponse.setTrackId(entity.getRequestCheckInProductId().getRequestCheckInEntity().getTrackId());
        return requestCheckInResponse;
    }

    List<RequestCheckInProductEntity> getRequestCheckInProductForProcess() throws SchedulePilotException;

    PageResponseDto<RequestCheckInResponse> getRequestCheckInProductResponse(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;

    RequestCheckInProductEntity save(RequestCheckInProductEntity requestCheckInProductEntity);

    void processRequestCheckInProduct(RequestCheckInProductEntity requestCheckInProductEntity) throws SchedulePilotException;
}
