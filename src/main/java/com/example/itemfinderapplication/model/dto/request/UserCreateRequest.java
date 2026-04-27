package com.example.itemfinderapplication.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserCreateRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    String name;
    @NotBlank
    @Size(min = 3, max = 20)
    String surname;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
    @NotBlank(message = "Telefon nömrəsi boş ola bilməz")
    @Pattern(
            regexp = "^(50|51|55|60|70|77|99)\\d{7}$",
            message = "Telefon nömrəsi düzgün deyil (məs: 501234567)"
    )
    String phone;
}
