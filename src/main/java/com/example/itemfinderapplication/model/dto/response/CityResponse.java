package com.example.itemfinderapplication.model.dto.response;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private Long id;
    private String name;
}
