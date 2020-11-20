package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
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
public interface TicketCheckInRepository extends JpaRepository<TicketCheckInEntity, Long>,
        PagingAndSortingRepository<TicketCheckInEntity, Long> {

    @Query(value = "select nextval('schedule_pilot_db.track_id_ticket_check_in')", nativeQuery = true)
    Long getTicketCheckInSequence();

    Optional<TicketCheckInEntity> findByTrackId(String trackId);

    @Query(value = "SELECT tci FROM TicketCheckInEntity tci " +
            "WHERE (:userAccountId IS NULL) OR (tci.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "ORDER BY tci.createdDate ASC")
    Page<TicketCheckInEntity> findAllTicketCheckInPage(Pageable pageable, Long userAccountId);

    @Query(value = "SELECT tci FROM TicketCheckInEntity tci " +
            "WHERE (:userAccountId IS NULL) OR (tci.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "ORDER BY tci.createdDate ASC")
    List<TicketCheckInEntity> findAllTicketCheckInSort(Sort sort, Long userAccountId);

}
