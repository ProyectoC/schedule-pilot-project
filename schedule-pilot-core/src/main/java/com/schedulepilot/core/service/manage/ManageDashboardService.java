package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.response.dashboards.GeneralChart;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ManageDashboardService {

    // Preview
    GeneralChart getDashboardStatusOperation(Long userAccountId);

    GeneralChart getDashboardStatusLoanMade(Long userAccountId);

    GeneralChart getDashboardStatusReturnMade(Long userAccountId);

    GeneralChart getDashboardPrincipal(Long userAccountId);

    // Reports
    GeneralChart getDashboardLoanProduct(Map<String, String> parameters, Long userAccountId);

    GeneralChart getDashboardRequestVsLoans(Map<String, String> parameters, Long userAccountId);

    GeneralChart getDashboardPenaltySummary(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException;
}
