# Personal Expense Tracker - Backend

This is the backend for the Personal Expense Tracker application built with Spring Boot.

For easy startup of both frontend and backend, see the [RUNNING.md](../RUNNING.md) file in the root directory.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security (JWT-based auth)
- MySQL
- Hibernate
- Lombok
- Validation (Jakarta Validation)
- BCrypt for password hashing

## Prerequisites

- Java 17
- Maven
- MySQL Server

## Setup Instructions

1. **Database Setup**
   - Create a MySQL database named `personal_expense_tracker`
   - Update the database credentials in `src/main/resources/application.yml` if needed

2. **Build the Application**
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   
   Or run the jar file:
   ```bash
   java -jar target/personal-expense-tracker-0.0.1-SNAPSHOT.jar
   ```

## API Endpoints

### Auth
- `POST /api/users` - Signup
- `POST /api/login` - Login
- `GET /api/users/{id}` - Get user profile

### Banks
- `GET /api/banks` - List all banks

### Accounts
- `GET /api/accounts?userId={id}` - Get user accounts
- `POST /api/accounts` - Create account
- `PATCH /api/accounts/{id}/link` - Link/unlink account

### Transactions
- `GET /api/transactions?userId={id}` - Get user transactions
- `POST /api/transactions` - Create transaction

### Loans
- `GET /api/loans?userId={id}` - Get user loans

### Dashboard
- `GET /api/dashboard/{userId}` - Get dashboard data

## Database Schema

The application automatically creates the following tables:
- users
- banks
- accounts
- transactions
- loans
- user_preferences

## Security

- Passwords and PINs are hashed using BCrypt
- JWT tokens expire after 24 hours
- All protected routes require a valid JWT token

## Seeded Data

On first run, the application seeds:
- 10 demo banks
- A sample user (username: johndoe, password: password123)
- Sample accounts, transactions, and loans for testing