package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.ViewChartDashboardRequests;
import com.schedulepilot.core.repository.ViewChartDashboardRequestsRepository;
import com.schedulepilot.core.service.ViewChartDashboardRequestsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ViewChartDashboardRequestsServiceImp implements ViewChartDashboardRequestsService {

    private final ViewChartDashboardRequestsRepository viewChartDashboardRequestsRepository;

    @Override
    public List<ViewChartDashboardRequests> getAll() {
        return this.viewChartDashboardRequestsRepository.findAll();
    }
}
