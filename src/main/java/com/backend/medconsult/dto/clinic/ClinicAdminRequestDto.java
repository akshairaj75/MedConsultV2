package com.backend.medconsult.dto.clinic;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ClinicAdminRequestDto {

    @NotNull(message = "Clinic ID is required")
    private UUID clinicId;

    @NotNull(message = "User ID is required")
    private UUID userId;

    private Boolean isPrimary = true;

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

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
}
