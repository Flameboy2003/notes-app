# 📝 NoteNest – Secure Notes Web Application

## 🚀 Overview
**NoteNest** is a full-stack notes management application that allows users to securely create, update, and manage their personal notes.

The application uses **JWT-based authentication** to ensure that each user can access only their own notes, with a smooth user experience powered by autosave and reactive UI updates.

---

## ✨ Features
- 🔐 User Authentication (JWT-based)
- 📝 Create, Update, Delete Notes (CRUD)
- 👤 User-specific note access (Authorization)
- ⚡ Autosave with debouncing
- 🗂 Notes grouped by date (Today / Yesterday / Earlier)
- 🎯 Responsive UI with smooth UX
- 🔄 Real-time UI updates without reload

---

## 🏗️ Tech Stack

### Frontend
- Nuxt (Vue 3)
- Tailwind CSS

### Backend
- Spring Boot
- Spring Security
- JWT (JSON Web Token)

### Database
- MySQL
- JPA / Hibernate

---

## 🔐 Authentication Flow
1. User logs in with email & password  
2. Backend validates credentials and generates a JWT token  
3. Token is stored in the frontend  
4. Each request includes the token in the `Authorization` header  
5. Backend validates the token using a JWT filter  
6. User-specific data is returned  

---

## 🔄 Application Flow
- User logs in → receives JWT token  
- User creates or edits notes  
- Notes are automatically saved using debouncing  
- Backend associates notes with the logged-in user  
- UI updates instantly without page reload  

---

## ⚙️ Key Concepts Implemented
- Stateless authentication using JWT  
- RESTful API design  
- Debounced autosave for performance optimization  
- Secure data access using user-based filtering  
- Handling SSR issues in Nuxt (client vs server execution)  
- Database optimization using `TEXT` for large content  

---

## 🚧 Challenges Faced
- Handling CORS with Spring Security  
- Fixing `localStorage` issues in SSR (Nuxt)  
- Managing autosave race conditions  
- Handling large note content beyond default DB limits  
- Syncing frontend state correctly  

---

## 📸 Screenshots
![image alt](https://github.com/Flameboy2003/notes-app/blob/b75476b45f35fd71ac0265469f1ecdcc9dc29a8d/images/1.png)
![image alt](https://github.com/Flameboy2003/notes-app/blob/b75476b45f35fd71ac0265469f1ecdcc9dc29a8d/images/2.png)
![image alt](https://github.com/Flameboy2003/notes-app/blob/b75476b45f35fd71ac0265469f1ecdcc9dc29a8d/images/3.png)
![image alt](https://github.com/Flameboy2003/notes-app/blob/b75476b45f35fd71ac0265469f1ecdcc9dc29a8d/images/4.png)
![image alt](https://github.com/Flameboy2003/notes-app/blob/b75476b45f35fd71ac0265469f1ecdcc9dc29a8d/images/5.png)
![image alt](https://github.com/Flameboy2003/notes-app/blob/b75476b45f35fd71ac0265469f1ecdcc9dc29a8d/images/6.png)
---

## 🛠️ Setup Instructions
### Frontend (NUXT)

- cd frontend
- npm install
  npm run dev
---
### Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run
