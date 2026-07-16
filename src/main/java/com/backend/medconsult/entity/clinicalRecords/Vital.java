package com.backend.medconsult.entity.clinicalRecords;

import jakarta.persistence.*;

import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.clinicalRecords.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "vitals",
    indexes = {
        @Index(name = "idx_vitals_patient", columnList = "patient_id, recorded_at DESC"),
        @Index(name = "idx_vitals_glucose", columnList = "patient_id, blood_glucose_mmol, recorded_at")
    }
)
public class Vital {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "vital_id", nullable = false, updatable = false, length = 36)
    private UUID vitalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_vitals_patient"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "recorded_by", foreignKey = @ForeignKey(name = "fk_vitals_recorder"))
    private User recordedBy;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private VitalSource source = VitalSource.PATIENT_APP;

    @Column(name = "blood_pressure_systolic")
    private Short bloodPressureSystolic;

    @Column(name = "blood_pressure_diastolic")
    private Short bloodPressureDiastolic;

    @Column(name = "heart_rate_bpm")
    private Short heartRateBpm;

    @Column(name = "blood_glucose_mmol", precision = 5, scale = 2)
    private BigDecimal bloodGlucoseMmol;

    @Column(name = "hba1c_percent", precision = 4, scale = 2)
    private BigDecimal hba1cPercent;

    @Column(name = "weight_kg", precision = 5, scale = 2)
    private BigDecimal weightKg;

    @Column(name = "temperature_c", precision = 4, scale = 1)
    private BigDecimal temperatureC;

    @Column(name = "oxygen_saturation", precision = 4, scale = 1)
    private BigDecimal oxygenSaturation;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public Vital() {
    }

    public UUID getVitalId() {
        return vitalId;
    }

    public void setVitalId(UUID vitalId) {
        this.vitalId = vitalId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(User recordedBy) {
        this.recordedBy = recordedBy;
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
