package com.example.itemfinderapplication.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Ad boş ola bilməz")
    @Size(min = 3, max = 20, message = "Ad 3-20 simvol aralığında olmalıdır")
    String name;

    @NotBlank(message = "Soyad boş ola bilməz")
    @Size(min = 3, max = 20, message = "Soyad 3-20 simvol aralığında olmalıdır")
    String surname;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email düzgün formatda olmalıdır. Məsələn: test@gmail.com")
    String email;

    @NotBlank(message = "Telefon nömrəsi boş ola bilməz")
    @Pattern(
            regexp = "^(050|051|055|070|077|010|099)\\d{7}$",
            message = "Telefon nömrəsi düzgün deyil. Məsələn: 0501234567"
    )
    String phone;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&._-]).{8,}$",
            message = "Şifrə minimum 8 simvol olmalı, ən azı 1 hərf, 1 rəqəm və 1 xüsusi simvol içerməlidir. Məsələn: Test123!"
    )
    String password;
}