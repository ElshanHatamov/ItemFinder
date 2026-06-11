package com.example.itemfinderapplication.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(

        @NotBlank(message = "Email boş ola bilməz")
        @Email(message = "Düzgün email daxil edin")
        String email,

        @NotBlank(message = "Kod boş ola bilməz")
        String code,

        @NotBlank(message = "Yeni şifrə boş ola bilməz")
        @Size(min = 8, message = "Şifrə minimum 8 simvol olmalıdır")
        String newPassword
) {
}