package com.example.itemfinderapplication.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String name;
    String surname;
    String email;
    String phone;
    String password;
}