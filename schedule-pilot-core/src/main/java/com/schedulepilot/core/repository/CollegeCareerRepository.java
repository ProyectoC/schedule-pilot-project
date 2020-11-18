package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.CollegeCareerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeCareerRepository extends JpaRepository<CollegeCareerEntity, Long> {
}
