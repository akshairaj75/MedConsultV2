package com.backend.medconsult.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.backend.medconsult.enums.doctor.*;

@Converter(autoApply = true)
public class LanguageProficiencyConverter implements AttributeConverter<LanguageProficiency, String> {

    @Override
    public String convertToDatabaseColumn(LanguageProficiency attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbValue();
    }

    @Override
    public LanguageProficiency convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LanguageProficiency.fromDbValue(dbData);
    }
}
