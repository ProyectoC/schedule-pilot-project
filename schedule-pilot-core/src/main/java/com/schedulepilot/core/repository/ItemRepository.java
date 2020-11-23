package com.schedulepilot.core.repository;

import com.schedulepilot.core.entities.model.ItemEntity;
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
public interface ItemRepository extends JpaRepository<ItemEntity, Long>, PagingAndSortingRepository<ItemEntity, Long> {

    @Query(value = "SELECT i from ItemEntity i " +
            "WHERE i.isActive = TRUE " +
            "AND(:productId IS NULL OR i.productEntity.id = :productId) " +
            "AND (:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name,'%'))) " +
            "AND (:serial IS NULL OR LOWER(i.serial1) LIKE LOWER(CONCAT('%', :serial,'%'))) " +
            "AND (:status IS NULL OR LOWER(i.itemStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    Page<ItemEntity> findAllWithPage(Pageable pageable, Long productId, String name, String serial, String status);

    @Query(value = "SELECT i from ItemEntity i " +
            "WHERE i.isActive = TRUE " +
            "AND(:productId IS NULL OR i.productEntity.id = :productId) " +
            "AND (:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name,'%'))) " +
            "AND (:serial IS NULL OR LOWER(i.serial1) LIKE LOWER(CONCAT('%', :serial,'%'))) " +
            "AND (:status IS NULL OR LOWER(i.itemStatusEntity.name) LIKE LOWER(CONCAT('%', :status,'%'))) ")
    List<ItemEntity> findAllWithSort(Sort sort, Long productId, String name, String serial, String status);

    @Query(value = "SELECT i from ItemEntity i " +
            "WHERE i.isActive = true " +
            "AND i.productEntity.id = :productId " +
            "AND i.itemStatusEntity.name = 'DISPONIBLE'")
    List<ItemEntity> findByEnable(Long productId);
}
