# ItemFinder

ItemFinder itirilmiş və tapılmış əşyaların idarə olunması üçün hazırlanmış backend tətbiqidir. Layihə Spring Boot əsasında qurulmuş, JWT autentifikasiyası və PostgreSQL verilənlər bazası ilə inteqrasiya edilmişdir.

## İstifadə olunan texnologiyalar

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* PostgreSQL
* Hibernate / JPA
* Gradle
* Lombok
* Git & GitHub

## Əsas imkanlar

* İstifadəçi qeydiyyatı və giriş sistemi
* JWT əsaslı autentifikasiya və avtorizasiya
* İtirilmiş əşyaların əlavə edilməsi və idarə olunması
* Tapılmış əşyaların əlavə edilməsi və idarə olunması
* İstifadəçi profilinin idarə olunması

## API Endpoint-ləri

### Authentication
* POST /auth/register
* POST /auth/login
* POST /auth/refresh
* POST /auth/logout

### Items
* GET /api/items
* GET /api/items/{id}
* GET /api/items/my-items
* GET /api/items/city/{cityId}
* POST /api/items
* PUT /api/items/{id}
* DELETE /api/items/{id}

### Cities
* GET /api/cities
* POST /api/cities

## Gələcək inkişaf planları

* Şəkil yükləmə funksionallığı
* E-mail bildirişləri
* Ətraflı axtarış və filtrləmə
* Admin paneli
* Swagger/OpenAPI sənədləşməsi

## Layihənin vəziyyəti

🚧 İnkişaf mərhələsindədir

## Müəllif

Elshan Hatamov

GitHub: https://github.com/ElshanHatamov