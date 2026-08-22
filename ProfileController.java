package com.dayflow.hrms.controller;

import com.dayflow.hrms.dto.AdminProfileUpdateRequest;
import com.dayflow.hrms.dto.EmployeeProfileUpdateRequest;
import com.dayflow.hrms.dto.ProfileResponse;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.service.ProfileService;
import com.dayflow.hrms.util.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final CurrentUser currentUser;

    public ProfileController(ProfileService profileService, CurrentUser currentUser) {
        this.profileService = profileService;
        this.currentUser = currentUser;
    }

    /** 3.3.1 View own profile (Employee or HR viewing themselves) */
    @GetMapping("/me")
    public ProfileResponse getOwnProfile() {
        return profileService.getOwnProfile(currentUser.get());
    }

    /** 3.3.2 Employee edits limited fields on their own profile */
    @PutMapping("/me")
    public ProfileResponse updateOwnProfile(@Valid @RequestBody EmployeeProfileUpdateRequest request) {
        return profileService.updateOwnProfile(currentUser.get(), request);
    }

    /** 3.3.1 Admin views any employee's full profile */
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('HR')")
    public ProfileResponse getProfile(@PathVariable String employeeId) {
        return profileService.getProfileByEmployeeId(employeeId);
    }

    /** 3.3.2 Admin edits any employee's full details (job + salary included) */
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasRole('HR')")
    public ProfileResponse updateProfileAsAdmin(@PathVariable String employeeId,
                                                 @Valid @RequestBody AdminProfileUpdateRequest request) {
        return profileService.updateAsAdmin(employeeId, request);
    }
}
