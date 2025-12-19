Write-Host "Starting Personal Expense Tracker Application..." -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green

Write-Host "Checking if backend JAR exists..." -ForegroundColor Yellow
Set-Location backend

if (-not (Test-Path "target/personal-expense-tracker-0.0.1-SNAPSHOT.jar")) {
    Write-Host "Building backend application..." -ForegroundColor Yellow
    $buildResult = Start-Process -NoNewWindow -FilePath "mvn" -ArgumentList "clean", "package" -Wait -PassThru
    if ($buildResult.ExitCode -ne 0) {
        Write-Host "Failed to build backend application." -ForegroundColor Red
        Set-Location ..
        $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        exit 1
    }
}

Write-Host "Starting Backend Server..." -ForegroundColor Yellow
Start-Process -NoNewWindow -FilePath "java" -ArgumentList "-jar", "target/personal-expense-tracker-0.0.1-SNAPSHOT.jar"
Set-Location ..

Start-Sleep -Seconds 10

Write-Host "Starting Frontend Server..." -ForegroundColor Yellow
Set-Location frontend

if (-not (Test-Path "node_modules")) {
    Write-Host "Installing frontend dependencies..." -ForegroundColor Yellow
    $installResult = Start-Process -NoNewWindow -FilePath "npm" -ArgumentList "install" -Wait -PassThru
    if ($installResult.ExitCode -ne 0) {
        Write-Host "Failed to install frontend dependencies." -ForegroundColor Red
        Set-Location ..
        $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        exit 1
    }
}

Start-Process -NoNewWindow -FilePath "npm" -ArgumentList "run", "dev"
Set-Location ..

Write-Host ""
Write-Host "Both servers are starting up!" -ForegroundColor Cyan
Write-Host ""
Write-Host "Backend API will be available at: http://localhost:8080" -ForegroundColor Cyan
Write-Host "Frontend will be available at: http://localhost:5000" -ForegroundColor Cyan
Write-Host ""
Write-Host "Press any key to exit this window (servers will continue running)..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")