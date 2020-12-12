package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.CountryEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CountryEntityService {

    Page<CountryEntity> getAllWithPagination(Map<String, String> parameters);

    CountryEntity getCountryByCode(String code) throws SchedulePilotException;
}
