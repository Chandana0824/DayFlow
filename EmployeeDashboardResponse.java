package com.dayflow.hrms.dto;

import java.util.List;

public class EmployeeDashboardResponse {
    private String fullName;
    private String employeeId;
    private String designation;
    private String todayStatus;      // e.g. "Present", "Not checked in yet"
    private int pendingLeaveCount;
    private int approvedLeaveThisMonth;
    private List<String> recentActivity;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getTodayStatus() { return todayStatus; }
    public void setTodayStatus(String todayStatus) { this.todayStatus = todayStatus; }
    public int getPendingLeaveCount() { return pendingLeaveCount; }
    public void setPendingLeaveCount(int pendingLeaveCount) { this.pendingLeaveCount = pendingLeaveCount; }
    public int getApprovedLeaveThisMonth() { return approvedLeaveThisMonth; }
    public void setApprovedLeaveThisMonth(int approvedLeaveThisMonth) { this.approvedLeaveThisMonth = approvedLeaveThisMonth; }
    public List<String> getRecentActivity() { return recentActivity; }
    public void setRecentActivity(List<String> recentActivity) { this.recentActivity = recentActivity; }
}
