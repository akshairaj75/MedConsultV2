package com.backend.medconsult.dto.clinic;

import java.util.UUID;

public class ClinicLanguageRequestDto {

    private UUID clinicId;
    private UUID languageId;

    public UUID getClinicId() {
        return clinicId;
    }

    public void setClinicId(UUID clinicId) {
        this.clinicId = clinicId;
    }

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }
}
