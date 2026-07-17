package com.backend.medconsult.dto.patient;

import com.backend.medconsult.enums.usersAndPatients.ConditionStatus;
import java.time.LocalDate;
import java.util.UUID;

public class PatientChronicConditionRequestDto {

    private UUID patientId;
    private String icd10Code;
    private String conditionName;
    private LocalDate diagnosisDate;
    private ConditionStatus status;
    private UUID managingDoctorId;
    private String notes;

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
}
