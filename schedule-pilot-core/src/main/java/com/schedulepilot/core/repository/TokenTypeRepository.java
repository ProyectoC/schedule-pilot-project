package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.TokenTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenTypeRepository extends CrudRepository<TokenTypeEntity, Long> {
}
