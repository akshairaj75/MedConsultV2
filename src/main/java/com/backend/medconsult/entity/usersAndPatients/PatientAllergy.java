package com.backend.medconsult.entity.usersAndPatients;

import jakarta.persistence.*;

import com.backend.medconsult.enums.usersAndPatients.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "patient_allergies",
    indexes = {
        @Index(name = "idx_allergy_patient", columnList = "patient_id")
    }
)
public class PatientAllergy {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "allergy_id", nullable = false, updatable = false, length = 36)
    private UUID allergyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_patient_allergies_patient"))
    private Patient patient;

    @Column(name = "allergen", nullable = false, length = 150)
    private String allergen;

    @Enumerated(EnumType.STRING)
    @Column(name = "allergy_type", nullable = false, columnDefinition = "ENUM('drug','food','environmental','other')")
    private AllergyType allergyType = AllergyType.other;

    @Column(name = "reaction", length = 255)
    private String reaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, columnDefinition = "ENUM('mild','moderate','severe')")
    private Severity severity = Severity.moderate;

    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by", foreignKey = @ForeignKey(name = "fk_patient_allergies_recorder"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User recordedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PatientAllergy() {
    }

    public UUID getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(UUID allergyId) {
        this.allergyId = allergyId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public AllergyType getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(AllergyType allergyType) {
        this.allergyType = allergyType;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public User getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(User recordedBy) {
        this.recordedBy = recordedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
