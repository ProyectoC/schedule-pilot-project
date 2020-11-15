package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketCheckOutRepository extends JpaRepository<TicketCheckOutEntity, Long> {

    @Query(value = "select nextval('schedule_pilot_db.track_id_ticket_check_out')", nativeQuery = true)
    Long getTicketCheckOutSequence();

    Optional<TicketCheckOutEntity> findByTrackId(String trackId);
}
