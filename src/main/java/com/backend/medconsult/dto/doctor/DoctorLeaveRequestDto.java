package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.enums.doctor.LeaveType;
import java.time.LocalDate;
import java.util.UUID;

public class DoctorLeaveRequestDto {

    private UUID dcId;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isApproved;
    private String notes;

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
}
