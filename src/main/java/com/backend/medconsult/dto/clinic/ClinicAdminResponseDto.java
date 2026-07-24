package com.backend.medconsult.dto.clinic;

import com.backend.medconsult.entity.clinic.ClinicAdmin;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClinicAdminResponseDto {

    private UUID id;
    private UUID clinicId;
    private UUID userId;
    private Boolean isPrimary;
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClinicId() {
        return clinicId;
    }

    public void setClinicId(UUID clinicId) {
        this.clinicId = clinicId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static ClinicAdminResponseDto fromEntity(ClinicAdmin entity) {
        if (entity == null) {
            return null;
        }
        ClinicAdminResponseDto dto = new ClinicAdminResponseDto();
        dto.setId(entity.getId());
        if (entity.getClinic() != null) {
            dto.setClinicId(entity.getClinic().getClinicId());
        }
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getUserId());
        }
        dto.setIsPrimary(entity.getIsPrimary());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
