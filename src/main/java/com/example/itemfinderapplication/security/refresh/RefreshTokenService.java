package com.example.itemfinderapplication.security.refresh;

import com.example.itemfinderapplication.model.entity.RefreshToken;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;

    public RefreshToken create(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validate(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token tapilmadi"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.deleteByToken(token);
            throw new RuntimeException("Refresh token muddeti bitti");
        }
        return refreshToken;
    }

    public void delete(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}