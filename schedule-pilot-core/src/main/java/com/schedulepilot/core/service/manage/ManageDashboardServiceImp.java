package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import com.schedulepilot.core.service.ViewChartStatusOperationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ManageDashboardServiceImp implements ManageDashboardService {

    private final ViewChartStatusOperationService viewChartStatusOperationService;

    @Override
    public List<ViewChartStatusOperation> getAllStatusOperation() {
        return this.viewChartStatusOperationService.getAll();
    }
}
