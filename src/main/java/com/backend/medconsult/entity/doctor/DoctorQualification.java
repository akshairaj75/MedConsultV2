package com.backend.medconsult.entity.doctor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "doctor_qualifications",
    indexes = {
        @Index(name = "idx_dqual_doctor", columnList = "doctor_id")
    }
)
public class DoctorQualification {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "qual_id", nullable = false, updatable = false, length = 36)
    private UUID qualId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_qualifications_doctor"))
    private Doctor doctor;

    @Column(name = "degree", nullable = false, length = 100)
    private String degree;

    @Column(name = "institution", nullable = false, length = 200)
    private String institution;

    @Column(name = "country", length = 2, columnDefinition = "CHAR(2)")
    private String country;

    @Column(name = "year_obtained")
    private Integer yearObtained;

    @Column(name = "sort_order", nullable = false)
    private Byte sortOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DoctorQualification() {
    }

    public UUID getQualId() {
        return qualId;
    }

    public void setQualId(UUID qualId) {
        this.qualId = qualId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
}
