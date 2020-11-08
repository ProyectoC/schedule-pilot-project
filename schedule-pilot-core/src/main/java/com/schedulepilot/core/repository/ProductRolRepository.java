package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.id.ProductRolId;
import com.schedulepilot.core.entities.model.ProductRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRolRepository extends JpaRepository<ProductRolEntity, ProductRolId> {

    @Query("SELECT pre from ProductRolEntity pre " +
            "WHERE pre.productRolId.productEntity.id = :productId " +
            "AND pre.productRolId.rolAccountEntity.id = :rolId")
    Optional<ProductRolEntity> findByProductAndRol(Long productId, Long rolId);

}
