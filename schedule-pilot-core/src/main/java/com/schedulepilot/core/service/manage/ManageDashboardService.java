package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManageDashboardService {

    List<ViewChartStatusOperation> getAllStatusOperation();
}
