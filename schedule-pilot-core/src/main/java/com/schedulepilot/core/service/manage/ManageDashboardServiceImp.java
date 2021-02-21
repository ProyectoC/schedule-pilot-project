package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.entities.model.*;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.queryresult.IPenaltySummaryResult;
import com.schedulepilot.core.service.imp.NativeQueryService;
import com.schedulepilot.core.repository.queryresult.ILoanProductResult;
import com.schedulepilot.core.response.dashboards.GeneralChart;
import com.schedulepilot.core.response.dashboards.ItemGeneralChart;
import com.schedulepilot.core.service.*;
import com.schedulepilot.core.util.CommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ManageDashboardServiceImp implements ManageDashboardService {

    private final ViewChartStatusOperationService viewChartStatusOperationService;
    private final ViewChartStatusLoanMadeService viewChartStatusLoanMadeService;
    private final ViewChartStatusReturnMadeService viewChartStatusReturnMadeService;
    private final ViewChartDashboardLoansService viewChartDashboardLoansService;
    private final ViewChartDashboardRequestsService viewChartDashboardRequestsService;
    private final ViewChartLoanProductService viewChartLoanProductService;
    private final UserAccountService userAccountService;
    private final NativeQueryService nativeQueryService;
    private final PenaltyCheckOutService penaltyCheckOutService;

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

    @Override
    public GeneralChart getDashboardLoanProduct(Map<String, String> parameters, Long userAccountId) {
        LocalDateTime dateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("date_start", null));
        LocalDateTime dateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("date_end", null));
        List<ILoanProductResult> result;
        if (this.userAccountService.isAdminUser(userAccountId)) {
            result = this.viewChartLoanProductService.getCountLoanProduct(dateStart, dateEnd);
        } else {
            result = this.viewChartLoanProductService.getCountLoanProductByUserAccount(userAccountId, dateStart, dateEnd);
        }
        GeneralChart chart = new GeneralChart("Prestamo de Recursos", "Reporte Actual", "bar",
                "Recurso", "Cantidad");
        ItemGeneralChart itemRequest = new ItemGeneralChart("Cantidad");
        for (ILoanProductResult temp : result) {
            chart.addLabel(temp.getProductName());
            itemRequest.addItem(BigDecimal.valueOf(temp.getTotalLoans()));
        }
        chart.addItemGeneral(itemRequest);

        return chart;
    }

    @Override
    public GeneralChart getDashboardRequestVsLoans(Map<String, String> parameters, Long userAccountId) {
        LocalDateTime dateStart = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("date_start", null));
        LocalDateTime dateEnd = CommonUtil.convertStringToLocalDateTime(parameters.getOrDefault("date_end", null));
        List<Tuple> result;
        if (this.userAccountService.isAdminUser(userAccountId)) {
            result = this.nativeQueryService.getReportFromRequestVsLoans(dateStart, dateEnd);
        } else {
            result = this.nativeQueryService.getReportFromRequestVsLoansByUserAccount(userAccountId, dateStart, dateEnd);
        }

        GeneralChart chart = new GeneralChart("Solicitudes vs. Prestamos", "Reporte Actual", "bar",
                "Usuario", "Cantidad");

        ItemGeneralChart itemRequest = new ItemGeneralChart("Solicitudes");
        for (Tuple temp : result) {
            chart.addLabel(temp.get("username").toString());
            itemRequest.addItem((BigDecimal) temp.get("requests"));
        }
        chart.addItemGeneral(itemRequest);


        ItemGeneralChart itemLoans = new ItemGeneralChart("Prestamos");
        for (Tuple temp : result) {
            itemLoans.addItem((BigDecimal) temp.get("loans"));
        }
        chart.addItemGeneral(itemLoans);

        return chart;
    }

    @Override
    public GeneralChart getDashboardPenaltySummary(Map<String, String> parameters, Long userAccountId) throws SchedulePilotException {
        Date dateStart = CommonUtil.convertStringToDate(parameters.getOrDefault("date_start", null));
        Date dateEnd = CommonUtil.convertStringToDate(parameters.getOrDefault("date_end", null));
        List<IPenaltySummaryResult> result;
        if (this.userAccountService.isAdminUser(userAccountId)) {
            result = this.penaltyCheckOutService.getSummary(dateStart, dateEnd);
        } else {
            result = this.penaltyCheckOutService.getSummaryByUserAccount(userAccountId, dateStart, dateEnd);
        }
        GeneralChart chart = new GeneralChart("Total de Multas", "Reporte Actual", "bar",
                "Usuario", "Total");
        ItemGeneralChart itemRequest = new ItemGeneralChart("Total $");
        for (IPenaltySummaryResult temp : result) {
            chart.addLabel(temp.getUsername());
            itemRequest.addItem(BigDecimal.valueOf(temp.getPrice()));
        }
        chart.addItemGeneral(itemRequest);

        return chart;
    }
}
