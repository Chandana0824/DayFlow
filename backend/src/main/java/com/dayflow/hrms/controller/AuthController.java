package com.dayflow.hrms.controller;

// Using Core Java/Spring Boot annotations as a boilerplate
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // 3.1.2 Sign In
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest request) {
        // TODO: Validate credentials against SQLite database
        // TODO: Return error messages for incorrect credentials
        // TODO: Generate and return session token / JWT
        
        System.out.println("Login attempt for email: " + request.getEmail());
        
        // Mock Response
        return ResponseEntity.ok().body("{\"message\": \"Login successful\", \"token\": \"mock-jwt-token\"}");
    }

    // 3.1.1 Sign Up
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest request) {
        // TODO: Validate Employee ID, Email format, Password security rules
        // TODO: Save user to SQLite database with specific Role (Employee / HR)
        // TODO: Trigger Email verification (mock or real)
        
        System.out.println("Signup attempt for: " + request.getEmail() + " as " + request.getRole());
        
        // Mock Response
        return ResponseEntity.ok().body("{\"message\": \"User registered successfully. Please verify email.\"}");
    }
}

// DTOs (Data Transfer Objects)
class LoginRequest {
    private String email;
    private String password;

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

class SignupRequest {
    private String employeeId;
    private String email;
    private String password;
    private String role; // "EMPLOYEE" or "HR"

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
