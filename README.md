# 🍰 DeliceOuDecoit Backend

> **A full-featured Spring Boot 3.3.1 REST API for managing establishments, users, images, and ratings.**

---

## 🧭 Overview

**DeliceOuDecoit** is a **Spring Boot 3.3.1 backend application** designed for a platform where users can:

- Register establishments (restaurants, cafés, etc.)
- Manage profiles & upload images  
- Rate establishments (product, service, hygiene)
- Authenticate securely via JWT

This backend is fully integrated with an Angular frontend (`http://localhost:4200`).

---

## ⚙️ Features at a Glance

✅ User authentication (Register / Login / Logout / Refresh token)  
✅ JWT-based stateless security  
✅ Profile CRUD + Image upload  
✅ Establishment CRUD + Gallery + Card image  
✅ Category system  
✅ Rating system (3 criteria → calculated overall)  
✅ Secure CORS setup for Angular frontend  
✅ File uploads for profiles & establishments  
✅ Maven Wrapper included (`mvnw`)

---

## 🧩 Tech Stack

| Layer | Technology | Framework / Library |
|:------|:------------|:--------------------|
| **Core** | Spring Boot | `3.3.1` |
| **Language** | Java | `17` |
| **Security** | Spring Security + JWT | `JJWT 0.12.3` |
| **Database** | MySQL | via `mysql-connector-j` |
| **ORM** | Spring Data JPA | Hibernate |
| **Build Tool** | Maven | (with Wrapper) |
| **JSON Parsing** | Jackson | — |
| **Validation** | Bean Validation | — |
| **Frontend CORS** | Angular | `http://localhost:4200` |

---

## 🗂️ Project Structure

```
src/
 └── main/
     └── java/com/example/deliceoudecoit/
         ├── configuration/     → Security, CORS, Logout
         ├── controller/        → REST endpoints
         ├── dao/               → JPA Repositories
         ├── entities/          → JPA Entities + DTOs
         ├── filter/            → JWT Filter
         └── service/           → Business logic
```

---

## 🔐 Authentication & Authorization

| Endpoint | Description |
|-----------|--------------|
| `POST /register` | Register new user |
| `POST /login` | Login & get JWT |
| `POST /refresh_token` | Refresh JWT |
| `POST /logout` | Invalidate token |
| `GET /me` | Retrieve user profile |
| `PUT /me` | Update profile |
| `DELETE /me` | Delete account |
| `POST /uploadProfileImage` | Upload profile avatar |

🔸 **Roles:** `USER`, `ADMIN`  
🔸 **Admin Routes:** `/admin_only/**`

---

## 🏢 Establishment Management

- Full CRUD operations via `/establishment`
- Upload **card image** & **gallery images**
- Automatic unique `nameId` generation (e.g., `Cafe_Delice_1`)
- Image storage in: `src/images/image_establishment/`

---

## ⭐ Rating System

- Users rate on **3 criteria** → `productQuality`, `service`, `hygiene`
- System computes **overall average**
- Ratings are **updatable and deletable**
- Establishments store **average rating** and **count**

---

## 🗃️ Category Management

Simple CRUD system for categories via `/category` endpoints.

---

## 📡 API Endpoints

| Method | Endpoint | Description |
|:------:|:----------|:-------------|
| **POST** | `/register` | Register user |
| **POST** | `/login` | Login & receive JWT |
| **POST** | `/refresh_token` | Refresh JWT |
| **POST** | `/logout` | Logout & revoke token |
| **GET** | `/me` | Get profile |
| **PUT** | `/me` | Update profile |
| **DELETE** | `/me` | Delete profile |
| **POST** | `/uploadProfileImage` | Upload avatar |
| **POST** | `/category` | Create category |
| **GET** | `/category` | List all categories |
| **GET** | `/category/{id}` | Get category by ID |
| **PUT** | `/category/{id}` | Update category |
| **DELETE** | `/category/{id}` | Delete category |
| **POST** | `/establishment` | Create (multipart) |
| **GET** | `/establishment/all` | List all |
| **GET** | `/establishment/{nameId}` | Get by nameId |
| **PUT** | `/establishment/{nameId}` | Update |
| **DELETE** | `/establishment/{nameId}` | Delete |
| **POST** | `/establishment/{id}/images` | Add gallery image |
| **POST** | `/establishment/uploadCardImage` | Upload card image |
| **GET** | `/establishment/{id}/images` | List images |
| **GET** | `/establishment/image/{name}` | Serve image |
| **DELETE** | `/establishment/{eId}/images/{iId}` | Delete image |
| **POST** | `/establishment/ratings/{id}` | Add rating |
| **PUT** | `/establishment/{eId}/{rId}` | Update rating |

---

## 🧱 Database Schema (Main Tables)

| Table | Columns |
|:------|:---------|
| **user** | id, username, password, firstname, lastname, role |
| **token** | id, access_token, refresh_token, logged_out, user_id |
| **category** | id, name |
| **establishment** | id, name, nameId, description, category_id, user_id, card_image, rates, ratingCount |
| **image** | id, url, establishment_id |
| **rating** | id, user_id, establishment_id, productQualityRating, serviceRating, hygieneRating, overallRating |

---

## 🧰 Setup & Run Locally

### 🧾 Prerequisites
- Java 17  
- MySQL 8+  
- Maven (or use `mvnw`)

### 🪜 Steps

```bash
# 1. Clone the repository
git clone <repo-url>
cd Backend_deliceoudecoit

# 2. Configure database
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/deliceoudecoit
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# JWT
application.security.jwt.secret-key=your-256-bit-secret
application.security.jwt.access-token-expiration=86400000
application.security.jwt.refresh-token-expiration=604800000

# Image upload directories
image.upload.dir=src/images/image_establishment/
```

### 📁 Create folders
```bash
mkdir -p src/images/image_profile
mkdir -p src/images/image_establishment
```

### 🚀 Run the app
```bash
./mvnw spring-boot:run
```
Server will start at → [http://localhost:8080](http://localhost:8080)

---

## 🧱 Build & Package

```bash
./mvnw clean package
```
Output JAR → `target/deliceoudecoit-0.0.1-SNAPSHOT.jar`

---

## 🧩 CI/CD with Jenkins

A `Jenkinsfile` is included for automated build/test/package pipeline:

```groovy
pipeline {
    agent any
    stages {
        stage('Build') { steps { sh './mvnw clean compile' } }
        stage('Test')  { steps { sh './mvnw test' } }
        stage('Package') { steps { sh './mvnw package' } }
    }
}
```

---

## 🛡️ Security Notes

- Passwords hashed with **BCrypt**
- JWT signed with **HMAC-SHA**
- Tokens invalidated on logout
- CORS restricted to **http://localhost:4200**
- File upload paths normalized & validated

---

## 🌐 Frontend Integration

- Works seamlessly with **Angular Frontend**
- Base URL: `http://localhost:4200`
- Use header:  
  ```
  Authorization: Bearer <token>
  ```

---

## 🚀 Future Improvements

- ✉️ Email verification  
- 🔑 Password reset  
- ☁️ Cloud storage (S3)  
- 🔍 Search & filtering  
- 🧮 Pagination  
- 🐳 Dockerization  
- 🧪 Unit & integration tests  

---

## 📜 License

**Apache License 2.0** (via Maven Wrapper)

---

> _DeliceOuDecoit – Where taste meets trust._  
> Built with ❤️ using **Spring Boot & JWT**

**Commit:** `df918651cfbdcb7bf01219210a505fc966e9438b`
