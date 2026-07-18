package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.enums.doctor.SessionType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class DoctorScheduleRequestDto {

    private UUID dcId;
    private Byte dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Short slotDurationMin;
    private Short maxPatients;
    private SessionType sessionType;
    private Boolean isActive;
    private LocalDate validFrom;
    private LocalDate validUntil;

    public UUID getDcId() {
        return dcId;
    }

    public void setDcId(UUID dcId) {
        this.dcId = dcId;
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
