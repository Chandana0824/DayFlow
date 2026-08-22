package com.dayflow.hrms.service;

import com.dayflow.hrms.dto.AdminDashboardResponse;
import com.dayflow.hrms.dto.EmployeeDashboardResponse;
import com.dayflow.hrms.model.Attendance;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.repository.AttendanceRepository;
import com.dayflow.hrms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceService attendanceService;

    public DashboardService(UserRepository userRepository,
                             AttendanceRepository attendanceRepository,
                             AttendanceService attendanceService) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.attendanceService = attendanceService;
    }

    /** 3.2.1 Employee dashboard: quick-access summary + recent activity. */
    public EmployeeDashboardResponse buildEmployeeDashboard(User user) {
        EmployeeDashboardResponse res = new EmployeeDashboardResponse();
        res.setFullName(user.getFullName() != null ? user.getFullName() : user.getEmployeeId());
        res.setEmployeeId(user.getEmployeeId());
        res.setDesignation(user.getDesignation());
        res.setTodayStatus(attendanceService.todayStatusLabel(user));

        // Leave counts are a seam for the 3.5 Leave module — defaulted to 0 until
        // that module's repository/service exists and is wired in here.
        res.setPendingLeaveCount(0);
        res.setApprovedLeaveThisMonth(0);

        List<Attendance> recent = attendanceRepository.findByEmployeeIdOrderByDateDesc(user.getEmployeeId());
        List<String> activity = recent.stream()
                .limit(5)
                .map(a -> a.getDate() + " — " + a.getStatus().name())
                .toList();
        res.setRecentActivity(activity);
        return res;
    }

    /** 3.2.2 Admin/HR dashboard: employee list, today's attendance snapshot. */
    public AdminDashboardResponse buildAdminDashboard() {
        List<User> allUsers = userRepository.findAll();
        LocalDate today = LocalDate.now();

        Map<String, Attendance> todaysByEmployee = new HashMap<>();
        for (Attendance a : attendanceRepository.findAllByDateBetweenOrderByDateDesc(today, today)) {
            todaysByEmployee.put(a.getEmployeeId(), a);
        }

        AdminDashboardResponse res = new AdminDashboardResponse();
        res.setTotalEmployees(allUsers.size());

        int present = 0;
        int onLeave = 0;
        List<AdminDashboardResponse.EmployeeSummary> summaries = allUsers.stream().map(u -> {
            AdminDashboardResponse.EmployeeSummary s = new AdminDashboardResponse.EmployeeSummary();
            s.setEmployeeId(u.getEmployeeId());
            s.setFullName(u.getFullName() != null ? u.getFullName() : u.getEmployeeId());
            s.setEmail(u.getEmail());
            s.setDesignation(u.getDesignation());
            s.setDepartment(u.getDepartment());
            s.setRole(u.getRole().name());

            Attendance today_ = todaysByEmployee.get(u.getEmployeeId());
            s.setTodayStatus(today_ == null ? "Not marked" : today_.getStatus().name());
            return s;
        }).toList();

        for (var s : summaries) {
            if ("PRESENT".equals(s.getTodayStatus()) || "HALF_DAY".equals(s.getTodayStatus())) present++;
            if ("LEAVE".equals(s.getTodayStatus())) onLeave++;
        }

        res.setEmployees(summaries);
        res.setPresentToday(present);
        res.setOnLeaveToday(onLeave);
        // Pending leave approvals: seam for the 3.5 Leave module, defaulted until wired in.
        res.setPendingLeaveApprovals(0);
        return res;
    }
}
