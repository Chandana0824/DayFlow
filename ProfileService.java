package com.dayflow.hrms.service;

import com.dayflow.hrms.dto.AdminProfileUpdateRequest;
import com.dayflow.hrms.dto.EmployeeProfileUpdateRequest;
import com.dayflow.hrms.dto.ProfileResponse;
import com.dayflow.hrms.exception.ApiException;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileResponse toResponse(User user) {
        ProfileResponse r = new ProfileResponse();
        r.setEmployeeId(user.getEmployeeId());
        r.setEmail(user.getEmail());
        r.setRole(user.getRole().name());
        r.setFullName(user.getFullName());
        r.setPhone(user.getPhone());
        r.setAddress(user.getAddress());
        r.setProfilePictureUrl(user.getProfilePictureUrl());
        r.setDesignation(user.getDesignation());
        r.setDepartment(user.getDepartment());
        r.setDateOfJoining(user.getDateOfJoining());
        r.setReportingManager(user.getReportingManager());
        r.setBasicSalary(user.getBasicSalary());
        r.setAllowances(user.getAllowances());
        r.setDeductions(user.getDeductions());

        double basic = user.getBasicSalary() == null ? 0 : user.getBasicSalary();
        double allow = user.getAllowances() == null ? 0 : user.getAllowances();
        double deduct = user.getDeductions() == null ? 0 : user.getDeductions();
        r.setNetSalary(basic + allow - deduct);
        return r;
    }

    /** 3.3.1 View own profile */
    public ProfileResponse getOwnProfile(User currentUser) {
        return toResponse(currentUser);
    }

    /** 3.3.1 Admin viewing any employee's profile */
    public ProfileResponse getProfileByEmployeeId(String employeeId) {
        User user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ApiException("Employee not found", HttpStatus.NOT_FOUND));
        return toResponse(user);
    }

    /** 3.3.2 Employee can only edit limited fields: address, phone, profile picture (+ display name) */
    @Transactional
    public ProfileResponse updateOwnProfile(User currentUser, EmployeeProfileUpdateRequest req) {
        if (req.getFullName() != null) currentUser.setFullName(req.getFullName());
        if (req.getPhone() != null) currentUser.setPhone(req.getPhone());
        if (req.getAddress() != null) currentUser.setAddress(req.getAddress());
        if (req.getProfilePictureUrl() != null) currentUser.setProfilePictureUrl(req.getProfilePictureUrl());
        userRepository.save(currentUser);
        return toResponse(currentUser);
    }

    /** 3.3.2 Admin can edit all employee details, including job & salary */
    @Transactional
    public ProfileResponse updateAsAdmin(String employeeId, AdminProfileUpdateRequest req) {
        User user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ApiException("Employee not found", HttpStatus.NOT_FOUND));

        if (req.getFullName() != null) user.setFullName(req.getFullName());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getAddress() != null) user.setAddress(req.getAddress());
        if (req.getProfilePictureUrl() != null) user.setProfilePictureUrl(req.getProfilePictureUrl());
        if (req.getDesignation() != null) user.setDesignation(req.getDesignation());
        if (req.getDepartment() != null) user.setDepartment(req.getDepartment());
        if (req.getDateOfJoining() != null) user.setDateOfJoining(req.getDateOfJoining());
        if (req.getReportingManager() != null) user.setReportingManager(req.getReportingManager());
        if (req.getBasicSalary() != null) user.setBasicSalary(req.getBasicSalary());
        if (req.getAllowances() != null) user.setAllowances(req.getAllowances());
        if (req.getDeductions() != null) user.setDeductions(req.getDeductions());

        userRepository.save(user);
        return toResponse(user);
    }
}
