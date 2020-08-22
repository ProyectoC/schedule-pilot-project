package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.RolAccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolAccountRepository extends CrudRepository<RolAccountEntity, Long> {
}
