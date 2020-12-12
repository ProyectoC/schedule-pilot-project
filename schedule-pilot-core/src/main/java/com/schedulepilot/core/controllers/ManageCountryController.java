package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.CountryConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.CountryDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.manage.ManageCountryService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = CountryConstants.REST_PATH_DEFAULT_V1)
public class ManageCountryController {

    private final ManageCountryService manageCountryService;

    public ManageCountryController(ManageCountryService manageCountryService) {
        this.manageCountryService = manageCountryService;
    }

    @ResponseBody
    @GetMapping()
    public ResponseEntity<ResponseDto<PageResponseDto<CountryDto>>> getAllCountries(@RequestParam Map<String, String> parameters) throws SchedulePilotException {
        return new ResponseEntity<>(ResponseDto.success(this.manageCountryService.getAllCountries(parameters)), HttpStatus.OK);
    }
}
