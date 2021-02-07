package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.DashboardConstants;
import com.schedulepilot.core.entities.model.ViewChartStatusOperation;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.manage.ManageDashboardService;
import com.schedulepilot.core.util.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = DashboardConstants.REST_PATH_DEFAULT_V1)
@AllArgsConstructor
public class DashboardController {

    private final ManageDashboardService manageDashboardService;

    @GetMapping(DashboardConstants.STATUS_OPERATIONS_REST)
    public ResponseEntity<ResponseDto<List<ViewChartStatusOperation>>> getStatusOperations() throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageDashboardService.getAllStatusOperation()), HttpStatus.OK);
    }

}
