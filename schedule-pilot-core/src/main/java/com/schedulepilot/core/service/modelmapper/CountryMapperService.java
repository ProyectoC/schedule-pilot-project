package com.schedulepilot.core.service.modelmapper;

import com.schedulepilot.core.dto.model.CountryDto;
import com.schedulepilot.core.entities.model.CountryEntity;
import org.springframework.stereotype.Service;

@Service
public interface CountryMapperService {

    CountryEntity convertDtoToEntity(CountryDto countryDto);

    CountryDto convertEntityToDto(CountryEntity countryEntity);
}
