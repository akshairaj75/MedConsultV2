package com.backend.medconsult.dto.clinic;

import com.backend.medconsult.entity.clinic.Clinic;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClinicResponseDto {

    private UUID clinicId;
    private String nameEn;
    private String nameAr;
    private String descriptionEn;
    private String descriptionAr;
    private String logoUrl;
    private String website;
    private String email;
    private String phonePrimary;
    private String phoneSecondary;
    private String mohLicenseNumber;
    private Boolean mohVerified;
    private LocalDateTime mohVerifiedAt;
    private String naphiesFacilityId;
    private Boolean isActive;
    private BigDecimal overallRating;
    private Integer reviewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getClinicId() {
        return clinicId;
    }

    public void setClinicId(UUID clinicId) {
        this.clinicId = clinicId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonePrimary() {
        return phonePrimary;
    }

    public void setPhonePrimary(String phonePrimary) {
        this.phonePrimary = phonePrimary;
    }

    public String getPhoneSecondary() {
        return phoneSecondary;
    }

    public void setPhoneSecondary(String phoneSecondary) {
        this.phoneSecondary = phoneSecondary;
    }

    public String getMohLicenseNumber() {
        return mohLicenseNumber;
    }

    public void setMohLicenseNumber(String mohLicenseNumber) {
        this.mohLicenseNumber = mohLicenseNumber;
    }

    public Boolean getMohVerified() {
        return mohVerified;
    }

    public void setMohVerified(Boolean mohVerified) {
        this.mohVerified = mohVerified;
    }

    public LocalDateTime getMohVerifiedAt() {
        return mohVerifiedAt;
    }

    public void setMohVerifiedAt(LocalDateTime mohVerifiedAt) {
        this.mohVerifiedAt = mohVerifiedAt;
    }

    public String getNaphiesFacilityId() {
        return naphiesFacilityId;
    }

    public void setNaphiesFacilityId(String naphiesFacilityId) {
        this.naphiesFacilityId = naphiesFacilityId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public static ClinicResponseDto fromEntity(Clinic clinic) {
        if (clinic == null) {
            return null;
        }
        ClinicResponseDto dto = new ClinicResponseDto();
        dto.setClinicId(clinic.getClinicId());
        dto.setNameEn(clinic.getNameEn());
        dto.setNameAr(clinic.getNameAr());
        dto.setDescriptionEn(clinic.getDescriptionEn());
        dto.setDescriptionAr(clinic.getDescriptionAr());
        dto.setLogoUrl(clinic.getLogoUrl());
        dto.setWebsite(clinic.getWebsite());
        dto.setEmail(clinic.getEmail());
        dto.setPhonePrimary(clinic.getPhonePrimary());
        dto.setPhoneSecondary(clinic.getPhoneSecondary());
        dto.setMohLicenseNumber(clinic.getMohLicenseNumber());
        dto.setMohVerified(clinic.getMohVerified());
        dto.setMohVerifiedAt(clinic.getMohVerifiedAt());
        dto.setNaphiesFacilityId(clinic.getNaphiesFacilityId());
        dto.setIsActive(clinic.getIsActive());
        dto.setOverallRating(clinic.getOverallRating());
        dto.setReviewCount(clinic.getReviewCount());
        dto.setCreatedAt(clinic.getCreatedAt());
        dto.setUpdatedAt(clinic.getUpdatedAt());
        return dto;
    }
}
