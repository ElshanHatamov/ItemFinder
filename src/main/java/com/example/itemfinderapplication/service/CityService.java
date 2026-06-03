package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.model.dto.request.CityRequest;
import com.example.itemfinderapplication.model.dto.response.CityResponse;
import com.example.itemfinderapplication.model.entity.City;
import com.example.itemfinderapplication.repository.CityRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CityService {

    CityRepository cityRepository;

    //Seher elave edecem
    @Transactional
    public CityResponse createCity(CityRequest request) {

        //Eyni adli seher olsa xeta ucun
        if (cityRepository.existsByName(request.getName())) {
            throw new IllegalStateException(
                    "Bu seher artiq movcuddur" + request.getName());
        }
        City city = new City();
        city.setName(request.getName());

        City saved = cityRepository.save(city);
        log.info("Yeni seher elave edildi: name={}", saved.getName());

        return CityResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    // Butun seherler
    public List<CityResponse> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(city -> CityResponse.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
