package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.CollegeCareerDto;
import com.schedulepilot.core.repository.CollegeCareerRepository;
import com.schedulepilot.core.service.CollegeCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CollegeCareerServiceImp implements CollegeCareerService {

    @Autowired
    private CollegeCareerRepository collegeCareerRepository;

    @Override
    public List<CollegeCareerDto> getAll() {
        List<CollegeCareerDto> list = new ArrayList<>();
        this.collegeCareerRepository.findAll().forEach(e -> list.add(CollegeCareerService.convertEntityToDTO(e)));
        return list;
    }
}
