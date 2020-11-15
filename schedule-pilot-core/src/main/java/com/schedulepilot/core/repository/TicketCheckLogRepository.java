package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketCheckLogRepository extends JpaRepository<TicketCheckLogEntity, Long> {
}
