package com.backend.medconsult.entity.doctor;

import jakarta.persistence.*;

import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.doctor.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "doctors",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_doc_user", columnNames = {"user_id"}),
        @UniqueConstraint(name = "uq_doc_moh", columnNames = {"moh_registration_number"})
    },
    indexes = {
        @Index(name = "idx_doc_rating", columnList = "overall_rating DESC"),
        @Index(name = "idx_doc_active", columnList = "is_active, moh_verified")
    }
)
public class Doctor {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "doctor_id", nullable = false, updatable = false, length = 36)
    private UUID doctorId;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctors_user"))
    private User user;

    @Column(name = "moh_registration_number", nullable = false, length = 60)
    private String mohRegistrationNumber;

    @Column(name = "moh_verified", nullable = false)
    private Boolean mohVerified = false;

    @Column(name = "title", nullable = false, length = 30)
    private DoctorTitle title = DoctorTitle.DR;

    @Column(name = "bio_en", columnDefinition = "TEXT")
    private String bioEn;

    @Column(name = "bio_ar", columnDefinition = "TEXT")
    private String bioAr;

    @Column(name = "experience_years", nullable = false)
    private Short experienceYears = 0;

    @Column(name = "overall_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal overallRating = BigDecimal.valueOf(0.00);

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount = 0;

    @Column(name = "consultation_fee_sar", precision = 8, scale = 2)
    private BigDecimal consultationFeeSar;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Doctor() {
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setIsActive(Boolean active) {
        isActive = active;
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
}
