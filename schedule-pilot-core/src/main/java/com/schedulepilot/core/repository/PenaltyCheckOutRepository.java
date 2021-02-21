package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import com.schedulepilot.core.repository.queryresult.IPenaltyReportResult;
import com.schedulepilot.core.repository.queryresult.IPenaltySummaryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface PenaltyCheckOutRepository extends JpaRepository<PenaltyCheckOut, Long> {

    @Query(value = "SELECT ua.username AS username, pco.createdDate AS penaltyDate, pco.pricePenalty AS pricePenalty, i.name AS itemName, " +
            "tci.deliveryDate AS createdDate, tci.returnDate AS returnDate " +
            "FROM PenaltyCheckOut pco " +
            "INNER JOIN pco.ticketCheckOutEntity tco " +
            "INNER JOIN tco.ticketCheckInEntity tci " +
            "INNER JOIN tci.itemEntity i " +
            "INNER JOIN tci.requestCheckInEntity rci " +
            "INNER JOIN rci.userAccountEntity ua " +
            "WHERE (cast(:startDate AS date) IS NULL OR pco.createdDate >= :startDate) " +
            "AND (cast(:endDate AS date) IS NULL OR pco.createdDate <= :endDate) ")
    List<IPenaltyReportResult> findReport(Date startDate, Date endDate);

    @Query(value = "SELECT ua.username AS username, pco.createdDate AS penaltyDate, pco.pricePenalty AS pricePenalty, i.name AS itemName, " +
            "tci.deliveryDate AS createdDate, tci.returnDate AS returnDate " +
            "FROM PenaltyCheckOut pco " +
            "INNER JOIN pco.ticketCheckOutEntity tco " +
            "INNER JOIN tco.ticketCheckInEntity tci " +
            "INNER JOIN tci.itemEntity i " +
            "INNER JOIN tci.requestCheckInEntity rci " +
            "INNER JOIN rci.userAccountEntity ua " +
            "WHERE ua.id = :userAccountId " +
            "AND (cast(:startDate AS date) IS NULL OR pco.createdDate >= :startDate) " +
            "AND (cast(:endDate AS date) IS NULL OR pco.createdDate <= :endDate) ")
    List<IPenaltyReportResult> findReportByUserAccount(Long userAccount, Date startDate, Date endDate);


    @Query(value = "SELECT ua.id, ua.username AS username, SUM(pco.pricePenalty) AS price " +
            "FROM PenaltyCheckOut pco " +
            "INNER JOIN pco.ticketCheckOutEntity tco " +
            "INNER JOIN tco.ticketCheckInEntity tci " +
            "INNER JOIN tci.itemEntity i " +
            "INNER JOIN tci.requestCheckInEntity rci " +
            "INNER JOIN rci.userAccountEntity ua " +
            "WHERE (cast(:startDate AS date) IS NULL OR pco.createdDate >= :startDate) " +
            "AND (cast(:endDate AS date) IS NULL OR pco.createdDate <= :endDate) " +
            "GROUP BY ua.id, ua.username ")
    List<IPenaltySummaryResult> findSummary(Date startDate, Date endDate);

    @Query(value = "SELECT ua.id, ua.username AS username, SUM(pco.pricePenalty) AS price " +
            "FROM PenaltyCheckOut pco " +
            "INNER JOIN pco.ticketCheckOutEntity tco " +
            "INNER JOIN tco.ticketCheckInEntity tci " +
            "INNER JOIN tci.itemEntity i " +
            "INNER JOIN tci.requestCheckInEntity rci " +
            "INNER JOIN rci.userAccountEntity ua " +
            "WHERE ua.id = :userAccountId " +
            "AND (cast(:startDate AS date) IS NULL OR pco.createdDate >= :startDate) " +
            "AND (cast(:endDate AS date) IS NULL OR pco.createdDate <= :endDate) " +
            "GROUP BY ua.id, ua.username ")
    List<IPenaltySummaryResult> findSummaryByUserAccount(Long userAccountId, Date startDate, Date endDate);
}
