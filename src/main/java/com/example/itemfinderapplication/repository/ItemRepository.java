package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import com.example.itemfinderapplication.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Butun itirilmis esyalar
    List<Item> findByStatus(ItemStatus status);

    // Sehere gore itmis esyalar
    List<Item> findByCityIdAndStatus(Long cityİd, ItemStatus status);

    // Novlere gore itirilmis esyalar
    List<Item> findByItemTypeAndStatus(ItemType itemType, ItemStatus itemStatus);
}
