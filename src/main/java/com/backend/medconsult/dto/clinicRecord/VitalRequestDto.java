package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.enums.clinicalRecords.VitalSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class VitalRequestDto {

    private UUID patientId;
    private UUID recordedById;
    private LocalDateTime recordedAt;
    private VitalSource source;
    private Short bloodPressureSystolic;
    private Short bloodPressureDiastolic;
    private Short heartRateBpm;
    private BigDecimal bloodGlucoseMmol;
    private BigDecimal hba1cPercent;
    private BigDecimal weightKg;
    private BigDecimal temperatureC;
    private BigDecimal oxygenSaturation;
    private String notes;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getRecordedById() {
        return recordedById;
    }

    public void setRecordedById(UUID recordedById) {
        this.recordedById = recordedById;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public VitalSource getSource() {
        return source;
    }

    public void setSource(VitalSource source) {
        this.source = source;
    }

    public Short getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(Short bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public Short getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(Short bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }

    public Short getHeartRateBpm() {
        return heartRateBpm;
    }

    public void setHeartRateBpm(Short heartRateBpm) {
        this.heartRateBpm = heartRateBpm;
    }

    public BigDecimal getBloodGlucoseMmol() {
        return bloodGlucoseMmol;
    }

    public void setBloodGlucoseMmol(BigDecimal bloodGlucoseMmol) {
        this.bloodGlucoseMmol = bloodGlucoseMmol;
    }

    public BigDecimal getHba1cPercent() {
        return hba1cPercent;
    }

    public void setHba1cPercent(BigDecimal hba1cPercent) {
        this.hba1cPercent = hba1cPercent;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public BigDecimal getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(BigDecimal temperatureC) {
        this.temperatureC = temperatureC;
    }

    public BigDecimal getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(BigDecimal oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
