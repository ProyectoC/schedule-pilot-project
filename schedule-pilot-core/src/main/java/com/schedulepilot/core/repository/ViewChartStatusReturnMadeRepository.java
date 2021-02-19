package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ViewChartStatusReturnMade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewChartStatusReturnMadeRepository extends JpaRepository<ViewChartStatusReturnMade, Long> {

    List<ViewChartStatusReturnMade> findByUserAccountId(Long userAccountId);

}
