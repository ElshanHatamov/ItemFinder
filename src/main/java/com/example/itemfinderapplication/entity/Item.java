package com.example.itemfinderapplication.entity;

import com.example.itemfinderapplication.constant.ItemStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String tittle;
    @Enumerated(EnumType.STRING)
    ItemStatus status;


}
