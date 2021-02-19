package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.ViewChartStatusReturnMade;
import com.schedulepilot.core.repository.ViewChartStatusReturnMadeRepository;
import com.schedulepilot.core.service.ViewChartStatusReturnMadeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ViewChartStatusReturnMadeServiceImp implements ViewChartStatusReturnMadeService {

    private final ViewChartStatusReturnMadeRepository viewChartStatusReturnMadeRepository;

    @Override
    public List<ViewChartStatusReturnMade> getByUserAccountId(Long userAccountId) {
        return this.viewChartStatusReturnMadeRepository.findByUserAccountId(userAccountId);
    }
}
