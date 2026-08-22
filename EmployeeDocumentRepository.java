package com.dayflow.hrms.repository;

import com.dayflow.hrms.model.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {
    List<EmployeeDocument> findByEmployeeIdOrderByUploadedAtDesc(String employeeId);
}
