package com.example.itemfinderapplication.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CityRequest {

    @NotBlank(message = "Seher adi bos ola bilmez")
    private String name;
}
