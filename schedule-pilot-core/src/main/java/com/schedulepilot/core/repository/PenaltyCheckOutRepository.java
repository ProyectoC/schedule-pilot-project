package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.PenaltyCheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyCheckOutRepository extends JpaRepository<PenaltyCheckOut, Long> {
}
