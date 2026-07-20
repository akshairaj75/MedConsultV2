package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoom;
import com.backend.medconsult.enums.caseRoom.CasePriority;
import com.backend.medconsult.enums.caseRoom.CaseRoomStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class CaseRoomResponseDto {

    private UUID caseRoomId;
    private UUID patientId;
    private String patientName;
    private UUID openedById;
    private String openedByName;
    private String title;
    private String description;
    private CaseRoomStatus status;
    private CasePriority priority;
    private LocalDateTime closedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CaseRoomResponseDto() {
    }

    public static CaseRoomResponseDto fromEntity(CaseRoom entity) {
        if (entity == null) {
            return null;
        }
        CaseRoomResponseDto dto = new CaseRoomResponseDto();
        dto.setCaseRoomId(entity.getCaseRoomId());

        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getPatientId());
            if (entity.getPatient().getUser() != null) {
                dto.setPatientName(entity.getPatient().getUser().getFullName());
            }
        }

        if (entity.getOpenedBy() != null) {
            dto.setOpenedById(entity.getOpenedBy().getDoctorId());
            if (entity.getOpenedBy().getUser() != null) {
                dto.setOpenedByName(entity.getOpenedBy().getUser().getFullName());
            }
        }

        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setClosedAt(entity.getClosedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public UUID getCaseRoomId() {
        return caseRoomId;
    }

    public void setCaseRoomId(UUID caseRoomId) {
        this.caseRoomId = caseRoomId;
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

    public UUID getOpenedById() {
        return openedById;
    }

    public void setOpenedById(UUID openedById) {
        this.openedById = openedById;
    }

    public String getOpenedByName() {
        return openedByName;
    }

    public void setOpenedByName(String openedByName) {
        this.openedByName = openedByName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CaseRoomStatus getStatus() {
        return status;
    }

    public void setStatus(CaseRoomStatus status) {
        this.status = status;
    }

    public CasePriority getPriority() {
        return priority;
    }

    public void setPriority(CasePriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
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
}
