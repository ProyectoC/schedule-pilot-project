package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long>, PagingAndSortingRepository<CountryEntity, Long> {

    @Query("SELECT c FROM CountryEntity AS c ")
    Page<CountryEntity> getAllWithPage(Pageable pageable);

    @Query("SELECT c FROM CountryEntity AS c ")
    List<CountryEntity> getAllWithSort(Sort sort);

    Optional<CountryEntity> findByIso(String iso);
}
