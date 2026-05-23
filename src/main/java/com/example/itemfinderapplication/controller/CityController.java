package com.example.itemfinderapplication.controller;

import com.example.itemfinderapplication.model.dto.request.CityRequest;
import com.example.itemfinderapplication.model.dto.response.CityResponse;
import com.example.itemfinderapplication.service.CityService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityController {

    CityService cityService;

    // Seher elave etmek ucun
    @PostMapping
    public ResponseEntity<CityResponse> createCity(
            @Valid @RequestBody CityRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityService.createCity(request));
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }
}

