package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.entity.doctor.DoctorQualification;
import java.time.LocalDateTime;
import java.util.UUID;

public class DoctorQualificationResponseDto {

    private UUID qualId;
    private UUID doctorId;
    private String degree;
    private String institution;
    private String country;
    private Integer yearObtained;
    private Byte sortOrder;
    private LocalDateTime createdAt;

    public UUID getQualId() {
        return qualId;
    }

    public void setQualId(UUID qualId) {
        this.qualId = qualId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getYearObtained() {
        return yearObtained;
    }

    public void setYearObtained(Integer yearObtained) {
        this.yearObtained = yearObtained;
    }

    public Byte getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Byte sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static DoctorQualificationResponseDto fromEntity(DoctorQualification qual) {
        if (qual == null) {
            return null;
        }
        DoctorQualificationResponseDto dto = new DoctorQualificationResponseDto();
        dto.setQualId(qual.getQualId());
        if (qual.getDoctor() != null) {
            dto.setDoctorId(qual.getDoctor().getDoctorId());
        }
        dto.setDegree(qual.getDegree());
        dto.setInstitution(qual.getInstitution());
        dto.setCountry(qual.getCountry());
        dto.setYearObtained(qual.getYearObtained());
        dto.setSortOrder(qual.getSortOrder());
        dto.setCreatedAt(qual.getCreatedAt());
        return dto;
    }
}
