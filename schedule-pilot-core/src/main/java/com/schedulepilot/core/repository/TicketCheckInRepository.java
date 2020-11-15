package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TicketCheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketCheckInRepository extends JpaRepository<TicketCheckInEntity, Long> {

    @Query(value = "select nextval('schedule_pilot_db.track_id_ticket_check_in')", nativeQuery = true)
    Long getTicketCheckInSequence();

    Optional<TicketCheckInEntity> findByTrackId(String trackId);

}
