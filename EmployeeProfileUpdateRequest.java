package com.dayflow.hrms.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Fields an EMPLOYEE is allowed to edit on their own profile (3.3.2). */
public class EmployeeProfileUpdateRequest {

    @Size(max = 120, message = "Full name is too long")
    private String fullName;

    @Pattern(regexp = "^$|^[+0-9()\\-\\s]{7,20}$", message = "Enter a valid phone number")
    private String phone;

    @Size(max = 250, message = "Address is too long")
    private String address;

    @Size(max = 500, message = "Profile picture URL is too long")
    private String profilePictureUrl;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
}
