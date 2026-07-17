package com.backend.medconsult.dto.patient;

import com.backend.medconsult.entity.usersAndPatients.PatientAllergy;
import com.backend.medconsult.enums.usersAndPatients.AllergyType;
import com.backend.medconsult.enums.usersAndPatients.Severity;
import java.time.LocalDateTime;
import java.util.UUID;

public class PatientAllergyResponseDto {

    private UUID allergyId;
    private UUID patientId;
    private String allergen;
    private AllergyType allergyType;
    private String reaction;
    private Severity severity;
    private Boolean confirmed;
    private UUID recordedById;
    private LocalDateTime createdAt;

    public UUID getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(UUID allergyId) {
        this.allergyId = allergyId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
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

    public UUID getRecordedById() {
        return recordedById;
    }

    public void setRecordedById(UUID recordedById) {
        this.recordedById = recordedById;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static PatientAllergyResponseDto fromEntity(PatientAllergy allergy) {
        if (allergy == null) {
            return null;
        }
        PatientAllergyResponseDto dto = new PatientAllergyResponseDto();
        dto.setAllergyId(allergy.getAllergyId());
        if (allergy.getPatient() != null) {
            dto.setPatientId(allergy.getPatient().getPatientId());
        }
        dto.setAllergen(allergy.getAllergen());
        dto.setAllergyType(allergy.getAllergyType());
        dto.setReaction(allergy.getReaction());
        dto.setSeverity(allergy.getSeverity());
        dto.setConfirmed(allergy.getConfirmed());
        if (allergy.getRecordedBy() != null) {
            dto.setRecordedById(allergy.getRecordedBy().getUserId());
        }
        dto.setCreatedAt(allergy.getCreatedAt());
        return dto;
    }
}
