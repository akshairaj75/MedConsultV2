package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.entity.clinicalRecords.Vital;
import com.backend.medconsult.enums.clinicalRecords.VitalSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class VitalResponseDto {

    private UUID vitalId;
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

    public UUID getVitalId() {
        return vitalId;
    }

    public void setVitalId(UUID vitalId) {
        this.vitalId = vitalId;
    }

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

    public static VitalResponseDto fromEntity(Vital vital) {
        if (vital == null) {
            return null;
        }
        VitalResponseDto dto = new VitalResponseDto();
        dto.setVitalId(vital.getVitalId());
        if (vital.getPatient() != null) {
            dto.setPatientId(vital.getPatient().getPatientId());
        }
        if (vital.getRecordedBy() != null) {
            dto.setRecordedById(vital.getRecordedBy().getUserId());
        }
        dto.setRecordedAt(vital.getRecordedAt());
        dto.setSource(vital.getSource());
        dto.setBloodPressureSystolic(vital.getBloodPressureSystolic());
        dto.setBloodPressureDiastolic(vital.getBloodPressureDiastolic());
        dto.setHeartRateBpm(vital.getHeartRateBpm());
        dto.setBloodGlucoseMmol(vital.getBloodGlucoseMmol());
        dto.setHba1cPercent(vital.getHba1cPercent());
        dto.setWeightKg(vital.getWeightKg());
        dto.setTemperatureC(vital.getTemperatureC());
        dto.setOxygenSaturation(vital.getOxygenSaturation());
        dto.setNotes(vital.getNotes());
        return dto;
    }
}
