package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.id.RequestCheckInProductId;
import com.schedulepilot.core.entities.model.RequestCheckInProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
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
            "INNER JOIN req.requestCheckInProductId.requestCheckInEntity reqCIn " +
            "INNER JOIN req.requestCheckInProductId.productEntity pro " +
            "WHERE (:userAccountId IS NULL OR reqCIn.userAccountEntity.id = :userAccountId) " +
            "AND (:productName IS NULL OR LOWER(pro.name) LIKE LOWER(CONCAT('%', :productName,'%'))) " +
            "AND (:trackId IS NULL OR reqCIn.trackId = :trackId) " +
            "AND (cast(:loanDateStart AS date) IS NULL OR req.loanDate >= :loanDateStart) " +
            "AND (cast(:loanDateEnd as date) IS NULL OR req.loanDate <= :loanDateEnd) " +
            "AND (cast(:requestDateStart as date) IS NULL OR req.createdDate >= :requestDateStart) " +
            "AND (cast(:requestDateEnd as date) IS NULL OR req.createdDate <= :requestDateEnd) " +
            "AND (:status IS NULL OR LOWER(req.productRequestStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    Page<RequestCheckInProductEntity> findAllByUserAccountPage(Pageable pageable, Long userAccountId, String productName,
                                                               String trackId, LocalDateTime loanDateStart, LocalDateTime loanDateEnd,
                                                               Date requestDateStart, Date requestDateEnd,
                                                               String status);

    @Query(value = "SELECT req FROM RequestCheckInProductEntity req " +
            "INNER JOIN req.requestCheckInProductId.requestCheckInEntity reqCIn " +
            "INNER JOIN req.requestCheckInProductId.productEntity pro " +
            "WHERE (:userAccountId IS NULL OR reqCIn.userAccountEntity.id = :userAccountId) " +
            "AND (:productName IS NULL OR LOWER(pro.name) LIKE LOWER(CONCAT('%', :productName,'%'))) " +
            "AND (:trackId IS NULL OR reqCIn.trackId = :trackId) " +
            "AND (cast(:loanDateStart AS date) IS NULL OR req.loanDate >= :loanDateStart) " +
            "AND (cast(:loanDateEnd as date) IS NULL OR req.loanDate <= :loanDateEnd) " +
            "AND (cast(:requestDateStart as date) IS NULL OR req.createdDate >= :requestDateStart) " +
            "AND (cast(:requestDateEnd as date) IS NULL OR req.createdDate <= :requestDateEnd) " +
            "AND (:status IS NULL OR LOWER(req.productRequestStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    List<RequestCheckInProductEntity> findAllByUserAccountSort(Sort sort, Long userAccountId, String productName,
                                                               String trackId, LocalDateTime loanDateStart, LocalDateTime loanDateEnd,
                                                               Date requestDateStart, Date requestDateEnd,
                                                               String status);

    List<RequestCheckInProductEntity> findByProductRequestStatusEntity_NameAndCreatedDateBetweenOrderByCreatedDateDesc(String name, Date createdDateStart, Date createdDateEnd);
}
