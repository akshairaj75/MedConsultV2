package com.backend.medconsult.entity.clinic;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "clinics",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_clinic_license", columnNames = {"moh_license_number"})
    },
    indexes = {
        @Index(name = "idx_clinic_active", columnList = "is_active, deleted_at"),
        @Index(name = "idx_clinic_rating", columnList = "overall_rating DESC"),
        @Index(name = "idx_clinic_name_en", columnList = "name_en")
    }
)
@SQLDelete(sql = "UPDATE clinics SET deleted_at = CURRENT_TIMESTAMP WHERE clinic_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Clinic {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "clinic_id", nullable = false, updatable = false, length = 36)
    private UUID clinicId;

    @Column(name = "name_en", nullable = false, length = 200)
    private String nameEn;

    @Column(name = "name_ar", nullable = false, length = 200)
    private String nameAr;

    @Column(name = "description_en", columnDefinition = "TEXT")
    private String descriptionEn;

    @Column(name = "description_ar", columnDefinition = "TEXT")
    private String descriptionAr;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "website", length = 500)
    private String website;

    @Column(name = "email", length = 180)
    private String email;

    @Column(name = "phone_primary", nullable = false, length = 20)
    private String phonePrimary;

    @Column(name = "phone_secondary", length = 20)
    private String phoneSecondary;

    @Column(name = "moh_license_number", nullable = false, length = 60)
    private String mohLicenseNumber;

    @Column(name = "moh_verified", nullable = false)
    private Boolean mohVerified = false;

    @Column(name = "moh_verified_at")
    private LocalDateTime mohVerifiedAt;

    @Column(name = "naphies_facility_id", length = 60)
    private String naphiesFacilityId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "overall_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal overallRating = BigDecimal.valueOf(0.00);

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Clinic() {
    }

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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
