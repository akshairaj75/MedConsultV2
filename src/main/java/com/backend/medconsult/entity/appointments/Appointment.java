package com.backend.medconsult.entity.appointments;

import jakarta.persistence.*;

import com.backend.medconsult.entity.consultation.Consultation;
import com.backend.medconsult.entity.doctor.AppointmentSlot;
import com.backend.medconsult.entity.doctor.DoctorClinic;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.appointments.*;
import com.backend.medconsult.enums.doctor.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
    name = "appointments",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_appt_slot", columnNames = {"slot_id"})
    },
    indexes = {
        @Index(name = "idx_appt_patient", columnList = "patient_id, scheduled_date DESC"),
        @Index(name = "idx_appt_dc_date", columnList = "dc_id, scheduled_date, status"),
        @Index(name = "idx_appt_status", columnList = "status, scheduled_date"),
        @Index(name = "idx_appt_consult", columnList = "consultation_id")
    }
)
public class Appointment {

    @Id
    @Column(name = "appointment_id", length = 36, columnDefinition = "CHAR(36)")
    private String appointmentId = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointments_patient"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dc_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointments_dc"))
    private DoctorClinic doctorClinic;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false, foreignKey = @ForeignKey(name = "fk_appointments_slot"))
    private AppointmentSlot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", foreignKey = @ForeignKey(name = "fk_appointments_consultation"))
    private Consultation consultation;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false, columnDefinition = "ENUM('follow_up','new_patient','referral','emergency')")
    private AppointmentType appointmentType = AppointmentType.new_patient;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "duration_minutes", nullable = false)
    private Short durationMinutes = 30;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false, columnDefinition = "ENUM('in_clinic','virtual')")
    private SessionType sessionType = SessionType.in_clinic;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('scheduled','confirmed','completed','cancelled','no_show')")
    private AppointmentStatus status = AppointmentStatus.scheduled;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    @Column(name = "reminder_24h_sent", nullable = false)
    private Boolean reminder24hSent = false;

    @Column(name = "reminder_2h_sent", nullable = false)
    private Boolean reminder2hSent = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by", foreignKey = @ForeignKey(name = "fk_appointments_canceller"))
    private User cancelledBy;

    @Column(name = "cancel_reason", length = 255)
    private String cancelReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Appointment() {
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DoctorClinic getDoctorClinic() {
        return doctorClinic;
    }

    public void setDoctorClinic(DoctorClinic doctorClinic) {
        this.doctorClinic = doctorClinic;
    }

    public AppointmentSlot getSlot() {
        return slot;
    }

    public void setSlot(AppointmentSlot slot) {
        this.slot = slot;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Short getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Short durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getReminder24hSent() {
        return reminder24hSent;
    }

    public void setReminder24hSent(Boolean reminder24hSent) {
        this.reminder24hSent = reminder24hSent;
    }

    public Boolean getReminder2hSent() {
        return reminder2hSent;
    }

    public void setReminder2hSent(Boolean reminder2hSent) {
        this.reminder2hSent = reminder2hSent;
    }

    public User getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(User cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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
}
