# 🚀 Qaytar.az

Azərbaycanda itmiş və tapılmış əşyaların paylaşılması, uyğun elanların avtomatik aşkarlanması və əşyaların sahiblərinə qaytarılmasını asanlaşdıran full-stack Lost & Found platforması.

Qaytar.az istifadəçilərin itirdikləri və ya tapdıqları əşyalar haqqında elan yaratmasına, elanları idarə etməsinə, oxşar elanları tapmasına və əşya sahibləri ilə daha sürətli əlaqə qurmasına imkan verir.

Layihənin əsas məqsədi itmiş əşyaların sahiblərinə daha qısa müddətdə çatdırılmasını təmin etmək və istifadəçilər arasında təhlükəsiz əlaqə yaratmaqdır.

---

# ✨ Əsas Xüsusiyyətlər

## 🔐 Authentication & Security

* JWT Authentication
* Access Token & Refresh Token mexanizmi
* Refresh Token Rotation
* Spring Security ilə endpoint qorunması
* Role-based Authorization
* BCrypt ilə şifrə şifrələnməsi
* Email Verification (OTP Code)
* Forgot Password & Reset Password sistemi
* Login Notification Email
* İstifadəçi sessiyalarının təhlükəsiz idarə olunması

---

## 📦 Əşya Elanları

* İtmiş əşya elanı yaratmaq
* Tapılmış əşya elanı yaratmaq
* Elanları yeniləmək
* Elanları silmək
* Öz elanlarını görüntüləmək
* Şəhərə görə filtrasiya
* Statusa görə filtrasiya
* Əşya növünə görə filtrasiya
* Əşya təsviri (Description) dəstəyi
* Pagination dəstəyi
* Şəkil əlavə etmək
* Elan detalları səhifəsi

---

## 🤝 Matching Sistemi

* İtmiş və tapılmış əşyaların avtomatik müqayisəsi
* Şəhər və əşya növünə əsaslanan uyğunluq yoxlanışı
* Oxşar elan tapıldıqda email bildirişi göndərilməsi
* İstifadəçilər arasında əlaqənin sürətləndirilməsi

---

## 👤 İstifadəçi İdarəetməsi

* Qeydiyyat
* Email təsdiqlənməsi
* Giriş
* Çıxış (Logout)
* Profil məlumatlarının görüntülənməsi
* Şifrə bərpası
* İstifadəçi elanlarının idarə olunması

---

## 🖼️ Şəkil Dəstəyi

* Multipart File Upload
* Şəkillərin serverdə saxlanılması
* Dinamik şəkil URL-ləri
* Şəkillərin frontenddə göstərilməsi
* Multipart Upload Validation

---

# 🏗️ Layihə Arxitekturası

Backend Layered Architecture prinsiplərinə uyğun hazırlanmışdır.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Authentication axını:

```text
Client
    ↓
JWT Filter
    ↓
Spring Security
    ↓
Protected Endpoints
```

Layihədə aşağıdakı yanaşmalardan istifadə olunmuşdur:

* DTO Pattern
* Layered Architecture
* JWT Authentication
* Refresh Token Strategy
* Dependency Injection
* Repository Pattern

---

# ⚙️ Texnologiyalar

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* JWT Authentication
* Refresh Token Authentication
* Spring Mail
* Lombok
* Jakarta Validation
* Gradle

---

## Frontend

* React
* Vite
* Axios
* React Router
* Tailwind CSS
* Lucide React Icons

---

## DevOps

* Git
* GitHub
* Docker (Development mərhələsində)

---

# 🔗 API Endpoints

## Authentication

```http
POST /auth/register
POST /auth/verify-email

POST /auth/login
POST /auth/refresh
POST /auth/logout

POST /auth/forgot-password
POST /auth/reset-password

GET  /auth/profile
```

---

## Items

```http
GET    /api/items
GET    /api/items/{id}
GET    /api/items/my-items

POST   /api/items
PUT    /api/items/{id}
DELETE /api/items/{id}
```

---

## Search & Filter

```http
GET /api/items/search
GET /api/items?cityId=
GET /api/items?itemType=
GET /api/items?itemStatus=
```

---

## Cities

```http
GET  /api/cities
POST /api/cities
```

---

# 🛡️ Təhlükəsizlik

Layihədə aşağıdakı təhlükəsizlik mexanizmləri tətbiq edilmişdir:

* JWT Authentication
* Refresh Token Rotation
* Email Verification
* BCrypt Password Encryption
* Protected Routes
* Spring Security Filter Chain
* Request Validation
* Authentication Context Management

---

# 📈 Gələcək İnkişaf Planları

* Admin Panel
* Swagger / OpenAPI Documentation
* Docker Deployment
* Cloud Storage (AWS S3 / Cloudinary)
* Real-Time Notification System
* Advanced Search
* User Statistics Dashboard
* Mobile Application

---

# 👨‍💻 Layihə Haqqında

Bu layihənin backend hissəsi Java və Spring Boot texnologiyalarından istifadə edilərək hazırlanmışdır.

Layihə real dünya ssenarisinə uyğun olaraq JWT Authentication, Refresh Token, Email Verification, Password Recovery, Multipart File Upload və Matching System kimi funksionallıqları özündə birləşdirir.

Frontend hissəsi React və Tailwind CSS istifadə edilərək hazırlanmışdır.

Layihənin əsas məqsədi Java Backend Development bacarıqlarını real layihə üzərində nümayiş etdirməkdir.

---

# 👨‍💻 Author

**Elshan Hatamov**

Java Backend Developer

### Technologies

Java • Spring Boot • Spring Security • PostgreSQL • JWT • React

GitHub:

https://github.com/ElshanHatamov

---

⭐ Layihəni bəyəndinizsə repository-yə star verməyi unutmayın.

