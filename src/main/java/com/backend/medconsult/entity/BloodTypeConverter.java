package com.backend.medconsult.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.backend.medconsult.enums.usersAndPatients.*;

@Converter(autoApply = true)
public class BloodTypeConverter implements AttributeConverter<BloodType, String> {

    @Override
    public String convertToDatabaseColumn(BloodType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbValue();
    }

    @Override
    public BloodType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return BloodType.fromDbValue(dbData);
    }
}
