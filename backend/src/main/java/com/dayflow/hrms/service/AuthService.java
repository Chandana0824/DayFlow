package com.dayflow.hrms.service;

import com.dayflow.hrms.dto.AuthResponse;
import com.dayflow.hrms.dto.SignInRequest;
import com.dayflow.hrms.dto.SignUpRequest;
import com.dayflow.hrms.exception.ApiException;
import com.dayflow.hrms.model.Role;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.repository.UserRepository;
import com.dayflow.hrms.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil,
                        EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Transactional
    public AuthResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("An account with this email already exists", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new ApiException("This Employee ID is already registered", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setEmployeeId(request.getEmployeeId().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        user.setEmailVerified(false);
        user.setVerificationToken(UUID.randomUUID().toString());
        user.setVerificationTokenExpiry(Instant.now().plus(24, ChronoUnit.HOURS));

        userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());

        return new AuthResponse(
                null,
                user.getEmployeeId(),
                user.getEmail(),
                user.getRole().name(),
                "Account created. Check your email to verify your account before signing in."
        );
    }

    @Transactional
    public AuthResponse verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new ApiException("Invalid or already-used verification link", HttpStatus.BAD_REQUEST));

        if (user.getVerificationTokenExpiry() != null && user.getVerificationTokenExpiry().isBefore(Instant.now())) {
            throw new ApiException("Verification link has expired. Please sign up again or request a new link.", HttpStatus.BAD_REQUEST);
        }

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);
        userRepository.save(user);

        return new AuthResponse(null, user.getEmployeeId(), user.getEmail(), user.getRole().name(), "Email verified. You can now sign in.");
    }

    public AuthResponse signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new ApiException("Incorrect email or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ApiException("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        }

        if (!user.isEmailVerified()) {
            throw new ApiException("Please verify your email before signing in", HttpStatus.FORBIDDEN);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getEmployeeId());

        return new AuthResponse(token, user.getEmployeeId(), user.getEmail(), user.getRole().name(), "Signed in successfully");
    }
}
