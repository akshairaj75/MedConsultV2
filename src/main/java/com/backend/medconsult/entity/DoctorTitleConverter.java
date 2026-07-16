package com.backend.medconsult.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.backend.medconsult.enums.doctor.*;

@Converter(autoApply = true)
public class DoctorTitleConverter implements AttributeConverter<DoctorTitle, String> {

    @Override
    public String convertToDatabaseColumn(DoctorTitle attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbValue();
    }

    @Override
    public DoctorTitle convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return DoctorTitle.fromDbValue(dbData);
    }
}
