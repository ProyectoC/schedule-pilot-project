package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.CountryDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ManageCountryService {

    PageResponseDto<CountryDto> getAllCountries(Map<String, String> parameters);
}
