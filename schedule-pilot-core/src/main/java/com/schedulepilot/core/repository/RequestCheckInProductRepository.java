package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import com.schedulepilot.core.entities.model.ProductEntity;
import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface RequestCheckInProductRepository extends JpaRepository<RequestCheckInProductEntity, RequestCheckInProductId>, PagingAndSortingRepository<RequestCheckInProductEntity, RequestCheckInProductId> {

    @Query(value = "SELECT req FROM RequestCheckInProductEntity req " +
            "WHERE req.productRequestStatusEntity.name = 'SOLICITADO'" +
            "AND req.loanDate >= :createdDateStart AND req.loanDate <= :createdDateEnd " +
            "AND req.loanDate > :currentDate " +
            "ORDER BY req.createdDate ASC")
    List<RequestCheckInProductEntity> findByPriorityDate(LocalDateTime createdDateStart, LocalDateTime createdDateEnd, LocalDateTime currentDate);

    @Query(value = "SELECT req FROM RequestCheckInProductEntity req " +
            "WHERE (:userAccountId IS NULL) OR (req.requestCheckInProductId.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "ORDER BY req.createdDate ASC")
    Page<RequestCheckInProductEntity> findAllByUserAccountPage(Pageable pageable, Long userAccountId);

    @Query(value = "SELECT req FROM RequestCheckInProductEntity req " +
            "WHERE (:userAccountId IS NULL) OR (req.requestCheckInProductId.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "ORDER BY req.createdDate ASC")
    List<RequestCheckInProductEntity> findAllByUserAccountSort(Sort sort, Long userAccountId);

    List<RequestCheckInProductEntity> findByProductRequestStatusEntity_NameAndCreatedDateBetweenOrderByCreatedDateDesc(String name, Date createdDateStart, Date createdDateEnd);
}
