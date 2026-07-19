package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.entity.doctor.AppointmentSlot;
import com.backend.medconsult.enums.doctor.SessionType;
import com.backend.medconsult.enums.doctor.SlotStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentSlotResponseDto {

    private UUID slotId;
    private UUID dcId;
    private UUID scheduleId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private SessionType sessionType;
    private SlotStatus status;
    private UUID appointmentId;
    private LocalDateTime createdAt;

    public UUID getSlotId() {
        return slotId;
    }

    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static AppointmentSlotResponseDto fromEntity(AppointmentSlot slot) {
        if (slot == null) {
            return null;
        }
        AppointmentSlotResponseDto dto = new AppointmentSlotResponseDto();
        dto.setSlotId(slot.getSlotId());
        if (slot.getDoctorClinic() != null) {
            dto.setDcId(slot.getDoctorClinic().getDcId());
        }
        if (slot.getSchedule() != null) {
            dto.setScheduleId(slot.getSchedule().getScheduleId());
        }
        dto.setSlotDate(slot.getSlotDate());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        dto.setSessionType(slot.getSessionType());
        dto.setStatus(slot.getStatus());
        if (slot.getAppointment() != null) {
            dto.setAppointmentId(slot.getAppointment().getAppointmentId());
        }
        dto.setCreatedAt(slot.getCreatedAt());
        return dto;
    }
}
