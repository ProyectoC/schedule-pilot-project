package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ViewChartDashboardLoans;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ViewChartDashboardLoansService {

    List<ViewChartDashboardLoans> getAll();
}
