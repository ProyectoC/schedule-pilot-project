package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketCheckStatusRepository extends JpaRepository<TicketCheckStatusEntity, Long> {
}
