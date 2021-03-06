package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.constants.ItemConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.entities.model.ItemEntity;
import com.schedulepilot.core.entities.model.ItemEntity_;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.ItemRepository;
import com.schedulepilot.core.service.GlobalListDynamicService;
import com.schedulepilot.core.service.ItemService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ItemServiceImp implements ItemService {

    private static final List<String> LIST_ATTRIBUTES = Arrays.asList(ItemEntity_.name.getName());

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private GlobalListDynamicService globalListDynamicService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public PageResponseDto<ItemDto> getAll(Map<String, String> parameters) {
        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, LIST_ATTRIBUTES);
        paginationAndOrderTask.execute();

        String productIdStr = parameters.getOrDefault("productId", null);
        String itemName = parameters.getOrDefault("name", "");
        String serialName = parameters.getOrDefault("serial", "");
        String statusName = parameters.getOrDefault("status", "");

        Long productId = null;
        if (productIdStr != null)
            productId = Long.parseLong(productIdStr);

        PageResponseDto<ItemDto> pageResponse = new PageResponseDto<>();
        List<ItemDto> list = new ArrayList<>();
        if (paginationAndOrderTask.getPageData() != null) {
            Page<ItemEntity> page = this.itemRepository.findAllWithPage(paginationAndOrderTask.getPageData(), productId,
                    itemName, serialName, statusName);
            page.getContent().forEach(e -> list.add(ItemService.convertEntityToDTO(e)));
            pageResponse.build(list, page);
        } else {
            List<ItemEntity> itemEntities = this.itemRepository.findAllWithSort(paginationAndOrderTask.getSortData(), productId,
                    itemName, serialName, statusName);
            itemEntities.forEach(e -> list.add(ItemService.convertEntityToDTO(e)));
            pageResponse.build(list);
        }
        return pageResponse;
    }

    @Override
    public ItemEntity getByIdOrException(Long id) throws SchedulePilotException {
        Optional<ItemEntity> entity = itemRepository.findById(id);
        if (!entity.isPresent()) {
            throw new SchedulePilotException("Item Not Found");
        }
        return entity.get();
    }

    @Override
    public List<ItemEntity> getItemsEnable(Long productId) throws SchedulePilotException {
        return this.itemRepository.findByEnable(productId);
    }

    @Override
    public ItemEntity setOnLoan(ItemEntity itemEntity) throws SchedulePilotException {
        itemEntity.setItemStatusEntity(this.globalListDynamicService.getItemStatusOrException(ItemConstants.ON_LOAD_STATUS));
        return this.itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity setEnable(ItemEntity itemEntity) throws SchedulePilotException {
        itemEntity.setItemStatusEntity(this.globalListDynamicService.getItemStatusOrException(ItemConstants.ENABLE_STATUS));
        return this.itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity save(ItemEntity itemEntity) {
        itemEntity.setIsActive(true);
        return this.itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity update(ItemEntity itemEntity) {
        return this.itemRepository.save(itemEntity);
    }
}
