package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import com.example.itemfinderapplication.model.entity.Item;
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

    public void sendMatchingItemNotification(String toEmail,
                                             Item item
    ) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject("ItemFinder - Oxşar elan tapıldı ");

        mailMessage.setText("""
                Salam
                
                Sizin elanınıza uyğun yeni elan tapıldı.
                
                Əşya: %s
                Növ: %s
                Status: %s
                Şəhər: %s
                
                Zəhmət olmasa ItemFinder hesabınıza daxil olaraq elanları yoxlayın.
                
                Hörmətlə,
                ItemFinder Komandası
                """.formatted(
                item.getTittle(),
                item.getItemType(),
                item.getStatus(),
                item.getCity().getName()
        ));
        mailSender.send(mailMessage);

    }
}