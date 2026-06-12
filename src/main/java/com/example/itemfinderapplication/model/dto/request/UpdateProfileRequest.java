package com.example.itemfinderapplication.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank(message = "Ad boş ola bilməz")
        @Size(min = 3, message = "Ad minimum 3 simvol olmalıdır")
        String name,

        @NotBlank(message = "Soyad boş ola bilməz")
        @Size(min = 3, message = "Soyad minimum 3 simvol olmalıdır")
        String surname,

        @NotBlank(message = "Telefon nömrəsi boş ola bilməz")
        String phone
) {
}
