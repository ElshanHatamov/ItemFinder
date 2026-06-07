package com.example.itemfinderapplication.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequest {

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email düzgün formatda olmalıdır. Məsələn: test@gmail.com")
    String email;

    @NotBlank(message = "Şifrə boş ola bilməz")
    String password;
}
