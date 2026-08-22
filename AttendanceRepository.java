package com.dayflow.hrms.repository;

import com.dayflow.hrms.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeIdAndDate(String employeeId, LocalDate date);
    List<Attendance> findByEmployeeIdAndDateBetweenOrderByDateDesc(String employeeId, LocalDate from, LocalDate to);
    List<Attendance> findAllByDateBetweenOrderByDateDesc(LocalDate from, LocalDate to);
    List<Attendance> findByEmployeeIdOrderByDateDesc(String employeeId);
}
