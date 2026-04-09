package com.example.itemfinderapplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
    String email;
    @NotBlank
    String phone;
    @NotBlank
    String password;
    @CreationTimestamp
    LocalDateTime createAt;

    boolean active = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Item> itemList;

}
