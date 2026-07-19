package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.entity.clinicalRecords.Prescription;
import com.backend.medconsult.enums.clinicalRecords.PrescriptionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PrescriptionResponseDto {

    private UUID prescriptionId;
    private UUID patientId;
    private UUID doctorId;
    private UUID consultationId;
    private UUID appointmentId;
    private LocalDate issuedDate;
    private LocalDate validUntil;
    private PrescriptionStatus status;
    private String diagnosisNotes;
    private String pharmacistNotes;
    private String naphiesReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public String getDiagnosisNotes() {
        return diagnosisNotes;
    }

    public void setDiagnosisNotes(String diagnosisNotes) {
        this.diagnosisNotes = diagnosisNotes;
    }

    public String getPharmacistNotes() {
        return pharmacistNotes;
    }

    public void setPharmacistNotes(String pharmacistNotes) {
        this.pharmacistNotes = pharmacistNotes;
    }

    public String getNaphiesReference() {
        return naphiesReference;
    }

    public void setNaphiesReference(String naphiesReference) {
        this.naphiesReference = naphiesReference;
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

    public static PrescriptionResponseDto fromEntity(Prescription rx) {
        if (rx == null) {
            return null;
        }
        PrescriptionResponseDto dto = new PrescriptionResponseDto();
        dto.setPrescriptionId(rx.getPrescriptionId());
        if (rx.getPatient() != null) {
            dto.setPatientId(rx.getPatient().getPatientId());
        }
        if (rx.getDoctor() != null) {
            dto.setDoctorId(rx.getDoctor().getDoctorId());
        }
        if (rx.getConsultation() != null) {
            dto.setConsultationId(rx.getConsultation().getConsultationId());
        }
        if (rx.getAppointment() != null) {
            dto.setAppointmentId(rx.getAppointment().getAppointmentId());
        }
        dto.setIssuedDate(rx.getIssuedDate());
        dto.setValidUntil(rx.getValidUntil());
        dto.setStatus(rx.getStatus());
        dto.setDiagnosisNotes(rx.getDiagnosisNotes());
        dto.setPharmacistNotes(rx.getPharmacistNotes());
        dto.setNaphiesReference(rx.getNaphiesReference());
        dto.setCreatedAt(rx.getCreatedAt());
        dto.setUpdatedAt(rx.getUpdatedAt());
        return dto;
    }
}
