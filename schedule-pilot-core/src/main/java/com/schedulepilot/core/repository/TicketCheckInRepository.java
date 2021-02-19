package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
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
public interface TicketCheckInRepository extends JpaRepository<TicketCheckInEntity, Long>,
        PagingAndSortingRepository<TicketCheckInEntity, Long> {

    @Query(value = "SELECT nextval('schedule_pilot_db.track_id_ticket_check_in')", nativeQuery = true)
    Long getTicketCheckInSequence();

    Optional<TicketCheckInEntity> findByTrackId(String trackId);

    @Query(value = "SELECT tci FROM TicketCheckInEntity tci " +
    "INNER JOIN tci.ticketCheckStatusEntity tcse " +
    "WHERE tcse.name = :status " +
    "AND tci.deliveryDate <= :deliveryDate ")
    List<TicketCheckInEntity> findAllExpiredTicketCheckIn(String status, LocalDateTime deliveryDate);

    @Query(value = "SELECT tci FROM TicketCheckInEntity tci " +
            "INNER JOIN tci.requestCheckInEntity rci " +
            "INNER JOIN tci.itemEntity ie " +
            "WHERE (:userAccountId IS NULL OR tci.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "AND (:trackIdTicket IS NULL OR tci.trackId = :trackIdTicket) " +
            "AND (:trackIdRequest IS NULL OR rci.trackId = :trackIdRequest) " +
            "AND (cast(:deliveryDateStart AS date) IS NULL OR tci.deliveryDate >= :deliveryDateStart) " +
            "AND (cast(:deliveryDateEnd AS date) IS NULL OR tci.deliveryDate <= :deliveryDateEnd) " +
            "AND (cast(:returnDateStart AS date) IS NULL OR tci.returnDate >= :returnDateStart) " +
            "AND (cast(:returnDateEnd AS date) IS NULL OR tci.returnDate <= :returnDateEnd) " +
            "AND (:itemName IS NULL OR LOWER(ie.name) LIKE LOWER(CONCAT('%', :itemName,'%'))) " +
            "AND (:status IS NULL OR LOWER(tci.ticketCheckStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    Page<TicketCheckInEntity> findAllTicketCheckInPage(Pageable pageable, Long userAccountId, String trackIdTicket,
                                                       String trackIdRequest, LocalDateTime deliveryDateStart,
                                                       LocalDateTime deliveryDateEnd, LocalDateTime returnDateStart,
                                                       LocalDateTime returnDateEnd, String itemName, String status);

    @Query(value = "SELECT tci FROM TicketCheckInEntity tci " +
            "INNER JOIN tci.requestCheckInEntity rci " +
            "INNER JOIN tci.itemEntity ie " +
            "WHERE (:userAccountId IS NULL OR tci.requestCheckInEntity.userAccountEntity.id = :userAccountId) " +
            "AND (:trackIdTicket IS NULL OR tci.trackId = :trackIdTicket) " +
            "AND (:trackIdRequest IS NULL OR rci.trackId = :trackIdRequest) " +
            "AND (cast(:deliveryDateStart AS date) IS NULL OR tci.deliveryDate >= :deliveryDateStart) " +
            "AND (cast(:deliveryDateEnd AS date) IS NULL OR tci.deliveryDate <= :deliveryDateEnd) " +
            "AND (cast(:returnDateStart AS date) IS NULL OR tci.returnDate >= :returnDateStart) " +
            "AND (cast(:returnDateEnd AS date) IS NULL OR tci.returnDate <= :returnDateEnd) " +
            "AND (:itemName IS NULL OR LOWER(ie.name) LIKE LOWER(CONCAT('%', :itemName,'%'))) " +
            "AND (:status IS NULL OR LOWER(tci.ticketCheckStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    List<TicketCheckInEntity> findAllTicketCheckInSort(Sort sort, Long userAccountId, String trackIdTicket,
                                                       String trackIdRequest, LocalDateTime deliveryDateStart,
                                                       LocalDateTime deliveryDateEnd, LocalDateTime returnDateStart,
                                                       LocalDateTime returnDateEnd, String itemName, String status);

}
