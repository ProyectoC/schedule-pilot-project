package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewChartStatusOperationRepository extends JpaRepository<ViewChartStatusOperation, Long> {
}
