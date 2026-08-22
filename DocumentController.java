package com.dayflow.hrms.controller;

import com.dayflow.hrms.dto.DocumentResponse;
import com.dayflow.hrms.dto.DocumentUploadRequest;
import com.dayflow.hrms.service.DocumentService;
import com.dayflow.hrms.util.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final CurrentUser currentUser;

    public DocumentController(DocumentService documentService, CurrentUser currentUser) {
        this.documentService = documentService;
        this.currentUser = currentUser;
    }

    /** 3.3.1 Employee views their own documents */
    @GetMapping("/me")
    public List<DocumentResponse> myDocuments() {
        return documentService.listForEmployee(currentUser.get().getEmployeeId());
    }

    /** 3.3.1 Admin views any employee's documents */
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('HR')")
    public List<DocumentResponse> documentsForEmployee(@PathVariable String employeeId) {
        return documentService.listForEmployee(employeeId);
    }

    /** Admin attaches a document to an employee's profile (part of 3.3.2 "admin can edit all employee details") */
    @PostMapping("/{employeeId}")
    @PreAuthorize("hasRole('HR')")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentResponse addDocument(@PathVariable String employeeId, @Valid @RequestBody DocumentUploadRequest request) {
        return documentService.addDocument(employeeId, request);
    }

    /** Admin removes a document */
    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasRole('HR')")
    public void deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
    }
}
