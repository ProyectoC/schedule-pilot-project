package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ViewChartStatusOperationService {

    List<ViewChartStatusOperation> getAll();
}
