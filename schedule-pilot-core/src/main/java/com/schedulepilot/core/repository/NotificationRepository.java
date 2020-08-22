package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.NotificationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {
}
