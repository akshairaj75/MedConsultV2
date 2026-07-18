package com.backend.medconsult.dto.clinic;

import com.backend.medconsult.entity.clinic.ClinicSpecialty;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClinicSpecialtyResponseDto {

    private UUID id;
    private UUID clinicId;
    private UUID specialtyId;
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

    public UUID getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(UUID specialtyId) {
        this.specialtyId = specialtyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static ClinicSpecialtyResponseDto fromEntity(ClinicSpecialty specialty) {
        if (specialty == null) {
            return null;
        }
        ClinicSpecialtyResponseDto dto = new ClinicSpecialtyResponseDto();
        dto.setId(specialty.getId());
        if (specialty.getClinic() != null) {
            dto.setClinicId(specialty.getClinic().getClinicId());
        }
        if (specialty.getSpecialty() != null) {
            dto.setSpecialtyId(specialty.getSpecialty().getSpecialtyId());
        }
        dto.setCreatedAt(specialty.getCreatedAt());
        return dto;
    }
}
