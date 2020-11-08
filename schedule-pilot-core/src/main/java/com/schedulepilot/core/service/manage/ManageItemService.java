package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.request.ItemCreateRequest;
import com.schedulepilot.core.request.ItemDeleteRequest;
import com.schedulepilot.core.request.ItemUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ManageItemService {

    Logger LOGGER = LoggerFactory.getLogger(ManageItemService.class);

    PageResponseDto<ItemDto> getAllItems(Map<String, String> parameters) throws SchedulePilotException;

    void createItem(ItemCreateRequest itemCreateRequest) throws SchedulePilotException;

    void updateItem(ItemUpdateRequest itemUpdateRequest) throws SchedulePilotException;

    void deleteItem(ItemDeleteRequest itemDeleteRequest) throws SchedulePilotException;
}
