package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.entity.clinicalRecords.MedicationAdherence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MedicationAdherenceResponseDto {

    private UUID logId;
    private UUID rxItemId;
    private UUID patientId;
    private LocalDate logDate;
    private Boolean taken;
    private LocalDateTime takenAt;
    private String skippedReason;
    private LocalDateTime createdAt;

    public UUID getLogId() {
        return logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public UUID getRxItemId() {
        return rxItemId;
    }

    public void setRxItemId(UUID rxItemId) {
        this.rxItemId = rxItemId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    public String getSkippedReason() {
        return skippedReason;
    }

    public void setSkippedReason(String skippedReason) {
        this.skippedReason = skippedReason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static MedicationAdherenceResponseDto fromEntity(MedicationAdherence adherence) {
        if (adherence == null) {
            return null;
        }
        MedicationAdherenceResponseDto dto = new MedicationAdherenceResponseDto();
        dto.setLogId(adherence.getLogId());
        if (adherence.getRxItem() != null) {
            dto.setRxItemId(adherence.getRxItem().getItemId());
        }
        if (adherence.getPatient() != null) {
            dto.setPatientId(adherence.getPatient().getPatientId());
        }
        dto.setLogDate(adherence.getLogDate());
        dto.setTaken(adherence.getTaken());
        dto.setTakenAt(adherence.getTakenAt());
        dto.setSkippedReason(adherence.getSkippedReason());
        dto.setCreatedAt(adherence.getCreatedAt());
        return dto;
    }
}
