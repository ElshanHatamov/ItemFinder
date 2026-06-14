package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.model.dto.request.RegisterRequest;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.ItemRepository;
import com.example.itemfinderapplication.repository.UserRepository;
import com.example.itemfinderapplication.security.jwt.JwtService;
import com.example.itemfinderapplication.security.refresh.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @Mock
    RefreshTokenService refreshTokenService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    EmailService emailService;

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    AuthService authService;

    @Test
    void shouldRegisterSuccessfully() {

        RegisterRequest request = new RegisterRequest(
                "Elshan",
                "Hatamov",
                "elsen@gmail.com",
                "0501234567",
                "12345678"
        );

        when(userRepository.findByEmail("elsen@gmail.com"))
                .thenReturn(Optional.empty());

        when(userRepository.existsByPhone("0501234567"))
                .thenReturn(false);

        when(passwordEncoder.encode("12345678"))
                .thenReturn("encodedPassword");

        String result = authService.register(request);

        assertEquals(
                "Qeydiyyat uğurludur. Emailə göndərilən təsdiq kodunu daxil edin.",
                result
        );

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        User user = new User();
        user.setActive(true);

        when(userRepository.findByEmail("elsen@gmail.com"))
                .thenReturn(Optional.of(user));

        RegisterRequest request = new RegisterRequest(
                "Elshan",
                "Hatamov",
                "elsen@gmail.com",
                "0501234567",
                "12345678"
        );

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );

        assertEquals(
                "Bu email artıq istifadə olunub",
                exception.getMessage()
        );

        verify(userRepository, never()).save(any());
    }
}