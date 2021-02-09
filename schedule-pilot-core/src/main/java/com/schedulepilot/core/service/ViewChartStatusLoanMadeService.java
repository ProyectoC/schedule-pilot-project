package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ViewChartStatusLoanMade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ViewChartStatusLoanMadeService {

    List<ViewChartStatusLoanMade> getByUserAccountId(Long userAccountId);
}
