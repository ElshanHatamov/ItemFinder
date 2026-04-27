package com.example.itemfinderapplication.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String name;
    @NotBlank
    String surname;
    @NotBlank
    @Column(nullable = false, unique = true)
    String email;
    @NotBlank
    @Column(nullable = false, unique = true)
    String phone;
    @NotBlank
    String password;
    @CreationTimestamp
    LocalDateTime createAt;

    boolean active = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Item> itemList;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<RefreshToken> refreshTokens = new ArrayList<>();
}
