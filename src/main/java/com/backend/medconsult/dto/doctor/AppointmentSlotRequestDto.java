package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.enums.doctor.SessionType;
import com.backend.medconsult.enums.doctor.SlotStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentSlotRequestDto {

    private UUID dcId;
    private UUID scheduleId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private SessionType sessionType;
    private SlotStatus status;
    private UUID appointmentId;

    public UUID getDcId() {
        return dcId;
    }

    public void setDcId(UUID dcId) {
        this.dcId = dcId;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
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

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }
}
