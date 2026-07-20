package com.backend.medconsult.dto.platformAndCompliance;

import com.backend.medconsult.enums.platformAndCompliance.FileCategory;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class FileUploadRequestDto {

    private UUID patientId;

    @NotNull(message = "File category is required")
    private FileCategory category = FileCategory.OTHER;

    public FileUploadRequestDto() {
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public FileCategory getCategory() {
        return category;
    }

    public void setCategory(FileCategory category) {
        this.category = category;
    }
}
