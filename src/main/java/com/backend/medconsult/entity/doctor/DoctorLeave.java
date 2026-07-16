package com.backend.medconsult.entity.doctor;

import jakarta.persistence.*;

import com.backend.medconsult.enums.doctor.*;
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
    name = "doctor_leave",
    indexes = {
        @Index(name = "idx_leave_dc_dates", columnList = "dc_id, start_date, end_date")
    }
)
public class DoctorLeave {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "leave_id", nullable = false, updatable = false, length = 36)
    private UUID leaveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "dc_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_leave_doctor_clinic"))
    private DoctorClinic doctorClinic;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false, columnDefinition = "ENUM('annual','sick','conference','emergency','other')")
    private LeaveType leaveType = LeaveType.other;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    @Column(name = "notes", length = 255)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DoctorLeave() {
    }

    public UUID getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(UUID leaveId) {
        this.leaveId = leaveId;
    }

    public DoctorClinic getDoctorClinic() {
        return doctorClinic;
    }

    public void setDoctorClinic(DoctorClinic doctorClinic) {
        this.doctorClinic = doctorClinic;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean approved) {
        isApproved = approved;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
