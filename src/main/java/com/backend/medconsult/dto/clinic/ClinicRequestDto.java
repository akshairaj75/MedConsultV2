package com.backend.medconsult.dto.clinic;

import java.time.LocalDateTime;

public class ClinicRequestDto {

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
}
