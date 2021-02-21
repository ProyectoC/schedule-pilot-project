package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountUserRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByUsername(String username);

    Optional<UserAccountEntity> findByIdentification(String identification);

    Optional<UserAccountEntity> findByIdentificationCode(String identificationCode);

    Optional<UserAccountEntity> findByEmailOrEmailBackup(String email, String emailBackup);

    @Query(value = "SELECT COUNT(*) > 0 FROM UserAccountEntity AS uae " +
            "INNER JOIN uae.rolAccountEntity AS rae " +
            "WHERE uae.id = :userAccountId " +
            "AND (rae.name = 'Super User' OR rae.name = 'Registro y Control') ")
    boolean isAdminUser(Long userAccountId);

    @Query(value = "SELECT COUNT(*) > 0 FROM UserAccountEntity AS uae " +
            "INNER JOIN uae.rolAccountEntity AS rae " +
            "WHERE uae.id = :userAccountId " +
            "AND (rae.name = 'Docente de Planta' OR rae.name = 'Docente de Catedra' OR rae.name = 'Estudiante') ")
    boolean isCommonUser(Long userAccountId);
}
