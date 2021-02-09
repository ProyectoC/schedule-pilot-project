package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.ViewChartStatusLoanMade;
import com.schedulepilot.core.repository.ViewChartStatusLoanMadeRepository;
import com.schedulepilot.core.service.ViewChartStatusLoanMadeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ViewChartStatusLoanMadeServiceImp implements ViewChartStatusLoanMadeService {

    private final ViewChartStatusLoanMadeRepository viewChartStatusLoanMadeRepository;

    @Override
    public List<ViewChartStatusLoanMade> getByUserAccountId(Long userAccountId) {
        return this.viewChartStatusLoanMadeRepository.findByUserAccountId(userAccountId);
    }
}
