package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.enums.doctor.LanguageProficiency;
import java.util.UUID;

public class DoctorLanguageRequestDto {

    private UUID doctorId;
    private UUID languageId;
    private LanguageProficiency proficiency;

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
}
