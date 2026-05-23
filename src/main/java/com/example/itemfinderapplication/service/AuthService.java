package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.Role;
import com.example.itemfinderapplication.model.dto.request.RefreshRequest;
import com.example.itemfinderapplication.model.dto.request.RegisterRequest;
import com.example.itemfinderapplication.model.dto.response.AuthResponse;
import com.example.itemfinderapplication.model.dto.response.LoginResponse;
import com.example.itemfinderapplication.model.dto.response.UserResponse;
import com.example.itemfinderapplication.model.entity.RefreshToken;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.UserRepository;
import com.example.itemfinderapplication.security.jwt.JwtService;
import com.example.itemfinderapplication.security.refresh.RefreshTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;
    RefreshTokenService refreshTokenService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public LoginResponse login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User tapilmadi"));
        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.create(user);

        return new LoginResponse(accessToken, refreshToken.getToken());
    }

    public LoginResponse refresh(String refreshToken) {
        RefreshToken existing = refreshTokenService.validate(refreshToken);
        User user = existing.getUser();

        refreshTokenService.delete(refreshToken);

        String newAccessToken = jwtService.generateToken(user.getEmail());
        RefreshToken newRefreshToken = refreshTokenService.create(user);

        return new LoginResponse(newAccessToken, newRefreshToken.getToken());
    }

    public void logout(String refreshToken) {
        refreshTokenService.delete(refreshToken);
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.create(user);
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail());

        return new AuthResponse(accessToken, refreshToken.getToken(), userResponse);
    }
}

