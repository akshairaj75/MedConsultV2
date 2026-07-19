package com.backend.medconsult.dto.appointment;

import com.backend.medconsult.enums.appointments.AppointmentType;
import com.backend.medconsult.enums.doctor.SessionType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class AppointmentRequestDto {

    @NotNull(message = "Patient ID is required")
    private UUID patientId;

    @NotNull(message = "Doctor-Clinic ID is required")
    private UUID dcId;

    @NotNull(message = "Slot ID is required")
    private UUID slotId;

    @NotNull(message = "Appointment type is required")
    private AppointmentType appointmentType;

    @NotNull(message = "Scheduled date is required")
    @FutureOrPresent(message = "Scheduled date must be today or in the future")
    private LocalDate scheduledDate;

    @NotNull(message = "Session type is required")
    private SessionType sessionType;

    private String reason;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDcId() {
        return dcId;
    }

    public void setDcId(UUID dcId) {
        this.dcId = dcId;
    }

    public UUID getSlotId() {
        return slotId;
    }

    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
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

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
