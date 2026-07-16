package com.backend.medconsult.enums.doctor;

public enum LanguageProficiency {
    NATIVE("native"),
    FLUENT("fluent"),
    CONVERSATIONAL("conversational");

    private final String dbValue;

    LanguageProficiency(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static LanguageProficiency fromDbValue(String dbValue) {
        for (LanguageProficiency prof : LanguageProficiency.values()) {
            if (prof.dbValue.equalsIgnoreCase(dbValue)) {
                return prof;
            }
        }
        return FLUENT;
    }
}
