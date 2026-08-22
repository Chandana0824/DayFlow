package com.dayflow.hrms.dto;

import jakarta.validation.constraints.*;

public class SignUpRequest {

    @NotBlank(message = "Employee ID is required")
    @Pattern(regexp = "^[A-Za-z0-9-]{3,20}$", message = "Employee ID must be 3-20 letters, digits or hyphens")
    private String employeeId;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_\\-]).*$",
        message = "Password needs an uppercase letter, a lowercase letter, a number and a special character"
    )
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(EMPLOYEE|HR)$", message = "Role must be EMPLOYEE or HR")
    private String role;

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
