package com.backend.medconsult.dto.platformAndCompliance;

import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.enums.platformAndCompliance.FileCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public class FileMetadataResponseDto {

    private UUID fileId;
    private UUID uploadedById;
    private String uploadedByName;
    private String originalFilename;
    private String mimeType;
    private Long sizeBytes;
    private FileCategory category;
    private Boolean isEncrypted;
    private String checksumSha256;
    private UUID patientId;
    private String patientName;
    private LocalDateTime createdAt;

    public FileMetadataResponseDto() {
    }

    public static FileMetadataResponseDto fromEntity(FileMetadata entity) {
        if (entity == null) {
            return null;
        }

        FileMetadataResponseDto dto = new FileMetadataResponseDto();
        dto.setFileId(entity.getFileId());

        if (entity.getUploadedBy() != null) {
            dto.setUploadedById(entity.getUploadedBy().getUserId());
            dto.setUploadedByName(entity.getUploadedBy().getFullName());
        }

        dto.setOriginalFilename(entity.getOriginalFilename());
        dto.setMimeType(entity.getMimeType());
        dto.setSizeBytes(entity.getSizeBytes());
        dto.setCategory(entity.getCategory());
        dto.setIsEncrypted(entity.getIsEncrypted());
        dto.setChecksumSha256(entity.getChecksumSha256());

        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getPatientId());
            if (entity.getPatient().getUser() != null) {
                dto.setPatientName(entity.getPatient().getUser().getFullName());
            }
        }

        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public UUID getUploadedById() {
        return uploadedById;
    }

    public void setUploadedById(UUID uploadedById) {
        this.uploadedById = uploadedById;
    }

    public String getUploadedByName() {
        return uploadedByName;
    }

    public void setUploadedByName(String uploadedByName) {
        this.uploadedByName = uploadedByName;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public FileCategory getCategory() {
        return category;
    }

    public void setCategory(FileCategory category) {
        this.category = category;
    }

    public Boolean getIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(Boolean encrypted) {
        isEncrypted = encrypted;
    }

    public String getChecksumSha256() {
        return checksumSha256;
    }

    public void setChecksumSha256(String checksumSha256) {
        this.checksumSha256 = checksumSha256;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
