# 🚀 Tapıntı

### Azərbaycanda İtmiş və Tapılmış Əşyalar Platforması

Tapıntı istifadəçilərin itirdikləri və ya tapdıqları əşyalar haqqında elan yaratmasına, elanları idarə etməsinə və əşya sahibləri ilə əlaqə qurmasına imkan verən tam funksional full-stack veb platformadır.

Layihənin əsas məqsədi itmiş əşyaların sahiblərinə daha sürətli çatdırılmasını təmin etmək və istifadəçilər arasında təhlükəsiz əlaqə yaratmaqdır.

---

## ✨ Əsas Xüsusiyyətlər

### 🔐 Authentication & Security

* JWT Authentication
* Access Token & Refresh Token mexanizmi
* Spring Security ilə endpoint qorunması
* Role-based Authorization
* Şifrələrin BCrypt ilə hash-lənməsi
* İstifadəçi sessiyalarının idarə olunması

### 📦 Əşya Elanları

* İtmiş əşya elanı yaratmaq
* Tapılmış əşya elanı yaratmaq
* Elanları yeniləmək
* Elanları silmək
* Öz elanlarını görüntüləmək
* Şəhərə görə filtrasiya
* Əşya statusunun idarə olunması

### 👤 İstifadəçi İdarəetməsi

* Qeydiyyat
* Giriş
* Profil məlumatlarının görüntülənməsi
* İstifadəçi elanlarının idarə olunması

### 🖼️ Şəkil Dəstəyi

* Multipart File Upload
* Əşya şəkillərinin saxlanılması və göstərilməsi

---

## 🏗️ Layihə Arxitekturası

Backend Clean Architecture prinsiplərinə uyğun şəkildə hazırlanmışdır.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Layihədə DTO Pattern, JWT Authentication və Layered Architecture istifadə olunmuşdur.

---

## ⚙️ Texnologiyalar

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* JWT Authentication
* Lombok
* Validation
* Gradle

### Frontend

* React
* Vite
* Axios
* React Router
* Tailwind CSS
* Lucide Icons

### DevOps

* Git
* GitHub
* Docker (hazırlanır)

---

## 🔗 API Endpoint-ləri

### Authentication

```http
POST /auth/register
POST /auth/login
POST /auth/refresh
POST /auth/logout
```

### Items

```http
GET    /api/items
GET    /api/items/{id}
GET    /api/items/my-items
GET    /api/items/city/{cityId}

POST   /api/items
PUT    /api/items/{id}
DELETE /api/items/{id}
```

### Cities

```http
GET  /api/cities
POST /api/cities
```

---

## 🛡️ Təhlükəsizlik

Layihədə aşağıdakı təhlükəsizlik mexanizmləri tətbiq edilmişdir:

* JWT Authentication
* Refresh Token mexanizmi
* Protected Endpoints
* Password Encryption
* Request Validation
* Authentication Filter Chain

---

## 📈 Gələcək İnkişaf Planları

* Email Verification
* OTP təsdiqləmə sistemi
* Email bildirişləri
* Ətraflı axtarış sistemi
* Admin Panel
* Swagger / OpenAPI Documentation
* Docker Deployment
* Cloud Storage Integration
* Real-Time Notification System

---

## 👨‍💻 Layihə Haqqında

Bu layihənin backend hissəsi tam olaraq müəllif tərəfindən Java və Spring Boot texnologiyalarından istifadə edilərək hazırlanmışdır.

Frontend hissəsinin dizayn və istifadəçi təcrübəsinin təkmilləşdirilməsi zamanı süni intellekt dəstəyindən istifadə edilmişdir.

Layihənin əsas məqsədi Java Backend Development bacarıqlarını real dünya ssenarisində nümayiş etdirməkdir.

---

## 👤 Müəllif

**Elshan Hatamov**

Java Backend Developer

GitHub:
https://github.com/ElshanHatamov

---

⭐ Layihəni bəyəndinizsə repository-yə star verməyi unutmayın.
