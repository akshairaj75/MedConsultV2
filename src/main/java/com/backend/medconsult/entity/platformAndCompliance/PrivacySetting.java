package com.backend.medconsult.entity.platformAndCompliance;

import jakarta.persistence.*;

import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.enums.platformAndCompliance.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "privacy_settings",
    indexes = {
        @Index(name = "idx_priv_patient", columnList = "patient_id, permission"),
        @Index(name = "idx_priv_doctor", columnList = "doctor_id, patient_id")
    }
)
public class PrivacySetting {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "setting_id", nullable = false, updatable = false, length = 36)
    private UUID settingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_privacy_settings_patient"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", foreignKey = @ForeignKey(name = "fk_privacy_settings_doctor"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", foreignKey = @ForeignKey(name = "fk_privacy_settings_clinic"))
    private Clinic clinic;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_scope", nullable = false, columnDefinition = "ENUM('full_record','lab_results','prescriptions','vitals','consultations','allergies')")
    private DataScope dataScope = DataScope.full_record;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false, columnDefinition = "ENUM('allow','deny')")
    private PrivacyPermission permission = PrivacyPermission.allow;

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt = LocalDateTime.now();

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public PrivacySetting() {
    }

    public UUID getSettingId() {
        return settingId;
    }

    public void setSettingId(UUID settingId) {
        this.settingId = settingId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public DataScope getDataScope() {
        return dataScope;
    }

    public void setDataScope(DataScope dataScope) {
        this.dataScope = dataScope;
    }

    public PrivacyPermission getPermission() {
        return permission;
    }

    public void setPermission(PrivacyPermission permission) {
        this.permission = permission;
    }

    public LocalDateTime getGrantedAt() {
        return grantedAt;
    }

    public void setGrantedAt(LocalDateTime grantedAt) {
        this.grantedAt = grantedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
