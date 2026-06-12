package com.example.itemfinderapplication.controller;

import com.example.itemfinderapplication.model.dto.request.*;
import com.example.itemfinderapplication.model.dto.response.LoginResponse;
import com.example.itemfinderapplication.model.dto.response.UserResponse;
import com.example.itemfinderapplication.security.CustomUserDetails;
import com.example.itemfinderapplication.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        LoginResponse response = authService.refresh(refreshRequest.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest refreshRequest) {
        authService.logout(refreshRequest.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody VerifyEmailRequest emailRequest) {
        return ResponseEntity.ok(authService.verifyEmail(emailRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(
                authService.getProfile(userDetails.getUsername())
        );
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(
                authService.changePassword(userDetails.getUsername(),request)
        );
    }
}