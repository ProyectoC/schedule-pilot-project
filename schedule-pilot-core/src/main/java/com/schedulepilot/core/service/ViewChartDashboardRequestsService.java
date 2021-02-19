package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ViewChartDashboardRequests;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ViewChartDashboardRequestsService {

    List<ViewChartDashboardRequests> getAll();
}
