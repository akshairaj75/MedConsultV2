package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.entity.doctor.DoctorSpecialty;
import java.time.LocalDateTime;
import java.util.UUID;

public class DoctorSpecialtyResponseDto {

    private UUID id;
    private UUID doctorId;
    private UUID specialtyId;
    private UUID subSpecialtyId;
    private Boolean isPrimary;
    private LocalDateTime createdAt;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static DoctorSpecialtyResponseDto fromEntity(DoctorSpecialty ds) {
        if (ds == null) {
            return null;
        }
        DoctorSpecialtyResponseDto dto = new DoctorSpecialtyResponseDto();
        dto.setId(ds.getId());
        if (ds.getDoctor() != null) {
            dto.setDoctorId(ds.getDoctor().getDoctorId());
        }
        if (ds.getSpecialty() != null) {
            dto.setSpecialtyId(ds.getSpecialty().getSpecialtyId());
        }
        if (ds.getSubSpecialty() != null) {
            dto.setSubSpecialtyId(ds.getSubSpecialty().getSubSpecialtyId());
        }
        dto.setIsPrimary(ds.getIsPrimary());
        dto.setCreatedAt(ds.getCreatedAt());
        return dto;
    }
}
