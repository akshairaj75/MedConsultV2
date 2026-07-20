package com.backend.medconsult.dto.consultation;

import com.backend.medconsult.entity.consultation.Consultation;
import com.backend.medconsult.enums.consultation.ConsultationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConsultationResponseDto {

    private UUID consultationId;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
    private UUID appointmentId;
    private String subject;
    private ConsultationStatus status;
    private Boolean isUrgent;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private LocalDateTime lastMessageAt;
    private LocalDateTime createdAt;

    public ConsultationResponseDto() {
    }

    public static ConsultationResponseDto fromEntity(Consultation entity) {
        if (entity == null) {
            return null;
        }
        ConsultationResponseDto dto = new ConsultationResponseDto();
        dto.setConsultationId(entity.getConsultationId());
        
        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getPatientId());
            if (entity.getPatient().getUser() != null) {
                dto.setPatientName(entity.getPatient().getUser().getFullName());
            }
        }
        
        if (entity.getDoctor() != null) {
            dto.setDoctorId(entity.getDoctor().getDoctorId());
            if (entity.getDoctor().getUser() != null) {
                dto.setDoctorName(entity.getDoctor().getUser().getFullName());
            }
        }
        
        if (entity.getAppointment() != null) {
            dto.setAppointmentId(entity.getAppointment().getAppointmentId());
        }
        
        dto.setSubject(entity.getSubject());
        dto.setStatus(entity.getStatus());
        dto.setIsUrgent(entity.getIsUrgent());
        dto.setOpenedAt(entity.getOpenedAt());
        dto.setClosedAt(entity.getClosedAt());
        dto.setLastMessageAt(entity.getLastMessageAt());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
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

    public UUID getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(UUID appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public LocalDateTime getLastMessageAt() {
        return lastMessageAt;
    }

    public void setLastMessageAt(LocalDateTime lastMessageAt) {
        this.lastMessageAt = lastMessageAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
