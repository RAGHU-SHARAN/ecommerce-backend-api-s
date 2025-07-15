# 🛒 E-commerce Backend API

This is a **Java Spring Boot-based RESTful API** backend for an e-commerce application. It supports key features like user authentication, product and cart management.

---

## 🚀 Features

- 🔐 **JWT Authentication & Authorization**
- 👤 User Registration & Role-based Login (Admin/User)
- 📦 Product & Inventory Management (CRUD)
- 🛒 Shopping Cart & Cart Item APIs
- 🗄️ H2 Database (File-based)

## 🛠️ Tech Stack

| Tech             | Details                             |
|------------------|-------------------------------------|
| Language         | Java 21                             |
| Framework        | Spring Boot 3+                      |
| Security         | Spring Security + JWT               |
| Persistence      | Spring Data JPA + H2 DB             |
| Build Tool       | Maven                               |
| API Docs         | Swagger (Springdoc)                 |

---

## 🔐 Role-based API Access

| API Endpoint                 | Access        |
|-----------------------------|----------------|
| `/api/v1/auth/**`           | Public         |
| `/api/v1/products/**`       | Admin only     |
| `/api/v1/carts/**`          | Authenticated  |
| `/api/v1/cartItems/**`      | Authenticated  |

