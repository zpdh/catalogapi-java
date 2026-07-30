# CatalogApi

A full-stack catalog management application built with **Spring Boot** and **React**, featuring authentication, role-based authorization, product and category management, RabbitMQ messaging, PostgreSQL persistence, and Dockerized deployment.

## Overview

CatalogApi provides a complete e-commerce-style catalog management platform consisting of:

- **Backend API** built with Spring Boot
- **Frontend application** built with React, TypeScript, and Vite
- **PostgreSQL** database for persistent storage
- **RabbitMQ** message broker for asynchronous communication
- **Flyway** database migrations
- **JWT Authentication** with role-based access control
- **Docker Compose** for local development and deployment

---

## Technology Stack

### Backend

- Java 21
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT Authentication
- Spring AMQP
- RabbitMQ
- Flyway
- PostgreSQL
- Maven
- Checkstyle
- SpotBugs

### Frontend

- React
- TypeScript
- Vite
- ESLint
- Nginx (Production Container)

### Infrastructure

- Docker
- Docker Compose

---

## Features

### Authentication & Authorization

- JWT-based authentication
- Role-based authorization
- User registration and login
- Protected API endpoints

### Product Management

- Create products
- Update products
- Delete products
- List products
- Product categorization

### Category Management

- Create categories
- Update categories
- Delete categories
- List categories

### Messaging

- RabbitMQ integration
- Asynchronous event processing
- Decoupled service communication

### Database Management

- PostgreSQL persistence
- Flyway versioned migrations
- Seed data support

---

## Getting Started

### Prerequisites

- Docker
- Docker Compose

---

## Running with Docker

From the project root:

```bash
docker-compose up --build
```

or:

```bash
docker compose up --build
```

---

## Accessing Services

- Frontend: http://localhost
- Backend API: http://localhost:8080
- RabbitMQ Management: http://localhost:15672
- PostgreSQL: localhost:5432

### RabbitMQ Credentials

```text
Username: admin
Password: admin
```

### PostgreSQL Credentials

```text
Database: catalogdb
Username: admin
Password: admin
```
