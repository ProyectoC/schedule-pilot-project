package com.schedulepilot.core.service;

import com.schedulepilot.core.entities.model.ViewChartLoanProduct;
import com.schedulepilot.core.repository.queryresult.ILoanProductResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ViewChartLoanProductService {

    List<ILoanProductResult> getCountLoanProduct(LocalDateTime dateStart, LocalDateTime dateEnd);

    List<ILoanProductResult> getCountLoanProductByUserAccount(Long userAccountId, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<ViewChartLoanProduct> getLoanProduct(LocalDateTime dateStart, LocalDateTime dateEnd);

    List<ViewChartLoanProduct> getLoanProductByUserAccount(Long userAccountId, LocalDateTime dateStart, LocalDateTime dateEnd);
}
