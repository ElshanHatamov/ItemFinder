package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.Role;
import com.example.itemfinderapplication.model.dto.request.RegisterRequest;
import com.example.itemfinderapplication.model.dto.request.VerifyEmailRequest;
import com.example.itemfinderapplication.model.dto.response.LoginResponse;
import com.example.itemfinderapplication.model.entity.RefreshToken;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.UserRepository;
import com.example.itemfinderapplication.security.jwt.JwtService;
import com.example.itemfinderapplication.security.refresh.RefreshTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;
    RefreshTokenService refreshTokenService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    EmailService emailService;

    public LoginResponse login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User tapilmadi"));

        if (!user.isActive()) {
            throw new RuntimeException("Email təsdiqlənməyib. Zəhmət olmasa emailə göndərilən kodu daxil edin.");
        }
        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.create(user);

        // Giris ugurlu olduqdan sonra emaili buradan gonderilecek
        try {
            emailService.sendLoginNotification(user.getEmail());
            log.info("Emaile mesaj gonderildi " + user.getEmail());

        } catch (Exception e) {
            // email serverde pronblem yaransada program cokmur catch edir tutur
            System.out.println("Email göndərilərkən xəta baş verdi: " + e.getMessage());
        }


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

    public String register(RegisterRequest request) {

        User existingUser = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (existingUser != null) {

            if (existingUser.isActive()) {
                throw new RuntimeException("Bu email artıq istifadə olunub");
            }

            String code = generateVerificationCode();

            existingUser.setVerificationCode(code);
            existingUser.setVerificationCodeExpiresAt(
                    LocalDateTime.now().plusMinutes(10)
            );

            userRepository.save(existingUser);

            try {
                emailService.sendVerificationCode(existingUser.getEmail(), code);
            } catch (Exception e) {
                log.error("Verification email göndərilmədi: {}", e.getMessage());
            }

            return "Yeni təsdiq kodu emailə göndərildi.";
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Bu telefon nömrəsi artıq istifadə olunub");
        }

        String code = generateVerificationCode();

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user.setActive(false);
        user.setVerificationCode(code);
        user.setVerificationCodeExpiresAt(
                LocalDateTime.now().plusMinutes(10)
        );

        userRepository.save(user);

        try {
            emailService.sendVerificationCode(user.getEmail(), code);
        } catch (Exception e) {
            log.error("Verification email göndərilmədi: {}", e.getMessage());
        }

        return "Qeydiyyat uğurludur. Emailə göndərilən təsdiq kodunu daxil edin.";
    }

    // Email ucun verification kod
    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public String verifyEmail(VerifyEmailRequest emailRequest) {
        User user = userRepository.findByEmail(emailRequest.email())
                .orElseThrow(() -> new RuntimeException("Istifadəçi tapılmadı"));

        if (user.isActive()) {
            throw new RuntimeException("Email artıq təsdiqlənib");
        }
        if (!user.getVerificationCode().equals(emailRequest.code())) {
            throw new RuntimeException("Kod yanlışdır");
        }
        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Kodun vaxtı bitib");
        }

        // verification  etdikden sonra false olur true
        user.setActive(true);
        // Kod artiq lazim deyil deye bazadan temizlenir
        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);

        userRepository.save(user);

        return "Email uğurla təsdiqləndi";
    }
}