#!/bin/bash

echo "Starting Personal Expense Tracker Application..."
echo "=========================================="

echo "Checking if backend JAR exists..."
cd backend

if [ ! -f "target/personal-expense-tracker-0.0.1-SNAPSHOT.jar" ]; then
    echo "Building backend application..."
    mvn clean package
    if [ $? -ne 0 ]; then
        echo "Failed to build backend application."
        cd ..
        exit 1
    fi
fi

echo "Starting Backend Server..."
java -jar target/personal-expense-tracker-0.0.1-SNAPSHOT.jar &
BACKEND_PID=$!
cd ..

sleep 10

echo "Starting Frontend Server..."
cd frontend

if [ ! -d "node_modules" ]; then
    echo "Installing frontend dependencies..."
    npm install
    if [ $? -ne 0 ]; then
        echo "Failed to install frontend dependencies."
        cd ..
        exit 1
    fi
fi

npm run dev &
FRONTEND_PID=$!
cd ..

echo ""
echo "Both servers are starting up!"
echo ""
echo "Backend API will be available at: http://localhost:8080"
echo "Frontend will be available at: http://localhost:5000"
echo ""
echo "Press Ctrl+C to stop both servers"

# Wait for both processes
wait $BACKEND_PID
wait $FRONTEND_PID