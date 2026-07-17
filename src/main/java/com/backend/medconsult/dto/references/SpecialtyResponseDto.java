package com.backend.medconsult.dto.references;

import com.backend.medconsult.entity.references.Specialty;
import java.time.LocalDateTime;
import java.util.UUID;

public class SpecialtyResponseDto {

    private UUID specialtyId;
    private String code;
    private String nameEn;
    private String nameAr;
    private String category;
    private String iconSlug;
    private Boolean isActive;
    private Short sortOrder;
    private LocalDateTime createdAt;

    public UUID getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(UUID specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIconSlug() {
        return iconSlug;
    }

    public void setIconSlug(String iconSlug) {
        this.iconSlug = iconSlug;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Short getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Short sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static SpecialtyResponseDto fromEntity(Specialty specialty) {
        if (specialty == null) {
            return null;
        }
        SpecialtyResponseDto dto = new SpecialtyResponseDto();
        dto.setSpecialtyId(specialty.getSpecialtyId());
        dto.setCode(specialty.getCode());
        dto.setNameEn(specialty.getNameEn());
        dto.setNameAr(specialty.getNameAr());
        dto.setCategory(specialty.getCategory());
        dto.setIconSlug(specialty.getIconSlug());
        dto.setIsActive(specialty.getIsActive());
        dto.setSortOrder(specialty.getSortOrder());
        dto.setCreatedAt(specialty.getCreatedAt());
        return dto;
    }
}
