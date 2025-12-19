package com.expense.tracker.controller;

import com.expense.tracker.dto.DashboardDto;
import com.expense.tracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{userId}")
    public ResponseEntity<DashboardDto> getDashboardData(@PathVariable Long userId) {
        DashboardDto dashboardDto = dashboardService.getDashboardData(userId);
        return new ResponseEntity<>(dashboardDto, HttpStatus.OK);
    }
}