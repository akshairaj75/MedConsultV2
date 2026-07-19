package com.backend.medconsult.dto.appointment;

import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.enums.appointments.AppointmentStatus;
import com.backend.medconsult.enums.appointments.AppointmentType;
import com.backend.medconsult.enums.doctor.SessionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentResponseDto {

    private String appointmentId;
    private UUID patientId;
    private String patientName;
    private UUID dcId;
    private UUID doctorId;
    private String doctorName;
    private UUID slotId;
    private UUID consultationId;
    private AppointmentType appointmentType;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private Short durationMinutes;
    private SessionType sessionType;
    private AppointmentStatus status;
    private String reason;
    private Boolean reminder24hSent;
    private Boolean reminder2hSent;
    private UUID cancelledById;
    private String cancelReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ─── Getters & Setters ────────────────────────────────────────────

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public UUID getDcId() {
        return dcId;
    }

    public void setDcId(UUID dcId) {
        this.dcId = dcId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public UUID getSlotId() {
        return slotId;
    }

    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
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

    public UUID getCancelledById() {
        return cancelledById;
    }

    public void setCancelledById(UUID cancelledById) {
        this.cancelledById = cancelledById;
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

    // ─── Static Factory ────────────────────────────────────────────────

    public static AppointmentResponseDto fromEntity(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setAppointmentId(appointment.getAppointmentId());

        if (appointment.getPatient() != null) {
            dto.setPatientId(appointment.getPatient().getPatientId());
            if (appointment.getPatient().getUser() != null) {
                dto.setPatientName(appointment.getPatient().getUser().getFullName());
            }
        }

        if (appointment.getDoctorClinic() != null) {
            dto.setDcId(appointment.getDoctorClinic().getDcId());
            if (appointment.getDoctorClinic().getDoctor() != null) {
                dto.setDoctorId(appointment.getDoctorClinic().getDoctor().getDoctorId());
                if (appointment.getDoctorClinic().getDoctor().getUser() != null) {
                    dto.setDoctorName(appointment.getDoctorClinic().getDoctor().getUser().getFullName());
                }
            }
        }

        if (appointment.getSlot() != null) {
            dto.setSlotId(appointment.getSlot().getSlotId());
        }

        if (appointment.getConsultation() != null) {
            dto.setConsultationId(appointment.getConsultation().getConsultationId());
        }

        dto.setAppointmentType(appointment.getAppointmentType());
        dto.setScheduledDate(appointment.getScheduledDate());
        dto.setStartTime(appointment.getStartTime());
        dto.setDurationMinutes(appointment.getDurationMinutes());
        dto.setSessionType(appointment.getSessionType());
        dto.setStatus(appointment.getStatus());
        dto.setReason(appointment.getReason());
        dto.setReminder24hSent(appointment.getReminder24hSent());
        dto.setReminder2hSent(appointment.getReminder2hSent());

        if (appointment.getCancelledBy() != null) {
            dto.setCancelledById(appointment.getCancelledBy().getUserId());
        }

        dto.setCancelReason(appointment.getCancelReason());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setUpdatedAt(appointment.getUpdatedAt());

        return dto;
    }
}
