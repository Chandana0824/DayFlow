package com.dayflow.hrms.dto;

public class AuthResponse {
    private String token;
    private String employeeId;
    private String email;
    private String role;
    private String message;

    public AuthResponse() {}

    public AuthResponse(String token, String employeeId, String email, String role, String message) {
        this.token = token;
        this.employeeId = employeeId;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
