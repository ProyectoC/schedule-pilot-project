package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.RolAccountConstants;
import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.ItemDto;
import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.RolAccountService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = RolAccountConstants.REST_PATH_DEFAULT_V1)
public class RolAccountController {

    @Autowired
    private RolAccountService rolAccountService;

    @ResponseBody
    @GetMapping()
    public ResponseEntity<ResponseDto<List<RolAccountDto>>> getAllRoles() throws SchedulePilotException {
        return new ResponseEntity<ResponseDto<List<RolAccountDto>>>(ResponseDto.success(this.rolAccountService.getAllWithoutSuperAdmin()), HttpStatus.OK);
    }
}
