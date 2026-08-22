package com.dayflow.hrms.service;

import com.dayflow.hrms.dto.DocumentResponse;
import com.dayflow.hrms.dto.DocumentUploadRequest;
import com.dayflow.hrms.exception.ApiException;
import com.dayflow.hrms.model.EmployeeDocument;
import com.dayflow.hrms.repository.EmployeeDocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class DocumentService {

    private final EmployeeDocumentRepository documentRepository;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneOffset.UTC);

    public DocumentService(EmployeeDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    private DocumentResponse toResponse(EmployeeDocument d) {
        return new DocumentResponse(d.getId(), d.getName(), d.getUrl(), FMT.format(d.getUploadedAt()));
    }

    /** 3.3.1 View documents — an employee's own, or (for HR) any employee's. */
    public List<DocumentResponse> listForEmployee(String employeeId) {
        return documentRepository.findByEmployeeIdOrderByUploadedAtDesc(employeeId)
                .stream().map(this::toResponse).toList();
    }

    /** Admin attaches a document to an employee's profile. */
    @Transactional
    public DocumentResponse addDocument(String employeeId, DocumentUploadRequest request) {
        EmployeeDocument doc = new EmployeeDocument();
        doc.setEmployeeId(employeeId);
        doc.setName(request.getName());
        doc.setUrl(request.getUrl());
        documentRepository.save(doc);
        return toResponse(doc);
    }

    /** Admin removes a document. */
    @Transactional
    public void deleteDocument(Long documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new ApiException("Document not found", HttpStatus.NOT_FOUND);
        }
        documentRepository.deleteById(documentId);
    }
}
