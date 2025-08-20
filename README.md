# Scholar-360 Academic News Aggregator

## Project Overview
Scholar-360 is a full-stack web application designed to centralize academic updates from top universities worldwide.  
The platform collects and organizes news related to **admissions, events, conferences, research highlights, internships, and job postings**.  
An AI-powered scraper (to be integrated later) gathers news, and an **admin** approves which stories get posted as blog entries.  

Users can explore categorized academic updates in an easy-to-navigate interface.

---

## Features
- Categorized postings: Admissions, Jobs, Internships, Research Positions, Exams.
- Admin authentication using JWT to restrict posting creation.
- Database-backed API for managing and retrieving postings.
- Frontend prioritizes **content first**, followed by **school** and **department** for clear readability.
- Supports latest-first ordering of postings.

---

## Tech Stack
### Backend
- **Spring Boot (Java)** – REST API backend
- **PostgreSQL** – Database
- **Spring Security with JWT** – Admin authentication
- **JPA/Hibernate** – Database access

### Frontend
- **React.js** – User interface
- **Axios** – API integration
- **TailwindCSS** – Styling

### Deployment
- Backend hosted on **Render** / **Heroku** / **AWS EC2**  
- Frontend hosted on **Netlify** / **Vercel**  

---

## API Endpoints
- `GET /api/postings` → Fetch all postings (latest first)  
- `POST /api/postings` → Create a new posting (Admin only)  
- `GET /api/postings/{id}` → Fetch a posting by ID  
- `DELETE /api/postings/{id}` → Delete a posting (Admin only)  

---
