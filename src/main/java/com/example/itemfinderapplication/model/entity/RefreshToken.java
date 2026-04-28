package com.example.itemfinderapplication.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String token;

    @Column(nullable = false)
    Instant createdAt;

    @Column(nullable = false)
    Instant expiryDate;

//    String deviceInfo;
//
//    String ipAdress;

    boolean revoked = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
