package com.backend.medconsult.entity.doctor;

import jakarta.persistence.*;

import com.backend.medconsult.enums.doctor.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "doctor_schedule",
    indexes = {
        @Index(name = "idx_sched_dc", columnList = "dc_id, day_of_week, is_active"),
        @Index(name = "idx_sched_timing", columnList = "day_of_week, start_time")
    }
)
public class DoctorSchedule {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "schedule_id", nullable = false, updatable = false, length = 36)
    private UUID scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dc_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_schedule_doctor_clinic"))
    private DoctorClinic doctorClinic;

    @Column(name = "day_of_week", nullable = false)
    private Byte dayOfWeek; // 0=Sunday ... 6=Saturday

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "slot_duration_min", nullable = false)
    private Short slotDurationMin = 30;

    @Column(name = "max_patients")
    private Short maxPatients;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false)
    private SessionType sessionType = SessionType.IN_CLINIC;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    public DoctorSchedule() {
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public DoctorClinic getDoctorClinic() {
        return doctorClinic;
    }

    public void setDoctorClinic(DoctorClinic doctorClinic) {
        this.doctorClinic = doctorClinic;
    }

    public Byte getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Byte dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Short getSlotDurationMin() {
        return slotDurationMin;
    }

    public void setSlotDurationMin(Short slotDurationMin) {
        this.slotDurationMin = slotDurationMin;
    }

    public Short getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(Short maxPatients) {
        this.maxPatients = maxPatients;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }
}
