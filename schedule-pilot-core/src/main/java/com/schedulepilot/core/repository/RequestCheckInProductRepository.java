package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface RequestCheckInProductRepository extends JpaRepository<RequestCheckInProductEntity, RequestCheckInProductId> {

    @Query(value = "SELECT req FROM RequestCheckInProductEntity req " +
            "WHERE req.productRequestStatusEntity.name = 'SOLICITADO'" +
            "AND req.loanDate >= :createdDateStart AND req.loanDate <= :createdDateEnd " +
            "ORDER BY req.createdDate ASC")
    List<RequestCheckInProductEntity> findByPriorityDate(LocalDateTime createdDateStart, LocalDateTime createdDateEnd);

    List<RequestCheckInProductEntity> findByProductRequestStatusEntity_NameAndCreatedDateBetweenOrderByCreatedDateDesc(String name, Date createdDateStart, Date createdDateEnd);
}
