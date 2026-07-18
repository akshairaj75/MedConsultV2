package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.enums.doctor.DoctorTitle;
import java.math.BigDecimal;
import java.util.UUID;

public class DoctorRequestDto {

    private UUID userId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
}
