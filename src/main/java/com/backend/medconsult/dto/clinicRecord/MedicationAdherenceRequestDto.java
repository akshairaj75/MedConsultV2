package com.backend.medconsult.dto.clinicRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MedicationAdherenceRequestDto {

    private UUID rxItemId;
    private UUID patientId;
    private LocalDate logDate;
    private Boolean taken;
    private LocalDateTime takenAt;
    private String skippedReason;

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
}
