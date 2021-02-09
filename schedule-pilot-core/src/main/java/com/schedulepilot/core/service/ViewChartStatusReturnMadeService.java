package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ViewChartStatusReturnMade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ViewChartStatusReturnMadeService {

    List<ViewChartStatusReturnMade> getByUserAccountId(Long userAccountId);
}
