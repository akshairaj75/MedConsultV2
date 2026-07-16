package com.backend.medconsult.enums.doctor;

public enum DoctorTitle {
    DR("Dr."),
    PROF("Prof."),
    ASSOC_PROF("Assoc. Prof.");

    private final String dbValue;

    DoctorTitle(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static DoctorTitle fromDbValue(String dbValue) {
        for (DoctorTitle title : DoctorTitle.values()) {
            if (title.dbValue.equalsIgnoreCase(dbValue)) {
                return title;
            }
        }
        return DR;
    }
}
