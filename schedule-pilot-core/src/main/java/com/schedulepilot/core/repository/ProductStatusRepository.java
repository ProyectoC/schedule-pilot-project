package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ProductStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatusEntity, Long> {
}
