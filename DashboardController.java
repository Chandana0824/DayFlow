package com.dayflow.hrms.controller;

import com.dayflow.hrms.dto.AdminDashboardResponse;
import com.dayflow.hrms.dto.EmployeeDashboardResponse;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.service.DashboardService;
import com.dayflow.hrms.util.CurrentUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final CurrentUser currentUser;

    public DashboardController(DashboardService dashboardService, CurrentUser currentUser) {
        this.dashboardService = dashboardService;
        this.currentUser = currentUser;
    }

    /** 3.2.1 Employee Dashboard */
    @GetMapping("/employee")
    public EmployeeDashboardResponse employeeDashboard() {
        User user = currentUser.get();
        return dashboardService.buildEmployeeDashboard(user);
    }

    /** 3.2.2 Admin / HR Dashboard */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('HR')")
    public AdminDashboardResponse adminDashboard() {
        return dashboardService.buildAdminDashboard();
    }
}
