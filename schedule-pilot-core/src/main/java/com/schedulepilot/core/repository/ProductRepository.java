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

    @Query(value = "SELECT p from ProductEntity p " +
            "WHERE p.isActive = TRUE " +
            "AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName,'%'))) " +
            "AND (:productDescription IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :productDescription, '%'))) " +
            "AND (:productStatus IS NULL OR LOWER(p.productStatusEntity.name) LIKE LOWER(CONCAT('%', :productStatus, '%'))) ")
    Page<ProductEntity> findAllWithPage(Pageable pageable, String productName, String productDescription, String productStatus);

    @Query(value = "SELECT p from ProductEntity p " +
            "WHERE p.isActive = TRUE " +
            "AND (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName,'%'))) " +
            "AND (:productDescription IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :productDescription, '%'))) " +
            "AND (:productStatus IS NULL OR LOWER(p.productStatusEntity.name) LIKE LOWER(CONCAT('%', :productStatus, '%'))) ")
    List<ProductEntity> findAllWithSort(Sort sort, String productName, String productDescription, String productStatus);
}
