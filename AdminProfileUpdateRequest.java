package com.dayflow.hrms.dto;

import jakarta.validation.constraints.Size;

/** Full field set an ADMIN/HR can edit on any employee's profile (3.3.2). */
public class AdminProfileUpdateRequest {

    @Size(max = 120) private String fullName;
    private String phone;
    @Size(max = 250) private String address;
    @Size(max = 500) private String profilePictureUrl;

    @Size(max = 80) private String designation;
    @Size(max = 80) private String department;
    private String dateOfJoining;
    @Size(max = 120) private String reportingManager;

    private Double basicSalary;
    private Double allowances;
    private Double deductions;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(String dateOfJoining) { this.dateOfJoining = dateOfJoining; }
    public String getReportingManager() { return reportingManager; }
    public void setReportingManager(String reportingManager) { this.reportingManager = reportingManager; }
    public Double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(Double basicSalary) { this.basicSalary = basicSalary; }
    public Double getAllowances() { return allowances; }
    public void setAllowances(Double allowances) { this.allowances = allowances; }
    public Double getDeductions() { return deductions; }
    public void setDeductions(Double deductions) { this.deductions = deductions; }
}
