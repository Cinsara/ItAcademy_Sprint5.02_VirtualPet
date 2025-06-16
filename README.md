# 🐾 VirtualPet Backend

Welcome to the **VirtualPet** backend — an application where users can adopt, train, and care for their virtual pets. This backend is built with Java and Spring Boot and exposes a powerful REST API to manage users, pets, training, battles, inventory, and the shop.

---

## ⚙️ **Tecnologías utilizadas**

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white) Java 17
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white) Spring Boot
- 🐘 MySQL 
- 🔐 Spring Security + JWT
- 🛠️ Gradle
- 🌐 Swagger OpenAPI

---

## 🚀 Instalación y ejecución

1. **Clone the repository:**

   ```bash
   git clone https://github.com/tu-usuario/virtualpet-backend.git
   cd virtualpet-backend/VirtualPet

2. **Set up your databases:**

- Use MySQL for storage.

3. **Edit ```application.properties``` or ```application.yml```:**

   Configure your DB credentials, JWT secrets, and ports.

4. **Run the backend:**

   With Gradle:

```bash
./gradlew bootRun
```
  Or run it from your IDE (e.g., IntelliJ or Eclipse).

## 🔐 Authentication

- The backend uses JWT for stateless authentication.

- Protected routes require a token in the header:
```Authorization: Bearer <token>```

## 📁 Project Structure

```
VirtualPet/
├── controller/         # REST controllers
├── services/           # Business logic
├── model/              # Entities and DTOs
├── repositories/       # MySQL access
├── utils/              # Another files
├── config/             # JWT and security configuration
├── dto/                # Data transfer objects             
├── resources/
│   └── application.properties
└── VirtualPetApplication.java
```

## 🔗 Key Endpoints

Swagger UI is available at:

📚 ```http://localhost:8080/swagger-ui/index.html```

