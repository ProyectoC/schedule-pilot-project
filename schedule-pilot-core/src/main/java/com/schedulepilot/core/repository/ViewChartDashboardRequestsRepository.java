package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ViewChartDashboardRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewChartDashboardRequestsRepository extends JpaRepository<ViewChartDashboardRequests, Long> {

}
