package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.ViewChartDashboardLoans;
import com.schedulepilot.core.repository.ViewChartDashboardLoansRepository;
import com.schedulepilot.core.service.ViewChartDashboardLoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ViewChartDashboardLoansServiceImp implements ViewChartDashboardLoansService {

    private final ViewChartDashboardLoansRepository viewChartDashboardLoansRepository;

    @Override
    public List<ViewChartDashboardLoans> getAll() {
        return this.viewChartDashboardLoansRepository.findAll();
    }
}
