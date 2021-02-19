package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ViewChartStatusLoanMade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewChartStatusLoanMadeRepository extends JpaRepository<ViewChartStatusLoanMade, Long> {

    @Query(name = "SELECT vcslm FROM ViewChartStatusLoanMade vcslm WHERE vcslm.userAccountId = :userAccountId")
    List<ViewChartStatusLoanMade> findByUserAccountId(Long userAccountId);
}
