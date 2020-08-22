package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.LogAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAuditRepository extends JpaRepository<LogAuditEntity, Long> {
}
