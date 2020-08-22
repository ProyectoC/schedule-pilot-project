package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ParameterEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParameterRepository extends CrudRepository<ParameterEntity, Long> {

    Optional<ParameterEntity> findByName(String name);
}
