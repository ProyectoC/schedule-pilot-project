package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ItemDetailEntity;
import com.schedulepilot.core.entities.model.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemDetailRepository extends JpaRepository<ItemDetailEntity, Long> {

    Optional<ItemDetailEntity> findByKeyAndItemEntity_Id(String key, Long itemEntityId);
}
