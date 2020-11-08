package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.entities.model.ItemDetailEntity;
import com.schedulepilot.core.entities.model.ItemEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.ItemDetailRepository;
import com.schedulepilot.core.request.ItemCreateRequest;
import com.schedulepilot.core.request.ItemDeleteRequest;
import com.schedulepilot.core.request.ItemUpdateRequest;
import com.schedulepilot.core.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ManageItemServiceImp implements ManageItemService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDetailRepository itemDetailRepository;

    @Override
    public PageResponseDto<ItemDto> getAllItems(Map<String, String> parameters) throws SchedulePilotException {
        return this.itemService.getAll(parameters);
    }

    @Override
    public void createItem(ItemCreateRequest itemCreateRequest) throws SchedulePilotException {
        ItemDto itemDto = ItemService.convertRequestCreateToDTO(itemCreateRequest);
        ItemEntity itemEntity = ItemService.convertDTOToEntity(itemDto);
        List<ItemDetailEntity> itemDetailEntityList = itemEntity.getItemDetailEntityList();
        itemEntity.setItemDetailEntityList(new ArrayList<>());
        this.setItemDetails(itemEntity, itemDetailEntityList);
        this.itemService.save(itemEntity);
    }

    @Override
    public void updateItem(ItemUpdateRequest itemUpdateRequest) throws SchedulePilotException {
        ItemDto itemDto = ItemService.convertRequestUpdateToDTO(itemUpdateRequest);
        ItemEntity itemEntityActual = this.itemService.getByIdOrException(itemUpdateRequest.getId());
        ItemEntity itemEntity = ItemService.convertDTOToEntity(itemDto);
        itemEntity.setCreatedBy(itemEntityActual.getCreatedBy());
        itemEntity.setCreatedDate(itemEntityActual.getCreatedDate());
        List<ItemDetailEntity> itemDetailEntityList = itemEntity.getItemDetailEntityList();
        itemEntity.setItemDetailEntityList(new ArrayList<>());
        this.setItemDetails(itemEntity, itemDetailEntityList);
        this.itemService.update(itemEntity);
    }

    private void setItemDetails(ItemEntity itemEntity, List<ItemDetailEntity> itemDetailEntityList) {
        for (ItemDetailEntity itemDetailEntity : itemDetailEntityList) {
            if (itemEntity.getId() == null) {
                itemEntity.addItemDetail(itemDetailEntity);
                continue;
            }

            Optional<ItemDetailEntity> itemDetailOptionalTemp = this.itemDetailRepository
                    .findByKeyAndItemEntity_Id(itemDetailEntity.getKey(), itemEntity.getId());
            if (itemDetailOptionalTemp.isPresent()) {
                ItemDetailEntity itemDetailEntityTemp = itemDetailOptionalTemp.get();
                itemDetailEntityTemp.setValue(itemDetailEntity.getValue());
                itemEntity.addItemDetail(itemDetailEntityTemp);
            } else {
                itemEntity.addItemDetail(itemDetailEntity);
            }
        }
    }

    @Override
    public void deleteItem(ItemDeleteRequest itemDeleteRequest) throws SchedulePilotException {

    }
}
