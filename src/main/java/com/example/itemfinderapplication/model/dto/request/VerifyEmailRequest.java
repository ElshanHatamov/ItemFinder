package com.example.itemfinderapplication.model.dto.request;

public record VerifyEmailRequest(
        String email,
        String code
) {

}
