package com.schedulepilot.core.service.modelmapper;

import com.schedulepilot.core.dto.model.CountryDto;
import com.schedulepilot.core.entities.model.CountryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryMapperServiceImp implements CountryMapperService {

    private final ModelMapper modelMapper;

    public CountryMapperServiceImp(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CountryEntity convertDtoToEntity(CountryDto countryDto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(countryDto, CountryEntity.class);
    }

    @Override
    public CountryDto convertEntityToDto(CountryEntity countryEntity) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(countryEntity, CountryDto.class);
    }
}
