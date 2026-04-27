package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
