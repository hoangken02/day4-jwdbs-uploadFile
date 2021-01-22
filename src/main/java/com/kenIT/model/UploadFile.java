package com.kenIT.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFile {
    private String description;
    private CommonsMultipartFile[] files;

    public UploadFile() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommonsMultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(CommonsMultipartFile[] files) {
        this.files = files;
    }

    public UploadFile(String description, CommonsMultipartFile[] files) {
        this.description = description;
        this.files = files;
    }
}
