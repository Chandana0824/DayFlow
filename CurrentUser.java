package com.dayflow.hrms.util;

import com.dayflow.hrms.exception.ApiException;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    private final UserRepository userRepository;

    public CurrentUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** Resolves the signed-in user from the JWT set on the security context by JwtAuthFilter. */
    public User get() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.UNAUTHORIZED));
    }

    public boolean isHr(User user) {
        return user.getRole().name().equals("HR");
    }
}
