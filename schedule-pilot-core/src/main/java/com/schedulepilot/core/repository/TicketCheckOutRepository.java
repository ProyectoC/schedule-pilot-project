package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketCheckOutRepository extends JpaRepository<TicketCheckOutEntity, Long>,
        PagingAndSortingRepository<TicketCheckOutEntity, Long> {

    @Query(value = "select nextval('schedule_pilot_db.track_id_ticket_check_out')", nativeQuery = true)
    Long getTicketCheckOutSequence();

    Optional<TicketCheckOutEntity> findByTrackId(String trackId);

    @Query(value = "SELECT tco FROM TicketCheckOutEntity tco " +
            "INNER JOIN tco.ticketCheckStatusEntity tcse " +
            "INNER JOIN tco.ticketCheckInEntity tci " +
            "WHERE tcse.name = :status " +
            "AND tci.returnDate <= :returnDate ")
    List<TicketCheckOutEntity> findAllExpiredTicketCheckOut(String status, LocalDateTime returnDate);

    @Query(value = "SELECT tco FROM TicketCheckOutEntity tco " +
            "INNER JOIN tco.ticketCheckInEntity tci " +
            "INNER JOIN tci.itemEntity item " +
            "WHERE (:userAccountId IS NULL OR tco.ticketCheckInEntity.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "AND (:trackIdTicketOut IS NULL OR tco.trackId = :trackIdTicketOut) " +
            "AND (:trackIdTicketIn IS NULL OR tci.trackId = :trackIdTicketIn) " +
            "AND (cast(:deliveryDateStart AS date) IS NULL OR tci.deliveryDate >= :deliveryDateStart) " +
            "AND (cast(:deliveryDateEnd AS date) IS NULL OR tci.deliveryDate <= :deliveryDateEnd) " +
            "AND (cast(:returnDateStart AS date) IS NULL OR tci.returnDate >= :returnDateStart) " +
            "AND (cast(:returnDateEnd AS date) IS NULL OR tci.returnDate <= :returnDateEnd) " +
            "AND (:itemName IS NULL OR LOWER(item.name) LIKE LOWER(CONCAT('%', :itemName,'%'))) " +
            "AND (:status IS NULL OR LOWER(tco.ticketCheckStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    Page<TicketCheckOutEntity> findAllTicketCheckOutPage(Pageable pageable, Long userAccountId, String trackIdTicketOut,
                                                         String trackIdTicketIn, LocalDateTime deliveryDateStart,
                                                         LocalDateTime deliveryDateEnd, LocalDateTime returnDateStart,
                                                         LocalDateTime returnDateEnd, String itemName, String status);

    @Query(value = "SELECT tco FROM TicketCheckOutEntity tco " +
            "INNER JOIN tco.ticketCheckInEntity tci " +
            "INNER JOIN tci.itemEntity item " +
            "WHERE (:userAccountId IS NULL OR tco.ticketCheckInEntity.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "AND (:trackIdTicketOut IS NULL OR tco.trackId = :trackIdTicketOut) " +
            "AND (:trackIdTicketIn IS NULL OR tci.trackId = :trackIdTicketIn) " +
            "AND (cast(:deliveryDateStart AS date) IS NULL OR tci.deliveryDate >= :deliveryDateStart) " +
            "AND (cast(:deliveryDateEnd AS date) IS NULL OR tci.deliveryDate <= :deliveryDateEnd) " +
            "AND (cast(:returnDateStart AS date) IS NULL OR tci.returnDate >= :returnDateStart) " +
            "AND (cast(:returnDateEnd AS date) IS NULL OR tci.returnDate <= :returnDateEnd) " +
            "AND (:itemName IS NULL OR LOWER(item.name) LIKE LOWER(CONCAT('%', :itemName,'%'))) " +
            "AND (:status IS NULL OR LOWER(tco.ticketCheckStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    List<TicketCheckOutEntity> findAllTicketCheckOutSort(Sort sort, Long userAccountId, String trackIdTicketOut,
                                                         String trackIdTicketIn, LocalDateTime deliveryDateStart,
                                                         LocalDateTime deliveryDateEnd, LocalDateTime returnDateStart,
                                                         LocalDateTime returnDateEnd, String itemName, String status);
}
