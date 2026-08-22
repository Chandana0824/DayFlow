package com.dayflow.hrms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DocumentUploadRequest {

    @NotBlank(message = "Document name is required")
    @Size(max = 120, message = "Name is too long")
    private String name;

    @NotBlank(message = "Document URL is required")
    @Size(max = 500, message = "URL is too long")
    private String url;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
