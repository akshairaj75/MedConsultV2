package com.backend.medconsult.dto.clinic;

import com.backend.medconsult.entity.clinic.ClinicOperatingHour;
import java.time.LocalTime;
import java.util.UUID;

public class ClinicOperatingHourResponseDto {

    private UUID hoursId;
    private UUID branchId;
    private Byte dayOfWeek;
    private Boolean isClosed;
    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalTime breakStart;
    private LocalTime breakEnd;
    private String notes;

    public UUID getHoursId() {
        return hoursId;
    }

    public void setHoursId(UUID hoursId) {
        this.hoursId = hoursId;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public Byte getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Byte dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean closed) {
        isClosed = closed;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public LocalTime getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(LocalTime breakStart) {
        this.breakStart = breakStart;
    }

    public LocalTime getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(LocalTime breakEnd) {
        this.breakEnd = breakEnd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static ClinicOperatingHourResponseDto fromEntity(ClinicOperatingHour hour) {
        if (hour == null) {
            return null;
        }
        ClinicOperatingHourResponseDto dto = new ClinicOperatingHourResponseDto();
        dto.setHoursId(hour.getHoursId());
        if (hour.getBranch() != null) {
            dto.setBranchId(hour.getBranch().getBranchId());
        }
        dto.setDayOfWeek(hour.getDayOfWeek());
        dto.setIsClosed(hour.getIsClosed());
        dto.setOpenTime(hour.getOpenTime());
        dto.setCloseTime(hour.getCloseTime());
        dto.setBreakStart(hour.getBreakStart());
        dto.setBreakEnd(hour.getBreakEnd());
        dto.setNotes(hour.getNotes());
        return dto;
    }
}
