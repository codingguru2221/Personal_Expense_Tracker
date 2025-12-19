@echo off
setlocal

echo Starting Personal Expense Tracker Application...
echo ==========================================

echo Checking if backend JAR exists...
cd backend
if not exist "target\personal-expense-tracker-0.0.1-SNAPSHOT.jar" (
    echo Building backend application...
    call mvn clean package
    if errorlevel 1 (
        echo Failed to build backend application.
        cd ..
        pause
        exit /b 1
    )
)

echo Starting Backend Server...
start "Backend Server" /MIN cmd /c "java -jar target/personal-expense-tracker-0.0.1-SNAPSHOT.jar"
cd ..

timeout /t 10 /nobreak >nul

echo Starting Frontend Server...
cd frontend
if not exist "node_modules" (
    echo Installing frontend dependencies...
    call npm install
    if errorlevel 1 (
        echo Failed to install frontend dependencies.
        cd ..
        pause
        exit /b 1
    )
)

start "Frontend Server" /MIN cmd /c "npm run dev"
cd ..

echo.
echo Both servers are starting up!
echo.
echo Backend API will be available at: http://localhost:8080
echo Frontend will be available at: http://localhost:5000
echo.
echo Press any key to exit this window (servers will continue running)...
pause >nul