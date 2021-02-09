package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ViewChartDashboardLoans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewChartDashboardLoansRepository extends JpaRepository<ViewChartDashboardLoans, Long> {
}
