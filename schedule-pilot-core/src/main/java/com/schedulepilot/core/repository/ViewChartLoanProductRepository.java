package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ViewChartLoanProduct;
import com.schedulepilot.core.repository.queryresult.ILoanProductResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViewChartLoanProductRepository extends JpaRepository<ViewChartLoanProduct, Long> {

    @Query(value = "SELECT vclp.productName AS productName, COUNT(*) AS totalLoans " +
            "FROM ViewChartLoanProduct AS vclp " +
            "WHERE (cast(:dateStart AS date) IS NULL OR vclp.date >= :dateStart) " +
            "AND (cast(:dateEnd AS date) IS NULL OR vclp.date <= :dateEnd) " +
            "GROUP BY vclp.productName ")
    List<ILoanProductResult> countTotalLoansProduct(LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query(value = "SELECT vclp.productName AS productName, COUNT(*) AS totalLoans " +
            "FROM ViewChartLoanProduct AS vclp " +
            "WHERE vclp.userAccountId = :userAccountId " +
            "AND (cast(:dateStart AS date) IS NULL OR vclp.date >= :dateStart) " +
            "AND (cast(:dateEnd AS date) IS NULL OR vclp.date <= :dateEnd) " +
            "GROUP BY vclp.productName ")
    List<ILoanProductResult> countTotalLoansProductByUserAccount(Long userAccountId, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query(value = "SELECT vclp " +
            "FROM ViewChartLoanProduct AS vclp " +
            "WHERE (cast(:dateStart AS date) IS NULL OR vclp.date >= :dateStart) " +
            "AND (cast(:dateEnd AS date) IS NULL OR vclp.date <= :dateEnd) " +
            "ORDER BY vclp.userAccountId ")
    List<ViewChartLoanProduct> findTotalLoansProduct(LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query(value = "SELECT vclp " +
            "FROM ViewChartLoanProduct AS vclp " +
            "WHERE vclp.userAccountId = :userAccountId " +
            "AND (cast(:dateStart AS date) IS NULL OR vclp.date >= :dateStart) " +
            "AND (cast(:dateEnd AS date) IS NULL OR vclp.date <= :dateEnd) " +
            "ORDER BY vclp.userAccountId ")
    List<ViewChartLoanProduct> findTotalLoansProductByUserAccount(Long userAccountId, LocalDateTime dateStart, LocalDateTime dateEnd);
}
