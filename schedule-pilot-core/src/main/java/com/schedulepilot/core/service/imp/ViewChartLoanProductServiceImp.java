package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.entities.model.ViewChartLoanProduct;
import com.schedulepilot.core.repository.ViewChartLoanProductRepository;
import com.schedulepilot.core.repository.queryresult.ILoanProductResult;
import com.schedulepilot.core.service.ViewChartLoanProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ViewChartLoanProductServiceImp implements ViewChartLoanProductService {

    private final ViewChartLoanProductRepository viewChartLoanProductRepository;

    @Override
    public List<ILoanProductResult> getCountLoanProduct(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return this.viewChartLoanProductRepository.countTotalLoansProduct(dateStart, dateEnd);
    }

    @Override
    public List<ILoanProductResult> getCountLoanProductByUserAccount(Long userAccountId, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return this.viewChartLoanProductRepository.countTotalLoansProductByUserAccount(userAccountId, dateStart, dateEnd);
    }

    @Override
    public List<ViewChartLoanProduct> getLoanProduct(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return this.viewChartLoanProductRepository.findTotalLoansProduct(dateStart, dateEnd);
    }

    @Override
    public List<ViewChartLoanProduct> getLoanProductByUserAccount(Long userAccountId, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return this.viewChartLoanProductRepository.findTotalLoansProductByUserAccount(userAccountId, dateStart, dateEnd);
    }
}
