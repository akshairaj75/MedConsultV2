package com.backend.medconsult.dto.references;

import com.backend.medconsult.entity.references.SubSpecialty;
import java.time.LocalDateTime;
import java.util.UUID;

public class SubSpecialtyResponseDto {

    private UUID subSpecialtyId;
    private UUID specialtyId;
    private String nameEn;
    private String nameAr;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public UUID getSubSpecialtyId() {
        return subSpecialtyId;
    }

    public void setSubSpecialtyId(UUID subSpecialtyId) {
        this.subSpecialtyId = subSpecialtyId;
    }

    public UUID getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(UUID specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static SubSpecialtyResponseDto fromEntity(SubSpecialty subSpecialty) {
        if (subSpecialty == null) {
            return null;
        }
        SubSpecialtyResponseDto dto = new SubSpecialtyResponseDto();
        dto.setSubSpecialtyId(subSpecialty.getSubSpecialtyId());
        if (subSpecialty.getSpecialty() != null) {
            dto.setSpecialtyId(subSpecialty.getSpecialty().getSpecialtyId());
        }
        dto.setNameEn(subSpecialty.getNameEn());
        dto.setNameAr(subSpecialty.getNameAr());
        dto.setIsActive(subSpecialty.getIsActive());
        dto.setCreatedAt(subSpecialty.getCreatedAt());
        return dto;
    }
}
