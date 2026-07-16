package com.backend.medconsult.entity.clinicalRecords;

import jakarta.persistence.*;

import com.backend.medconsult.entity.usersAndPatients.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "medication_adherence",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_adhere_item_date", columnNames = {"rx_item_id", "log_date"})
    },
    indexes = {
        @Index(name = "idx_adhere_patient", columnList = "patient_id, log_date DESC")
    }
)
public class MedicationAdherence {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "log_id", nullable = false, updatable = false, length = 36)
    private UUID logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "rx_item_id", nullable = false, foreignKey = @ForeignKey(name = "fk_medication_adherence_item"))
    private PrescriptionItem rxItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_medication_adherence_patient"))
    private Patient patient;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "taken", nullable = false)
    private Boolean taken = false;

    @Column(name = "taken_at")
    private LocalDateTime takenAt;

    @Column(name = "skipped_reason", length = 255)
    private String skippedReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public MedicationAdherence() {
    }

    public UUID getLogId() {
        return logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public PrescriptionItem getRxItem() {
        return rxItem;
    }

    public void setRxItem(PrescriptionItem rxItem) {
        this.rxItem = rxItem;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
}
