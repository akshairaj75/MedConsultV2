package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.enums.doctor.DoctorTitle;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DoctorResponseDto {

    private UUID doctorId;
    private UUID userId;
    private String fullName;
    private String mohRegistrationNumber;
    private Boolean mohVerified;
    private DoctorTitle title;
    private String bioEn;
    private String bioAr;
    private Short experienceYears;
    private BigDecimal overallRating;
    private Integer reviewCount;
    private BigDecimal consultationFeeSar;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMohRegistrationNumber() {
        return mohRegistrationNumber;
    }

    public void setMohRegistrationNumber(String mohRegistrationNumber) {
        this.mohRegistrationNumber = mohRegistrationNumber;
    }

    public Boolean getMohVerified() {
        return mohVerified;
    }

    public void setMohVerified(Boolean mohVerified) {
        this.mohVerified = mohVerified;
    }

    public DoctorTitle getTitle() {
        return title;
    }

    public void setTitle(DoctorTitle title) {
        this.title = title;
    }

    public String getBioEn() {
        return bioEn;
    }

    public void setBioEn(String bioEn) {
        this.bioEn = bioEn;
    }

    public String getBioAr() {
        return bioAr;
    }

    public void setBioAr(String bioAr) {
        this.bioAr = bioAr;
    }

    public Short getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Short experienceYears) {
        this.experienceYears = experienceYears;
    }

    public BigDecimal getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(BigDecimal overallRating) {
        this.overallRating = overallRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public BigDecimal getConsultationFeeSar() {
        return consultationFeeSar;
    }

    public void setConsultationFeeSar(BigDecimal consultationFeeSar) {
        this.consultationFeeSar = consultationFeeSar;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static DoctorResponseDto fromEntity(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setDoctorId(doctor.getDoctorId());
        if (doctor.getUser() != null) {
            dto.setUserId(doctor.getUser().getUserId());
            dto.setFullName(doctor.getUser().getFullName());
        }
        dto.setMohRegistrationNumber(doctor.getMohRegistrationNumber());
        dto.setMohVerified(doctor.getMohVerified());
        dto.setTitle(doctor.getTitle());
        dto.setBioEn(doctor.getBioEn());
        dto.setBioAr(doctor.getBioAr());
        dto.setExperienceYears(doctor.getExperienceYears());
        dto.setOverallRating(doctor.getOverallRating());
        dto.setReviewCount(doctor.getReviewCount());
        dto.setConsultationFeeSar(doctor.getConsultationFeeSar());
        dto.setIsActive(doctor.getIsActive());
        dto.setCreatedAt(doctor.getCreatedAt());
        dto.setUpdatedAt(doctor.getUpdatedAt());
        return dto;
    }
}
