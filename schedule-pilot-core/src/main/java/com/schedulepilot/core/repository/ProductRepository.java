package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, PagingAndSortingRepository<ProductEntity, Long> {

    @Query(value = "SELECT p from ProductEntity p WHERE p.isActive = true AND (:productName IS NULL OR " +
            "lower(p.name) LIKE lower(concat('%', :productName,'%')))")
    Page<ProductEntity> findAllWithPage(Pageable pageable, String productName);

    @Query(value = "SELECT p from ProductEntity p WHERE p.isActive = true AND (:productName IS NULL OR " +
            "lower(p.name) LIKE lower(concat('%', :productName,'%')))")
    List<ProductEntity> findAllWithSort(Sort sort, String productName);
}
