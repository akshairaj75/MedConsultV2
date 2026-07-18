package com.backend.medconsult.dto.doctor;

import java.util.UUID;

public class DoctorSpecialtyRequestDto {

    private UUID doctorId;
    private UUID specialtyId;
    private UUID subSpecialtyId;
    private Boolean isPrimary;

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(UUID specialtyId) {
        this.specialtyId = specialtyId;
    }

    public UUID getSubSpecialtyId() {
        return subSpecialtyId;
    }

    public void setSubSpecialtyId(UUID subSpecialtyId) {
        this.subSpecialtyId = subSpecialtyId;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }
}
