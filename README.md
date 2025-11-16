# 🔐 Spring Security

A lightweight Spring Boot + Spring Security + Thymeleaf web application demonstrating
role-based access control, user management, and a custom authentication flow.

---

# 🚀 Features

### 🔑 Authentication & Authorization

✅ Custom login page using Thymeleaf

✅ Role-based access for dashboard, management, and administration pages

✅ Dynamic display of logged-in user and roles

✅ Secure logout with CSRF protection

✅ Password change modal allowing users to update their own password

### 👥 User Management (NEW)

The **Administration page** now includes full user management features backed by a real database:

✅ Displays all registered users (excluding the signed-in admin)

✅ Shows assigned roles/authorities

✅ Buttons for modifying user roles

✅ CRUD operations for user accounts

Currently implemented with **JdbcUserDetailsManager**, storing users in your relational database.

### 💄 UI

✅ Simple, clean, responsive Bootstrap 5 design

✅ Shared navbar with role-based visibility

✅ Modal-based user interactions (password change)

---

# 🧱 Tech Stack

| Layer / Purpose | Technology                                     |
| --------------- | ---------------------------------------------- |
| **Framework**   | Spring Boot                                    |
| **Security**    | Spring Security + JDBC Authentication          |
| **User Store**  | JdbcUserDetailsManager + relational database   |
| **View Engine** | Thymeleaf + Thymeleaf Extras (Spring Security) |
| **Frontend**    | Bootstrap 5                                    |
| **Build Tool**  | Maven                                          |
| **Language**    | Java 17+                                       |

---

# 🧩 Roles & Access

| Role         | Accessible Pages                      |
| ------------ | ------------------------------------- |
| **EMPLOYEE** | `/` (Dashboard)                       |
| **MANAGER**  | `/`, `/management`                    |
| **ADMIN**    | `/`, `/management`, `/administration` |

Admins additionally access the **User Management Table**, allowing administrative control over accounts and roles.

---

# 📦 Summary

This project provides a clean, extendable Spring Security setup with:

✔ Custom login

✔ Database-backed authentication

✔ Role-based access

✔ User management

✔ Password change

✔ Bootstrap UI

Ready for expansion into a full admin panel or enterprise-style security module.

---
