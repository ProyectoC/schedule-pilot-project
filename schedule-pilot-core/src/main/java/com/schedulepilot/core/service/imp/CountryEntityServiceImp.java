package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.CountryEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.CountryRepository;
import com.schedulepilot.core.service.CountryEntityService;
import com.schedulepilot.core.tasks.PaginationAndOrderTask;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CountryEntityServiceImp implements CountryEntityService {

    private final CountryRepository countryRepository;
    private final ApplicationContext applicationContext;

    private Map<String, String> listOrderParametersAvailable;

    public CountryEntityServiceImp(CountryRepository countryRepository, ApplicationContext applicationContext) {
        this.countryRepository = countryRepository;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        this.listOrderParametersAvailable = new HashMap<>();
        this.listOrderParametersAvailable.put("name", "name");
        this.listOrderParametersAvailable.put("iso", "iso");
    }

    @Override
    public Page<CountryEntity> getAllWithPagination(Map<String, String> parameters) {

        PaginationAndOrderTask paginationAndOrderTask = this.applicationContext.getBean(PaginationAndOrderTask.class,
                parameters, this.listOrderParametersAvailable);
        paginationAndOrderTask.execute();

        Page<CountryEntity> page;
        if (paginationAndOrderTask.getPageData() != null)
            page = this.countryRepository.getAllWithPage(paginationAndOrderTask.getPageData());
        else {
            List<CountryEntity> list = this.countryRepository.getAllWithSort(paginationAndOrderTask.getSortData());
            page = new PageImpl<>(list, PageRequest.of(0, list.size()), list.size());
        }
        return page;
    }

    @Override
    public CountryEntity getCountryByCode(String code) throws SchedulePilotException {
        return this.countryRepository.findByIso(code).orElseThrow(() -> new SchedulePilotException("Country Not Found with code: " + code));
    }
}
