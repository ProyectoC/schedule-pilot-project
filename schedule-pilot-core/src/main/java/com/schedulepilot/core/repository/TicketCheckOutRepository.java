package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketCheckOutRepository extends JpaRepository<TicketCheckOutEntity, Long>,
        PagingAndSortingRepository<TicketCheckOutEntity, Long> {

    @Query(value = "select nextval('schedule_pilot_db.track_id_ticket_check_out')", nativeQuery = true)
    Long getTicketCheckOutSequence();

    Optional<TicketCheckOutEntity> findByTrackId(String trackId);

    @Query(value = "SELECT tco FROM TicketCheckOutEntity tco " +
            "WHERE (:userAccountId IS NULL) OR (tco.ticketCheckInEntity.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "ORDER BY tco.createdDate ASC")
    Page<TicketCheckOutEntity> findAllTicketCheckOutPage(Pageable pageable, Long userAccountId);

    @Query(value = "SELECT tco FROM TicketCheckOutEntity tco " +
            "WHERE (:userAccountId IS NULL) OR (tco.ticketCheckInEntity.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "ORDER BY tco.createdDate ASC")
    List<TicketCheckOutEntity> findAllTicketCheckOutSort(Sort sort, Long userAccountId);
}
