# 📚 CourseHub API

![Java 21](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot 3](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring)
![Gradle](https://img.shields.io/badge/Build-Gradle-0388d1?logo=gradle)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow)

A demo **REST API** for managing **Courses** and **Students** (1 : N).  
Built with a **hexagonal/clean architecture**, fully tested, secured with **JWT** and **role-based access control**.

---

## 🏗️ Tech Stack
* Java 21 / JDK 21
* Spring Boot 3.X   `web` ▪ `security` ▪ `validation`
* Gradle Kotlin DSL
* JJWT (Auth0 java-jwt)
* Jackson Datatype JSR-310 (Java Time)
* Lombok
* JUnit 5, Mockito, AssertJ, Spring Security Test
* H2 database (runtime) – switchable

---
## 🚀 Getting Started

### Prerrequisitos
| Requisito | Versión |
|-----------|---------|
| **JDK**   | 21      |
| **Gradle**| 8 + (o usa el wrapper `./gradlew`) |

---

### Variables de entorno (`.env`)
```env
# ⚠️  Usa 32+ caracteres ASCII para HS256
JWT_SECRET_KEY=UnaClaveSuperSecretaDe32CaracteresMin
# Token válido durante 24 h (en milisegundos)
JWT_EXPIRATION_MS=86400000


