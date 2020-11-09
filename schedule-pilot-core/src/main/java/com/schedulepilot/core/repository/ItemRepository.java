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
            "WHERE i.isActive = true " +
            "AND(:productId <= 0L OR i.productEntity.id = :productId)")
    Page<ItemEntity> findAllWithPage(Pageable pageable, long productId);

    @Query(value = "SELECT i from ItemEntity i " +
            "WHERE i.isActive = true " +
            "AND(:productId <= 0L OR i.productEntity.id = :productId)")
    List<ItemEntity> findAllWithSort(Sort sort, long productId);

    @Query(value = "SELECT i from ItemEntity i " +
            "WHERE i.isActive = true " +
            "AND i.productEntity.id = :productId " +
            "AND i.itemStatusEntity.name = 'DISPONIBLE'")
    List<ItemEntity> findByEnable(long productId);
}
