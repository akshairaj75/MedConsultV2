package com.backend.medconsult.dto.patient;

import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.enums.usersAndPatients.BloodType;
import com.backend.medconsult.enums.usersAndPatients.MaritalStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PatientResponseDto {

    private UUID patientId;
    private UUID userId;
    private LocalDate dateOfBirth;
    private BloodType bloodType;
    private String nationalId;
    private String nationality;
    private MaritalStatus maritalStatus;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
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

    public static PatientResponseDto fromEntity(Patient patient) {
        if (patient == null) {
            return null;
        }
        PatientResponseDto dto = new PatientResponseDto();
        dto.setPatientId(patient.getPatientId());
        if (patient.getUser() != null) {
            dto.setUserId(patient.getUser().getUserId());
        }
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setBloodType(patient.getBloodType());
        dto.setNationalId(patient.getNationalId());
        dto.setNationality(patient.getNationality());
        dto.setMaritalStatus(patient.getMaritalStatus());
        dto.setEmergencyContactName(patient.getEmergencyContactName());
        dto.setEmergencyContactPhone(patient.getEmergencyContactPhone());
        dto.setNotes(patient.getNotes());
        dto.setCreatedAt(patient.getCreatedAt());
        dto.setUpdatedAt(patient.getUpdatedAt());
        return dto;
    }
}
