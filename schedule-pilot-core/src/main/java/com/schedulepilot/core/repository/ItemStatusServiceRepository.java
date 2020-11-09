package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ItemStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStatusServiceRepository extends JpaRepository<ItemStatusEntity, Long> {
}
