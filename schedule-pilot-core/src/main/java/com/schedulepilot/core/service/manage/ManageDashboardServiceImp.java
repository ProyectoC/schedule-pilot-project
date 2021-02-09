package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import com.schedulepilot.core.response.dashboards.GeneralChart;
import com.schedulepilot.core.response.dashboards.ItemGeneralChart;
import com.schedulepilot.core.service.ViewChartStatusOperationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class ManageDashboardServiceImp implements ManageDashboardService {

    private final ViewChartStatusOperationService viewChartStatusOperationService;

    @Override
    public GeneralChart getDashboardStatusOperation(Long userAccountId) {
        ViewChartStatusOperation statusOperation = this.viewChartStatusOperationService.getByUserAccountId(userAccountId);

        GeneralChart chart = new GeneralChart("Estado de Operaciones", "Reporte Actual", "bar",
                "Tipos", "Cantidad");
        chart.addLabel("Solicitudes");
        chart.addLabel("Prestamos");
        chart.addLabel("Devoluciones");
        ItemGeneralChart itemRequest = new ItemGeneralChart("Cantidad");
        itemRequest.addItem(BigDecimal.valueOf(statusOperation.getRequests()));
        itemRequest.addItem(BigDecimal.valueOf(statusOperation.getTickets()));
        itemRequest.addItem(BigDecimal.valueOf(statusOperation.getRefunds()));

        chart.addItemGeneral(itemRequest);
        return chart;
    }
}
