package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.RolAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolAccountRepository extends JpaRepository<RolAccountEntity, Long> {

    @Query("SELECT r FROM RolAccountEntity r WHERE r.name <> 'Super User'")
    List<RolAccountEntity> findAllWithoutSuperAdmin();
}
