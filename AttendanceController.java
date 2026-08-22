package com.dayflow.hrms.controller;

import com.dayflow.hrms.dto.AttendanceResponse;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.service.AttendanceService;
import com.dayflow.hrms.util.CurrentUser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final CurrentUser currentUser;

    public AttendanceController(AttendanceService attendanceService, CurrentUser currentUser) {
        this.attendanceService = attendanceService;
        this.currentUser = currentUser;
    }

    /** 3.4.1 Employee check-in */
    @PostMapping("/check-in")
    public AttendanceResponse checkIn() {
        return attendanceService.checkIn(currentUser.get());
    }

    /** 3.4.1 Employee check-out */
    @PostMapping("/check-out")
    public AttendanceResponse checkOut() {
        return attendanceService.checkOut(currentUser.get());
    }

    /** 3.4.2 Employee: own attendance only, daily/weekly view via date range */
    @GetMapping("/me")
    public List<AttendanceResponse> myAttendance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return attendanceService.getOwnAttendance(currentUser.get(), from, to);
    }

    /** 3.4.2 Admin/HR: attendance of all employees */
    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public List<AttendanceResponse> allAttendance(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return attendanceService.getAllAttendance(from, to);
    }

    /** 3.4.2 Admin/HR: attendance of one specific employee */
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('HR')")
    public List<AttendanceResponse> attendanceForEmployee(
            @PathVariable String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return attendanceService.getAttendanceForEmployee(employeeId, from, to);
    }
}
