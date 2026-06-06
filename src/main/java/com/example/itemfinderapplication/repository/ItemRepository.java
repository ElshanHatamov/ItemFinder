package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import com.example.itemfinderapplication.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Butun itirilmis esyalar
    List<Item> findByStatus(ItemStatus status);

    // Sehere gore itmis esyalar
    List<Item> findByCityIdAndStatus(Long cityİd, ItemStatus status);

    // Novlere gore itirilmis esyalar
    List<Item> findByItemTypeAndStatus(ItemType itemType, ItemStatus itemStatus);

    // ID ye gore olan esyalar
    List<Item> getItemsById(Long id);

    List<Item> findByUserEmail(String ownerEmail);

    @Query("""
        SELECT i FROM Item i
        WHERE (:cityId IS NULL OR i.city.id = :cityId)
          AND (:itemType IS NULL OR i.itemType = :itemType)
          AND (:status IS NULL OR i.status = :status)
        ORDER BY i.createAt DESC
        """)
    List<Item> searchItems(
            @Param("cityId") Long cityId,
            @Param("itemType") ItemType itemType,
            @Param("status") ItemStatus status
    );
}
