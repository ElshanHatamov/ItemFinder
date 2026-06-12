package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import com.example.itemfinderapplication.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Butun itirilmis esyalar
    @EntityGraph(attributePaths = {"user", "city"})
    Page<Item> findByStatus(ItemStatus status, Pageable pageable);

    // Sehere gore itmis esyalar
    @EntityGraph(attributePaths = {"user", "city"})
    List<Item> findByCityIdAndStatus(Long cityId, ItemStatus status);

    // Novlere gore itirilmis esyalar
    @EntityGraph(attributePaths = {"user", "city"})
    List<Item> findByItemTypeAndStatus(ItemType itemType, ItemStatus itemStatus);

    // ID ye gore olan esya
    @EntityGraph(attributePaths = {"user", "city"})
    Optional<Item> findById(Long id);

    // Istifadecinin elanlari
    @EntityGraph(attributePaths = {"user", "city"})
    List<Item> findByUserEmail(String ownerEmail);

    // Dinamik axtaris sistemi
    @EntityGraph(attributePaths = {"user", "city"})
    @Query("""
            SELECT i FROM Item i
            WHERE (:cityId IS NULL OR i.city.id = :cityId)
              AND (:itemType IS NULL OR i.itemType = :itemType)
              AND (:status IS NULL OR i.status = :status)
            """)
    Page<Item> searchItems(
            @Param("cityId") Long cityId,
            @Param("itemType") ItemType itemType,
            @Param("status") ItemStatus status,
            Pageable pageable
    );

    // Matching sistemi ucun oxsar elanlarin tapilmasi
    @EntityGraph(attributePaths = {"user", "city"})
    List<Item> findByCityIdAndItemTypeAndStatus(
            Long cityId,
            ItemType itemType,
            ItemStatus status
    );

    // N+1 probleminin qarsisini almaq ucun
    // Item ile birlikde User ve City de evvelden yuklenir
    @EntityGraph(attributePaths = {"user", "city"})
    Page<Item> findAll(Pageable pageable);


    long countByUserEmail(String email);

    long countByUserEmailAndStatus(String email,ItemStatus status);
}
