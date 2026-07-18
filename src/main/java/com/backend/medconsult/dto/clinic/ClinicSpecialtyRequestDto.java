package com.backend.medconsult.dto.clinic;

import java.util.UUID;

public class ClinicSpecialtyRequestDto {

    private UUID clinicId;
    private UUID specialtyId;

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
}
