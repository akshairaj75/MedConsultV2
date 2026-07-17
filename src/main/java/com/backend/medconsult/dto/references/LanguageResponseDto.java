package com.backend.medconsult.dto.references;

import com.backend.medconsult.entity.references.Language;
import java.util.UUID;

public class LanguageResponseDto {

    private UUID languageId;
    private String code;
    private String nameEn;
    private String nameAr;
    private Boolean isActive;

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public static LanguageResponseDto fromEntity(Language language) {
        if (language == null) {
            return null;
        }
        LanguageResponseDto dto = new LanguageResponseDto();
        dto.setLanguageId(language.getLanguageId());
        dto.setCode(language.getCode());
        dto.setNameEn(language.getNameEn());
        dto.setNameAr(language.getNameAr());
        dto.setIsActive(language.getIsActive());
        return dto;
    }
}
