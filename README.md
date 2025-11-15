🔐 Spring Security

A lightweight Spring Boot + Spring Security + Thymeleaf web application demonstrating
role-based access control and custom login/logout functionality.


🚀 Features

✅ Custom login page using Thymeleaf

✅ Role-based access for dashboard, management, and administration pages

✅ Dynamic user and role display after login

✅ Secure logout with CSRF protection

✅ In-memory authentication (no database needed)

✅ Simple Bootstrap 5 responsive UI

✅ Configurable access rules via SecurityFilterChain



🧱 Tech Stack

| Layer / Purpose | Technology                                       |
| --------------- | ------------------------------------------------ |
| **Framework**   | Spring Boot                                      |
| **Security**    | Spring Security (In-Memory Authentication)       |
| **View Engine** | Thymeleaf + Thymeleaf Extras for Spring Security |
| **Frontend**    | Bootstrap 5                                      |
| **Build Tool**  | Maven                                            |
| **Language**    | Java 17+                                         |



🧩 Roles & Access

| Role         | Accessible Pages                      |
| ------------ | ------------------------------------- |
| **EMPLOYEE** | `/` (Dashboard)                       |
| **MANAGER**  | `/`, `/management`                    |
| **ADMIN**    | `/`, `/management`, `/administration` |



💡 Future Improvements

✨ Replace in-memory authentication with a database (JPA or JDBC)

✨ Add registration and password encoding

