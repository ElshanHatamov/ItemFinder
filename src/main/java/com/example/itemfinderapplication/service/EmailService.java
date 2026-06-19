package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.model.entity.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmailService {

    JavaMailSender mailSender;

    @Async
    public void sendLoginNotification(String toEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("supportqaytar@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Qaytar.az - Hesabınıza yeni giriş qeydə alındı");
        mailMessage.setText("""
                Salam,

                Qaytar.az hesabınıza uğurla giriş edildi.

                Əgər bu əməliyyatı siz etməmisinizsə, dərhal şifrənizi dəyişməyinizi tövsiyə edirik.

                Hörmətlə,
                Qaytar.az komandası
                """);

        mailSender.send(mailMessage);
    }

    @Async
    public void sendMatchingItemNotification(String toEmail, Item item) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("supportqaytar@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Qaytar.az - Oxşar elan tapıldı");

        mailMessage.setText("""
                Salam,

                Sizin elanınıza uyğun yeni elan tapıldı.

                Əşya: %s
                Növ: %s
                Status: %s
                Şəhər: %s

                Zəhmət olmasa Qaytar.az hesabınıza daxil olaraq elanları yoxlayın.

                Hörmətlə,
                Qaytar.az komandası
                """.formatted(
                item.getTitle(),
                item.getItemType(),
                item.getStatus(),
                item.getCity().getName()
        ));

        mailSender.send(mailMessage);
    }

    @Async
    public void sendVerificationCode(String toEmail, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("supportqaytar@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Qaytar.az - Email təsdiq kodu");

        mailMessage.setText("""
                Salam,

                Qaytar.az hesabınızı təsdiqləmək üçün təsdiq kodunuz:

                %s

                Bu kod 10 dəqiqə ərzində etibarlıdır.

                Əgər bu qeydiyyatı siz etməmisinizsə, bu mesajı nəzərə almayın.

                Hörmətlə,
                Qaytar.az komandası
                """.formatted(code));

        mailSender.send(mailMessage);
    }
    @Async
    public void sendPasswordResetCode(String toEmail, String code) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("supportqaytar@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Qaytar.az - Şifrə bərpa kodu");

        mailMessage.setText("""
            Salam,

            Qaytar.az hesabınız üçün şifrə bərpa sorğusu göndərilib.

            Şifrəni yeniləmək üçün təsdiq kodunuz:

            %s

            Bu kod 10 dəqiqə ərzində etibarlıdır.

            Əgər bu sorğunu siz etməmisinizsə, bu mesajı nəzərə almayın.

            Hörmətlə,
            Qaytar.az komandası
            """.formatted(code));

        mailSender.send(mailMessage);
    }
}