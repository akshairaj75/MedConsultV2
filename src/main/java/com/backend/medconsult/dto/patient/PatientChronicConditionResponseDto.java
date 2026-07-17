package com.backend.medconsult.dto.patient;

import com.backend.medconsult.entity.usersAndPatients.PatientChronicCondition;
import com.backend.medconsult.enums.usersAndPatients.ConditionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PatientChronicConditionResponseDto {

    private UUID conditionId;
    private UUID patientId;
    private String icd10Code;
    private String conditionName;
    private LocalDate diagnosisDate;
    private ConditionStatus status;
    private UUID managingDoctorId;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getConditionId() {
        return conditionId;
    }

    public void setConditionId(UUID conditionId) {
        this.conditionId = conditionId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDate diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public ConditionStatus getStatus() {
        return status;
    }

    public void setStatus(ConditionStatus status) {
        this.status = status;
    }

    public UUID getManagingDoctorId() {
        return managingDoctorId;
    }

    public void setManagingDoctorId(UUID managingDoctorId) {
        this.managingDoctorId = managingDoctorId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public static PatientChronicConditionResponseDto fromEntity(PatientChronicCondition condition) {
        if (condition == null) {
            return null;
        }
        PatientChronicConditionResponseDto dto = new PatientChronicConditionResponseDto();
        dto.setConditionId(condition.getConditionId());
        if (condition.getPatient() != null) {
            dto.setPatientId(condition.getPatient().getPatientId());
        }
        dto.setIcd10Code(condition.getIcd10Code());
        dto.setConditionName(condition.getConditionName());
        dto.setDiagnosisDate(condition.getDiagnosisDate());
        dto.setStatus(condition.getStatus());
        if (condition.getManagingDoctor() != null) {
            dto.setManagingDoctorId(condition.getManagingDoctor().getDoctorId());
        }
        dto.setNotes(condition.getNotes());
        dto.setCreatedAt(condition.getCreatedAt());
        dto.setUpdatedAt(condition.getUpdatedAt());
        return dto;
    }
}
