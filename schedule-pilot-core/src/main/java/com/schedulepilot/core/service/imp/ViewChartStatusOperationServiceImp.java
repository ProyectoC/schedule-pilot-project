package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import com.schedulepilot.core.repository.ViewChartStatusOperationRepository;
import com.schedulepilot.core.service.ViewChartStatusOperationService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ViewChartStatusOperationServiceImp implements ViewChartStatusOperationService {

    private final ViewChartStatusOperationRepository viewChartStatusOperationRepository;

    @Override
    public ViewChartStatusOperation getByUserAccountId(Long userAccountId) {
        return this.viewChartStatusOperationRepository.findByUserAccountId(userAccountId).orElseThrow(() -> new SchedulingException("Chart not found with userAccountId: " + userAccountId));
    }
}
