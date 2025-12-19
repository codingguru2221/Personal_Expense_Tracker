# Personal Expense Tracker

A complete full-stack application for tracking personal expenses with a React/TypeScript frontend and Spring Boot backend.

## Project Structure

- `backend/` - Spring Boot backend application
- `frontend/` - React/TypeScript frontend application
- `start-app.bat` - Windows batch script to start both applications
- `start-app.sh` - Linux/Mac shell script to start both applications
- `start-app.ps1` - PowerShell script for Windows

## Quick Start

### Prerequisites
- Java 17
- Node.js and npm
- Maven

### Automated Startup
The automated scripts will automatically build the applications if needed:

- **Windows**: Double-click `start-app.bat` or run `start-app.ps1` in PowerShell
- **Linux/Mac**: Run `chmod +x start-app.sh && ./start-app.sh`

### Manual Startup
See [RUNNING.md](RUNNING.md) for detailed instructions.

## Accessing the Application

- **Frontend**: http://localhost:5000
- **Backend API**: http://localhost:8080
- **API Documentation**: See backend README for endpoint details

## Features

### Backend
- RESTful API with Spring Boot
- JWT-based authentication
- MySQL/H2 database support
- Entity relationships for users, accounts, transactions, loans
- Data seeding for demo purposes

### Frontend
- React with TypeScript
- TanStack Query for data fetching
- Responsive UI with Tailwind CSS
- Multi-step user registration
- Dashboard with financial analytics

## Documentation

- [Backend README](backend/README.md)
- [Frontend README](frontend/README.md) (if available)
- [Running Instructions](RUNNING.md)
- [Backend Summary](backend/SUMMARY.md)

## Development

### Backend
```bash
cd backend
mvn spring-boot:run
```

### Frontend
```bash
cd frontend
npm run dev
```