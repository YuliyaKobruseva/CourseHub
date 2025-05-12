# 📚 CourseHub API

![Java 21](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring)
![Gradle](https://img.shields.io/badge/Build-Gradle-0388d1?logo=gradle)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow)

A demo **REST API** for managing **Courses** and **Students** (1 : N), secured with **JWT** and **role-based** access.  
Built with a **hexagonal/clean architecture**, fully tested (unit & integration), and documented with **Swagger UI**.  

## ✨ Features

- **CRUD Operations**
    - Courses: Create, Read, Update, Delete
    - Students: Create, Read, Update, Delete

- **Enrollment**
    - Enroll an existing student into a course via `POST /api/enroll`

- **Validation**
    - Bean Validation (`@Valid`, `@NotBlank`)
    - Business rules (course dates coherent, one course per student)

- **Persistence**
    - Spring Data JPA
    - H2 in-memory (default), switchable to PostgreSQL

- **Security**
    - Spring Security 6 + JWT (HS256 via Auth0 java-jwt)
    - Role-based access: `ADMIN` vs. `STUDENT`

- **Documentation**
    - OpenAPI 3 → Swagger UI at `/swagger-ui.html`
    - Auto-generated request/response schemas and “Authorize” button

- **Testing**
    - Unit tests (JUnit 5 + Mockito + AssertJ)
    - Integration tests (SpringBootTest + MockMvc + Spring Security Test)

- **Clean Architecture**
    - Hexagonal layers: Domain → Use Cases → Infrastructure → Interfaces
    - DTOs, Mappers, `@PreAuthorize` annotations for clarity  

## 🏗️ Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
    - Spring Web
    - Spring Security
    - Spring Data JPA
    - Spring Validation
- **Build Tool**
- **JWT**
- **Database**: H2 (in-memory) by default; easily switchable to PostgreSQL
- **Documentation**: Springdoc OpenAPI (Swagger UI)
- **Testing**:
    - Unit: JUnit 5, Mockito, AssertJ
    - Integration: SpringBootTest, MockMvc, Spring Security Test

## 🔧 Configuration

### 1. Environment Variables (`.env`)

Create a file named `.env` in the project root (and add it to `.gitignore`):

```dotenv
# JWT secret for HS256 signing. Must be at least 32 characters.
JWT_SECRET_KEY=YourSuperSecretKeyOfAtLeast32Chars!!

# JWT expiration time in milliseconds (e.g. 24h = 86_400_000 ms)
JWT_EXPIRATION_MS=86400000
```

## 🚀 Getting Started

1. **Clone the repository**  
   ```bash
   git clone https://github.com/your-org/coursehub.git
   cd coursehub

2. **Configure Environment Variables**

Create a file named `.env` in the project root (and ensure it’s added to `.gitignore`), then add:
    
```dotenv
# Secret for signing/verifying JWT (HS256). Must be at least 32 characters.
JWT_SECRET_KEY=YourSuperSecretKeyOfAtLeast32Chars!!
    
# JWT expiration time in milliseconds (e.g. 24h = 86_400_000 ms)
JWT_EXPIRATION_MS=86400000
```

3. **Build & Run**

  ```bash
      ./gradlew clean bootRun
  ```

4. **Access Swagger UI**

Open your browser and navigate to: http://localhost:8080/swagger-ui.html

5. **Run Tests**

- **All tests (unit + integration)**
  ```bash
  ./gradlew test
  ```

6. **Build Fat JAR**

  ```bash
      ./gradlew clean build
  ```
The runnable JAR will be generated in: build/libs/coursehub-<version>.jar

8. **Import Insomnia Collection**

1. **Open Insomnia** and select **Create → Import/Export → Import Data → From File**
2. Choose `docs/insomnia/coursehub_collection.json` and import
3. Go to **Manage Environments** (top-right) and set:

   ```json
   {
     "base_url": "http://localhost:8080",
     "authToken": ""
   }
    ```
Send the Login request (POST /auth/login)—you should see “✔ Script passed” and authToken will be populated automatically.

All other requests in the collection already include the headers:
  
  ```
    Authorization: Bearer {{ authToken }}
    Content-Type: application/json
```

## 🔐 Roles & Endpoints Summary

| Method & Path                                   | Role           | Description                         |
|-------------------------------------------------|----------------|-------------------------------------|
| `POST /auth/login`                              | Public         | Obtain JWT                          |
| `GET  /api/courses`                             | ADMIN · USER   | List all courses                    |
| `GET  /api/courses/{id}`                        | ADMIN · USER   | Get course by ID                    |
| `POST /api/courses`                             | **ADMIN**      | Create a new course                 |
| `PUT  /api/courses/{id}`                        | **ADMIN**      | Update existing course              |
| `DELETE /api/courses/{id}`                      | **ADMIN**      | Delete course                       |
| `GET  /api/students`                            | ADMIN · USER   | List all students                   |
| `GET  /api/students/{id}`                       | ADMIN · USER   | Get student by ID                   |
| `POST /api/students/course/{courseId}`          | **ADMIN**      | Create student in a course          |
| `POST /api/enroll`                              | **ADMIN**      | Enroll existing student into course |
| `PUT  /api/students/{id}`                       | **ADMIN**      | Update student                      |
| `DELETE /api/students/{id}`                     | **ADMIN**      | Delete student                      |
| `GET  /api/students/course/{courseId}`          | ADMIN · USER   | List student full names by course   |

## 🧑‍💻 Demo Users

| Username | Password  | Role    |
|----------|-----------|---------|
| `admin`  | `admin123`| ADMIN   |
| `student`| `student123`| STUDENT |

---

## 📝 Useful Commands

```bash
# Run all tests
  ./gradlew test

# Run only integration tests
  ./gradlew test --tests "*IntegrationTest"

# Start with H2 in-memory
  ./gradlew bootRun

# Package fat-jar
  ./gradlew clean build
```

## ⚖️ License

This project is licensed under the **MIT License**.  
See the [LICENSE](LICENSE) file for details.
