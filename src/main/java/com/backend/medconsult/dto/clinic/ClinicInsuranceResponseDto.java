package com.backend.medconsult.dto.clinic;

import com.backend.medconsult.entity.clinic.ClinicInsurance;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClinicInsuranceResponseDto {

    private UUID id;
    private UUID clinicId;
    private UUID providerId;
    private String networkClass;
    private Boolean isActive;
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

    public UUID getProviderId() {
        return providerId;
    }

    public void setProviderId(UUID providerId) {
        this.providerId = providerId;
    }

    public String getNetworkClass() {
        return networkClass;
    }

    public void setNetworkClass(String networkClass) {
        this.networkClass = networkClass;
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

    public static ClinicInsuranceResponseDto fromEntity(ClinicInsurance insurance) {
        if (insurance == null) {
            return null;
        }
        ClinicInsuranceResponseDto dto = new ClinicInsuranceResponseDto();
        dto.setId(insurance.getId());
        if (insurance.getClinic() != null) {
            dto.setClinicId(insurance.getClinic().getClinicId());
        }
        if (insurance.getProvider() != null) {
            dto.setProviderId(insurance.getProvider().getProviderId());
        }
        dto.setNetworkClass(insurance.getNetworkClass());
        dto.setIsActive(insurance.getIsActive());
        dto.setCreatedAt(insurance.getCreatedAt());
        return dto;
    }
}
