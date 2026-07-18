package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.entity.doctor.DoctorLeave;
import com.backend.medconsult.enums.doctor.LeaveType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DoctorLeaveResponseDto {

    private UUID leaveId;
    private UUID dcId;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isApproved;
    private String notes;
    private LocalDateTime createdAt;

    public UUID getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(UUID leaveId) {
        this.leaveId = leaveId;
    }

    public UUID getDcId() {
        return dcId;
    }

    public void setDcId(UUID dcId) {
        this.dcId = dcId;
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

    public static DoctorLeaveResponseDto fromEntity(DoctorLeave leave) {
        if (leave == null) {
            return null;
        }
        DoctorLeaveResponseDto dto = new DoctorLeaveResponseDto();
        dto.setLeaveId(leave.getLeaveId());
        if (leave.getDoctorClinic() != null) {
            dto.setDcId(leave.getDoctorClinic().getDcId());
        }
        dto.setLeaveType(leave.getLeaveType());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setIsApproved(leave.getIsApproved());
        dto.setNotes(leave.getNotes());
        dto.setCreatedAt(leave.getCreatedAt());
        return dto;
    }
}
