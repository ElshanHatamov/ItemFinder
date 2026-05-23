package com.example.itemfinderapplication.model.entity;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    @Column(nullable = false)
    String tittle;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status secilmelidir")
    @Column(nullable = false)
    ItemStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Esya novu secilmelidir")
    @Column(nullable = false)
    ItemType itemType;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    City city;

}
