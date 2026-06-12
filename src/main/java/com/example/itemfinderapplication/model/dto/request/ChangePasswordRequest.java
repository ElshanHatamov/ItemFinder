package com.example.itemfinderapplication.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "Köhnə şifrə boş ola bilməz")
        String oldPassword,
        @NotBlank(message = "Yeni şifrə boş ola bilməz")
        @Size(min = 8, message = "Yeni şifrə minimum 8 simvol olmalıdır")
        String newPassword
) {


}
