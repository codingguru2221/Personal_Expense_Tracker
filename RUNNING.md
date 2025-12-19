# Running the Personal Expense Tracker Application

This document explains how to run both the backend and frontend applications simultaneously using the provided automation scripts.

## Prerequisites

Before running the application, make sure you have:

1. Java 17 installed
2. Node.js and npm installed
3. Maven installed (for backend)

The automated scripts will automatically build the applications if needed.

## Automated Startup Scripts

The automated scripts will automatically:
1. Check if the backend JAR file exists, and build it if missing
2. Check if frontend dependencies are installed, and install them if missing
3. Start both applications

### Windows Users

Choose one of the following options:

#### Option 1: Batch File (Recommended for Windows)
Double-click on `start-app.bat` or run from command prompt:
```cmd
start-app.bat
```

#### Option 2: PowerShell Script
Right-click on `start-app.ps1` and select "Run with PowerShell" or run from PowerShell:
```powershell
.\start-app.ps1
```

### Linux/Mac Users
Make the script executable and run it:
```bash
chmod +x start-app.sh
./start-app.sh
```

## Manual Startup

If you prefer to start the applications manually:

### Backend
```bash
cd backend
java -jar target/personal-expense-tracker-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd frontend
npm run dev
```

## Accessing the Applications

After startup:

- **Backend API**: http://localhost:8080
- **Frontend UI**: http://localhost:5000

## Stopping the Applications

### When using automated scripts:
- **Windows**: Close the command prompt/PowerShell window that opened for each server
- **Linux/Mac**: Press Ctrl+C in the terminal where the script is running

### When started manually:
- Press Ctrl+C in each terminal window where the applications are running

## Troubleshooting

1. **Port conflicts**: If ports 8080 or 5000 are already in use, the applications will fail to start
2. **Build issues**: Make sure you've built both applications before running the scripts
3. **Java/Node not found**: Ensure Java and Node.js are properly installed and in your PATH

## H2 Database Console

When running the backend with the H2 database, you can access the database console at:
http://localhost:8080/h2-console

Connection details:
- JDBC URL: jdbc:h2:mem:testdb
- User: sa
- Password: (leave empty)