package com.dayflow.hrms.dto;

import java.util.List;

public class AdminDashboardResponse {
    private int totalEmployees;
    private int presentToday;
    private int onLeaveToday;
    private int pendingLeaveApprovals;
    private List<EmployeeSummary> employees;

    public static class EmployeeSummary {
        private String employeeId;
        private String fullName;
        private String email;
        private String designation;
        private String department;
        private String role;
        private String todayStatus;

        public String getEmployeeId() { return employeeId; }
        public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getDesignation() { return designation; }
        public void setDesignation(String designation) { this.designation = designation; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getTodayStatus() { return todayStatus; }
        public void setTodayStatus(String todayStatus) { this.todayStatus = todayStatus; }
    }

    public int getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(int totalEmployees) { this.totalEmployees = totalEmployees; }
    public int getPresentToday() { return presentToday; }
    public void setPresentToday(int presentToday) { this.presentToday = presentToday; }
    public int getOnLeaveToday() { return onLeaveToday; }
    public void setOnLeaveToday(int onLeaveToday) { this.onLeaveToday = onLeaveToday; }
    public int getPendingLeaveApprovals() { return pendingLeaveApprovals; }
    public void setPendingLeaveApprovals(int pendingLeaveApprovals) { this.pendingLeaveApprovals = pendingLeaveApprovals; }
    public List<EmployeeSummary> getEmployees() { return employees; }
    public void setEmployees(List<EmployeeSummary> employees) { this.employees = employees; }
}
