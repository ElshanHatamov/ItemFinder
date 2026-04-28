package com.example.itemfinderapplication.repository;

import com.example.itemfinderapplication.model.entity.RefreshToken;
import com.example.itemfinderapplication.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r where r.expiryDate < :now")
    void deleteExpiredTokens(Instant now);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken r SET r.revoked = true WHERE r.user.id = :userId")
    void revokeAllByUserId(Long userId);

    void deleteByToken(String token);

    void deleteByUser(User user);
}
