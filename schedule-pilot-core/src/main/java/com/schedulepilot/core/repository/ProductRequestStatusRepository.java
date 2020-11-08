package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ProductRequestStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRequestStatusRepository extends JpaRepository<ProductRequestStatusEntity, Long> {
}
