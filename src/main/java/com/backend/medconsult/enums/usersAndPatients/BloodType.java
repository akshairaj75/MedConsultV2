package com.backend.medconsult.enums.usersAndPatients;

public enum BloodType {
    A_POS("A+"),
    A_NEG("A-"),
    B_POS("B+"),
    B_NEG("B-"),
    AB_POS("AB+"),
    AB_NEG("AB-"),
    O_POS("O+"),
    O_NEG("O-"),
    Unknown("Unknown");

    private final String dbValue;

    BloodType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static BloodType fromDbValue(String dbValue) {
        for (BloodType type : BloodType.values()) {
            if (type.dbValue.equalsIgnoreCase(dbValue)) {
                return type;
            }
        }
        return Unknown;
    }
}
