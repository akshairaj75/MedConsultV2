package com.backend.medconsult.dto.clinic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

import com.backend.medconsult.entity.clinic.Clinic;


public class ClinicDetailResponse {

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

    // Nested collections
    private List<ClinicBranchResponseDto> branches;
    private List<ClinicSpecialtyResponseDto> specialties;
    private List<ClinicLanguageResponseDto> languages;
    private List<ClinicInsuranceResponseDto> insurances;

    // ── Getters & Setters ──────────────────────────────────────────────

    public UUID getClinicId() { return clinicId; }
    public void setClinicId(UUID clinicId) { this.clinicId = clinicId; }

    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }

    public String getNameAr() { return nameAr; }
    public void setNameAr(String nameAr) { this.nameAr = nameAr; }

    public String getDescriptionEn() { return descriptionEn; }
    public void setDescriptionEn(String descriptionEn) { this.descriptionEn = descriptionEn; }

    public String getDescriptionAr() { return descriptionAr; }
    public void setDescriptionAr(String descriptionAr) { this.descriptionAr = descriptionAr; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhonePrimary() { return phonePrimary; }
    public void setPhonePrimary(String phonePrimary) { this.phonePrimary = phonePrimary; }

    public String getPhoneSecondary() { return phoneSecondary; }
    public void setPhoneSecondary(String phoneSecondary) { this.phoneSecondary = phoneSecondary; }

    public String getMohLicenseNumber() { return mohLicenseNumber; }
    public void setMohLicenseNumber(String mohLicenseNumber) { this.mohLicenseNumber = mohLicenseNumber; }

    public Boolean getMohVerified() { return mohVerified; }
    public void setMohVerified(Boolean mohVerified) { this.mohVerified = mohVerified; }

    public LocalDateTime getMohVerifiedAt() { return mohVerifiedAt; }
    public void setMohVerifiedAt(LocalDateTime mohVerifiedAt) { this.mohVerifiedAt = mohVerifiedAt; }

    public String getNaphiesFacilityId() { return naphiesFacilityId; }
    public void setNaphiesFacilityId(String naphiesFacilityId) { this.naphiesFacilityId = naphiesFacilityId; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public BigDecimal getOverallRating() { return overallRating; }
    public void setOverallRating(BigDecimal overallRating) { this.overallRating = overallRating; }

    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<ClinicBranchResponseDto> getBranches() { return branches; }
    public void setBranches(List<ClinicBranchResponseDto> branches) { this.branches = branches; }

    public List<ClinicSpecialtyResponseDto> getSpecialties() { return specialties; }
    public void setSpecialties(List<ClinicSpecialtyResponseDto> specialties) { this.specialties = specialties; }

    public List<ClinicLanguageResponseDto> getLanguages() { return languages; }
    public void setLanguages(List<ClinicLanguageResponseDto> languages) { this.languages = languages; }

    public List<ClinicInsuranceResponseDto> getInsurances() { return insurances; }
    public void setInsurances(List<ClinicInsuranceResponseDto> insurances) { this.insurances = insurances; }

    // ── Factory ────────────────────────────────────────────────────────

    /**
     * Maps only the scalar fields from the entity.
     * The caller is responsible for populating the nested list fields.
     */
    public static ClinicDetailResponse fromEntity(Clinic clinic) {
        if (clinic == null) return null;
        ClinicDetailResponse r = new ClinicDetailResponse();
        r.setClinicId(clinic.getClinicId());
        r.setNameEn(clinic.getNameEn());
        r.setNameAr(clinic.getNameAr());
        r.setDescriptionEn(clinic.getDescriptionEn());
        r.setDescriptionAr(clinic.getDescriptionAr());
        r.setLogoUrl(clinic.getLogoUrl());
        r.setWebsite(clinic.getWebsite());
        r.setEmail(clinic.getEmail());
        r.setPhonePrimary(clinic.getPhonePrimary());
        r.setPhoneSecondary(clinic.getPhoneSecondary());
        r.setMohLicenseNumber(clinic.getMohLicenseNumber());
        r.setMohVerified(clinic.getMohVerified());
        r.setMohVerifiedAt(clinic.getMohVerifiedAt());
        r.setNaphiesFacilityId(clinic.getNaphiesFacilityId());
        r.setIsActive(clinic.getIsActive());
        r.setOverallRating(clinic.getOverallRating());
        r.setReviewCount(clinic.getReviewCount());
        r.setCreatedAt(clinic.getCreatedAt());
        r.setUpdatedAt(clinic.getUpdatedAt());
        return r;
    }
}
