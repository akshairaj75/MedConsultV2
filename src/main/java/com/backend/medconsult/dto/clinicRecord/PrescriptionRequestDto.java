package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.enums.clinicalRecords.PrescriptionStatus;
import java.time.LocalDate;
import java.util.UUID;

public class PrescriptionRequestDto {

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
}
