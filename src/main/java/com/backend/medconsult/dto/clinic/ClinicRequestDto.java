package com.backend.medconsult.dto.clinic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ClinicRequestDto {

    @NotBlank(message = "English name is required")
    @Size(max = 200, message = "English name must not exceed 200 characters")
    private String nameEn;

    @NotBlank(message = "Arabic name is required")
    @Size(max = 200, message = "Arabic name must not exceed 200 characters")
    private String nameAr;

    private String descriptionEn;
    private String descriptionAr;

    @Size(max = 500, message = "Logo URL must not exceed 500 characters")
    private String logoUrl;

    @Size(max = 500, message = "Website URL must not exceed 500 characters")
    private String website;

    @Email(message = "Email must be a valid email address")
    @Size(max = 180, message = "Email must not exceed 180 characters")
    private String email;

    @NotBlank(message = "Primary phone is required")
    @Pattern(regexp = "^\\+?[0-9 \\-]{7,20}$", message = "Primary phone must be a valid phone number")
    private String phonePrimary;

    @Pattern(regexp = "^\\+?[0-9 \\-]{7,20}$", message = "Secondary phone must be a valid phone number")
    private String phoneSecondary;

    @NotBlank(message = "MOH License Number is required")
    @Size(max = 60, message = "MOH License Number must not exceed 60 characters")
    private String mohLicenseNumber;

    private Boolean mohVerified;
    private LocalDateTime mohVerifiedAt;

    @Size(max = 60, message = "NAPHIES Facility ID must not exceed 60 characters")
    private String naphiesFacilityId;

    private Boolean isActive;

    // ── Getters & Setters ──────────────────────────────────────────────

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
}
