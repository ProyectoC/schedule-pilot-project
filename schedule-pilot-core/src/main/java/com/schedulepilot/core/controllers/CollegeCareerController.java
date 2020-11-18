package com.schedulepilot.core.controllers;

import com.schedulepilot.core.constants.CollegeCareerConstants;
import com.schedulepilot.core.dto.model.CollegeCareerDto;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.service.CollegeCareerService;
import com.schedulepilot.core.util.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = CollegeCareerConstants.REST_PATH_DEFAULT_V1)
public class CollegeCareerController {

    @Autowired
    private CollegeCareerService collegeCareerService;

    @ResponseBody
    @GetMapping()
    public ResponseEntity<ResponseDto<List<CollegeCareerDto>>> getAllCollegeCareer() throws SchedulePilotException {
        return new ResponseEntity<ResponseDto<List<CollegeCareerDto>>>(ResponseDto.success(this.collegeCareerService.getAll()), HttpStatus.OK);
    }
}
