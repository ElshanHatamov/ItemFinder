package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.constant.ItemStatus;
import com.example.itemfinderapplication.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(ItemStatus status);
}
