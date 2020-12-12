package com.schedulepilot.core.service.manage;

import com.schedulepilot.core.dto.PageResponseDto;
import com.schedulepilot.core.dto.model.CountryDto;
import com.schedulepilot.core.entities.model.CountryEntity;
import com.schedulepilot.core.service.CountryEntityService;
import com.schedulepilot.core.service.modelmapper.CountryMapperService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ManageCountryServiceImp implements ManageCountryService {

    private final CountryEntityService countryEntityService;
    private final CountryMapperService countryMapperService;

    public ManageCountryServiceImp(CountryEntityService countryEntityService, CountryMapperService countryMapperService) {
        this.countryEntityService = countryEntityService;
        this.countryMapperService = countryMapperService;
    }

    @Override
    public PageResponseDto<CountryDto> getAllCountries(Map<String, String> parameters) {
        List<CountryDto> countriesList = new ArrayList<>();
        PageResponseDto<CountryDto> pageResponse = new PageResponseDto<>();

        Page<CountryEntity> page = this.countryEntityService.getAllWithPagination(parameters);

        page.getContent().forEach(e -> countriesList.add(this.countryMapperService.convertEntityToDto(e)));
        pageResponse.build(countriesList, page);

        return pageResponse;
    }
}
