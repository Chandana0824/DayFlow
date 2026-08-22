package com.dayflow.hrms.dto;

public class AttendanceResponse {
    private Long id;
    private String employeeId;
    private String date;      // yyyy-MM-dd
    private String checkIn;   // HH:mm, nullable
    private String checkOut;  // HH:mm, nullable
    private String status;

    public AttendanceResponse() {}

    public AttendanceResponse(Long id, String employeeId, String date, String checkIn, String checkOut, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }
    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
