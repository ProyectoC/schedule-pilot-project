package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.response.dashboards.GeneralChart;
import org.springframework.stereotype.Service;

@Service
public interface ManageDashboardService {

    GeneralChart getDashboardStatusOperation(Long userAccountId);

    GeneralChart getDashboardStatusLoanMade(Long userAccountId);

    GeneralChart getDashboardStatusReturnMade(Long userAccountId);

    GeneralChart getDashboardPrincipal(Long userAccountId);
}
