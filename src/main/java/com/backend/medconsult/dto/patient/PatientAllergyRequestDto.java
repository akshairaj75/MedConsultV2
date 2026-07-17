package com.backend.medconsult.dto.patient;

import com.backend.medconsult.enums.usersAndPatients.AllergyType;
import com.backend.medconsult.enums.usersAndPatients.Severity;
import java.util.UUID;

public class PatientAllergyRequestDto {

    private UUID patientId;
    private String allergen;
    private AllergyType allergyType;
    private String reaction;
    private Severity severity;
    private Boolean confirmed;
    private UUID recordedById;

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
}
