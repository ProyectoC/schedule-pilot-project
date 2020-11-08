package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.RequestCheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCheckInRepository extends JpaRepository<RequestCheckInEntity, Long> {

    @Query(value = "select nextval('schedule_pilot_db.track_id_request_check_in')", nativeQuery = true)
    Long getRequestCheckInSequence();
}
