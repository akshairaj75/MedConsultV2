package com.backend.medconsult.dto.clinic;

import com.backend.medconsult.entity.clinic.ClinicLanguage;
import java.util.UUID;

public class ClinicLanguageResponseDto {

    private UUID id;
    private UUID clinicId;
    private UUID languageId;

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

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public static ClinicLanguageResponseDto fromEntity(ClinicLanguage lang) {
        if (lang == null) {
            return null;
        }
        ClinicLanguageResponseDto dto = new ClinicLanguageResponseDto();
        dto.setId(lang.getId());
        if (lang.getClinic() != null) {
            dto.setClinicId(lang.getClinic().getClinicId());
        }
        if (lang.getLanguage() != null) {
            dto.setLanguageId(lang.getLanguage().getLanguageId());
        }
        return dto;
    }
}
