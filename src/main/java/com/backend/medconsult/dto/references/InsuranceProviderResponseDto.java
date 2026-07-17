package com.backend.medconsult.dto.references;

import com.backend.medconsult.entity.references.InsuranceProvider;
import java.time.LocalDateTime;
import java.util.UUID;

public class InsuranceProviderResponseDto {

    private UUID providerId;
    private String nameEn;
    private String nameAr;
    private String logoUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public UUID getProviderId() {
        return providerId;
    }

    public void setProviderId(UUID providerId) {
        this.providerId = providerId;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    public static InsuranceProviderResponseDto fromEntity(InsuranceProvider provider) {
        if (provider == null) {
            return null;
        }
        InsuranceProviderResponseDto dto = new InsuranceProviderResponseDto();
        dto.setProviderId(provider.getProviderId());
        dto.setNameEn(provider.getNameEn());
        dto.setNameAr(provider.getNameAr());
        dto.setLogoUrl(provider.getLogoUrl());
        dto.setIsActive(provider.getIsActive());
        dto.setCreatedAt(provider.getCreatedAt());
        return dto;
    }
}
