package com.example.itemfinderapplication.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmailService {

    JavaMailSender mailSender;

    public void sendLoginNotification(String toEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("elsen.hetemov123@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Hesabiniza yeni giris qeyde alindi ");
        mailMessage.setText("Salam,\n\nHesabınıza uğurla giriş edildi. Əgər bu əməliyyatı siz etməmisinizsə, dərhal şifrənizi dəyişməyinizi tövsiyə edirik.");

        // mektubu gonderen emr
        mailSender.send(mailMessage);
    }
}