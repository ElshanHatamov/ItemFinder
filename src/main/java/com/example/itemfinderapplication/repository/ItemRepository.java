package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(ItemStatus status);
}
