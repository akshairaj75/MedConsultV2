package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.entity.doctor.DoctorLanguage;
import com.backend.medconsult.enums.doctor.LanguageProficiency;
import java.util.UUID;

public class DoctorLanguageResponseDto {

    private UUID id;
    private UUID doctorId;
    private UUID languageId;
    private LanguageProficiency proficiency;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public LanguageProficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(LanguageProficiency proficiency) {
        this.proficiency = proficiency;
    }

    public static DoctorLanguageResponseDto fromEntity(DoctorLanguage dl) {
        if (dl == null) {
            return null;
        }
        DoctorLanguageResponseDto dto = new DoctorLanguageResponseDto();
        dto.setId(dl.getId());
        if (dl.getDoctor() != null) {
            dto.setDoctorId(dl.getDoctor().getDoctorId());
        }
        if (dl.getLanguage() != null) {
            dto.setLanguageId(dl.getLanguage().getLanguageId());
        }
        dto.setProficiency(dl.getProficiency());
        return dto;
    }
}
