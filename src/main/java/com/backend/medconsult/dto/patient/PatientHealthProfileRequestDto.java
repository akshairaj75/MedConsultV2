package com.backend.medconsult.dto.patient;

import com.backend.medconsult.enums.usersAndPatients.AlcoholStatus;
import com.backend.medconsult.enums.usersAndPatients.SmokingStatus;
import java.math.BigDecimal;
import java.util.UUID;

public class PatientHealthProfileRequestDto {

    private UUID patientId;
    private BigDecimal weightKg;
    private BigDecimal heightCm;
    private BigDecimal bmi;
    private SmokingStatus smokingStatus;
    private AlcoholStatus alcoholStatus;
    private String surgicalHistory;
    private String familyHistory;
    private String additionalNotes;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public BigDecimal getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(BigDecimal heightCm) {
        this.heightCm = heightCm;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public SmokingStatus getSmokingStatus() {
        return smokingStatus;
    }

    public void setSmokingStatus(SmokingStatus smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public AlcoholStatus getAlcoholStatus() {
        return alcoholStatus;
    }

    public void setAlcoholStatus(AlcoholStatus alcoholStatus) {
        this.alcoholStatus = alcoholStatus;
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public void setSurgicalHistory(String surgicalHistory) {
        this.surgicalHistory = surgicalHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
}
