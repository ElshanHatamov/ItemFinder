package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
