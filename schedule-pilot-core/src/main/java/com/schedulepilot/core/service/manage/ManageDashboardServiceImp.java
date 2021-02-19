package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.entities.model.ViewChartDashboardLoans;
import com.schedulepilot.core.entities.model.ViewChartDashboardRequests;
import com.schedulepilot.core.entities.model.ViewChartStatusLoanMade;
import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import com.schedulepilot.core.entities.model.ViewChartStatusReturnMade;
import com.schedulepilot.core.response.dashboards.GeneralChart;
import com.schedulepilot.core.response.dashboards.ItemGeneralChart;
import com.schedulepilot.core.service.ViewChartDashboardLoansService;
import com.schedulepilot.core.service.ViewChartDashboardRequestsService;
import com.schedulepilot.core.service.ViewChartStatusLoanMadeService;
import com.schedulepilot.core.service.ViewChartStatusOperationService;
import com.schedulepilot.core.service.ViewChartStatusReturnMadeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class ManageDashboardServiceImp implements ManageDashboardService {

    private final ViewChartStatusOperationService viewChartStatusOperationService;
    private final ViewChartStatusLoanMadeService viewChartStatusLoanMadeService;
    private final ViewChartStatusReturnMadeService viewChartStatusReturnMadeService;

    private final ViewChartDashboardLoansService viewChartDashboardLoansService;
    private final ViewChartDashboardRequestsService viewChartDashboardRequestsService;

    @Override
    public GeneralChart getDashboardStatusOperation(Long userAccountId) {
        ViewChartStatusOperation statusOperation = this.viewChartStatusOperationService.getByUserAccountId(userAccountId);
        GeneralChart chart = new GeneralChart("Estado de Operaciones", "Reporte Actual", "bar",
                "Tipos", "Cantidad");
        chart.addLabel("Solicitudes");
        chart.addLabel("Prestamos");
        chart.addLabel("Devoluciones");
        ItemGeneralChart itemRequest = new ItemGeneralChart("Cantidad");
        if (statusOperation != null) {
            itemRequest.addItem(BigDecimal.valueOf(statusOperation.getRequests()));
            itemRequest.addItem(BigDecimal.valueOf(statusOperation.getTickets()));
            itemRequest.addItem(BigDecimal.valueOf(statusOperation.getRefunds()));
        }
        chart.addItemGeneral(itemRequest);

        return chart;
    }

    @Override
    public GeneralChart getDashboardStatusLoanMade(Long userAccountId) {
        List<ViewChartStatusLoanMade> list = this.viewChartStatusLoanMadeService.getByUserAccountId(userAccountId);
        GeneralChart chart = new GeneralChart("Estado de Prestamos", "Reporte Actual", "pie",
                "Tipos", "Cantidad");
        ItemGeneralChart itemRequest = new ItemGeneralChart("Cantidad");
        for (ViewChartStatusLoanMade temp : list) {
            chart.addLabel(temp.getName());
            itemRequest.addItem(BigDecimal.valueOf(temp.getLoans()));
        }
        chart.addItemGeneral(itemRequest);

        return chart;
    }

    @Override
    public GeneralChart getDashboardStatusReturnMade(Long userAccountId) {
        List<ViewChartStatusReturnMade> list = this.viewChartStatusReturnMadeService.getByUserAccountId(userAccountId);
        GeneralChart chart = new GeneralChart("Estado de Devoluciones", "Reporte Actual", "pie",
                "Tipos", "Cantidad");
        ItemGeneralChart itemRequest = new ItemGeneralChart("Cantidad");
        for (ViewChartStatusReturnMade temp : list) {
            chart.addLabel(temp.getName());
            itemRequest.addItem(BigDecimal.valueOf(temp.getReturns()));
        }
        chart.addItemGeneral(itemRequest);

        return chart;
    }

    @Override
    public GeneralChart getDashboardPrincipal(Long userAccountId) {
        List<ViewChartDashboardLoans> listLoans = this.viewChartDashboardLoansService.getAll();
        List<ViewChartDashboardRequests> listRequests = this.viewChartDashboardRequestsService.getAll();
        GeneralChart chart = new GeneralChart("Solicitudes y Prestamos", "Reporte Actual", "line",
                "Fecha", "Cantidad");
        ItemGeneralChart itemLoan = new ItemGeneralChart("Prestamos");
        for (ViewChartDashboardLoans temp : listLoans) {
            chart.addLabel(temp.getDate());
            itemLoan.addItem(BigDecimal.valueOf(temp.getLoans()));
        }
        chart.addItemGeneral(itemLoan);
        ItemGeneralChart itemRequest = new ItemGeneralChart("Solicitudes");
        for (ViewChartDashboardRequests temp : listRequests) {
            chart.addLabel(temp.getDate());
            itemRequest.addItem(BigDecimal.valueOf(temp.getRequests()));
        }
        chart.addItemGeneral(itemRequest);

        return chart;

    }
}
