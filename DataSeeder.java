package com.dayflow.hrms.config;

import com.dayflow.hrms.model.Role;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Creates two ready-to-use, already-verified accounts on first startup so
 * you can sign in immediately without going through sign-up or email
 * verification at all. Safe to leave in for a hackathon demo; delete this
 * file (or wrap it in a profile check) before any real deployment.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seed("EMP-1001", "employee@dayflow.test", "Employee@123", Role.EMPLOYEE, "Demo Employee");
        seed("HR-2001", "hr@dayflow.test", "HrAdmin@123", Role.HR, "Demo HR Officer");

        log.info("=====================================================");
        log.info(" Dayflow demo accounts ready — sign in directly, no");
        log.info(" verification needed:");
        log.info("   Employee  ->  employee@dayflow.test / Employee@123");
        log.info("   HR/Admin  ->  hr@dayflow.test / HrAdmin@123");
        log.info("=====================================================");
    }

    private void seed(String employeeId, String email, String rawPassword, Role role, String fullName) {
        if (userRepository.existsByEmail(email)) return;

        User user = new User();
        user.setEmployeeId(employeeId);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setEmailVerified(true); // pre-verified — skips the email step entirely
        user.setFullName(fullName);
        userRepository.save(user);
    }
}
