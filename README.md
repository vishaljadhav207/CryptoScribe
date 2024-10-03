
---

# AI Chatbot

A web-based AI chatbot using **Spring Boot** for the backend and **Next.js** for the frontend, leveraging NLP for intelligent responses.

## Features

- Real-time chat interface
- RESTful API with Spring Boot
- User-friendly frontend with Next.js
- AI-powered responses

## Tech Stack

- Backend: Spring Boot (Java)
- Frontend: Next.js (React.js)
- Database: MySQL / MongoDB
- NLP: OpenAI API / GPT

## Setup

### Backend

1. Clone the repo and configure the database in `application.properties`.
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

2. Backend runs at `http://localhost:8080`.

### Frontend

1. Clone the repo and configure
   ```bash
   npm install
   npm run dev
   ```

2. Frontend runs at `http://localhost:3000`.

## API Endpoints

- `POST /api/chat`: Send message
- `GET /api/conversations`: Get chat history
