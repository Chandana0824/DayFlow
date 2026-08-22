package com.dayflow.hrms.service;

import com.dayflow.hrms.dto.AttendanceResponse;
import com.dayflow.hrms.exception.ApiException;
import com.dayflow.hrms.model.Attendance;
import com.dayflow.hrms.model.AttendanceStatus;
import com.dayflow.hrms.model.User;
import com.dayflow.hrms.repository.AttendanceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    private AttendanceResponse toResponse(Attendance a) {
        return new AttendanceResponse(
                a.getId(),
                a.getEmployeeId(),
                a.getDate().toString(),
                a.getCheckIn() == null ? null : a.getCheckIn().format(TIME_FMT),
                a.getCheckOut() == null ? null : a.getCheckOut().format(TIME_FMT),
                a.getStatus().name()
        );
    }

    /** 3.4.1 Employee check-in for today. Creates today's record if missing, marks PRESENT. */
    @Transactional
    public AttendanceResponse checkIn(User user) {
        LocalDate today = LocalDate.now();
        Attendance record = attendanceRepository.findByEmployeeIdAndDate(user.getEmployeeId(), today)
                .orElseGet(() -> {
                    Attendance a = new Attendance();
                    a.setEmployeeId(user.getEmployeeId());
                    a.setDate(today);
                    return a;
                });

        if (record.getCheckIn() != null) {
            throw new ApiException("Already checked in today", HttpStatus.CONFLICT);
        }

        record.setCheckIn(LocalTime.now());
        record.setStatus(AttendanceStatus.PRESENT);
        attendanceRepository.save(record);
        return toResponse(record);
    }

    /** 3.4.1 Employee check-out for today. */
    @Transactional
    public AttendanceResponse checkOut(User user) {
        LocalDate today = LocalDate.now();
        Attendance record = attendanceRepository.findByEmployeeIdAndDate(user.getEmployeeId(), today)
                .orElseThrow(() -> new ApiException("You haven't checked in today yet", HttpStatus.BAD_REQUEST));

        if (record.getCheckIn() == null) {
            throw new ApiException("You haven't checked in today yet", HttpStatus.BAD_REQUEST);
        }
        if (record.getCheckOut() != null) {
            throw new ApiException("Already checked out today", HttpStatus.CONFLICT);
        }

        LocalTime now = LocalTime.now();
        record.setCheckOut(now);

        // Simple half-day heuristic: less than 4 hours between check-in and check-out.
        long minutesWorked = java.time.Duration.between(record.getCheckIn(), now).toMinutes();
        if (minutesWorked < 240) {
            record.setStatus(AttendanceStatus.HALF_DAY);
        }

        attendanceRepository.save(record);
        return toResponse(record);
    }

    /** 3.4.2 Employee: own attendance only, daily/weekly range. */
    public List<AttendanceResponse> getOwnAttendance(User user, LocalDate from, LocalDate to) {
        return attendanceRepository
                .findByEmployeeIdAndDateBetweenOrderByDateDesc(user.getEmployeeId(), from, to)
                .stream().map(this::toResponse).toList();
    }

    /** 3.4.2 Admin/HR: attendance of all employees for a date range. */
    public List<AttendanceResponse> getAllAttendance(LocalDate from, LocalDate to) {
        return attendanceRepository.findAllByDateBetweenOrderByDateDesc(from, to)
                .stream().map(this::toResponse).toList();
    }

    /** 3.4.2 Admin/HR: attendance of one specific employee. */
    public List<AttendanceResponse> getAttendanceForEmployee(String employeeId, LocalDate from, LocalDate to) {
        return attendanceRepository
                .findByEmployeeIdAndDateBetweenOrderByDateDesc(employeeId, from, to)
                .stream().map(this::toResponse).toList();
    }

    public String todayStatusLabel(User user) {
        return attendanceRepository.findByEmployeeIdAndDate(user.getEmployeeId(), LocalDate.now())
                .map(a -> {
                    if (a.getCheckOut() != null) return "Checked out";
                    if (a.getCheckIn() != null) return "Checked in — " + a.getStatus().name();
                    return "Not checked in yet";
                })
                .orElse("Not checked in yet");
    }
}
