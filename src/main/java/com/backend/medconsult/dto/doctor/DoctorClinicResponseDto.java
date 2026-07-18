package com.backend.medconsult.dto.doctor;

import com.backend.medconsult.entity.doctor.DoctorClinic;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DoctorClinicResponseDto {

    private UUID dcId;
    private UUID doctorId;
    private UUID clinicId;
    private UUID branchId;
    private String department;
    private BigDecimal consultationFeeSar;
    private Boolean isPrimary;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getDcId() {
        return dcId;
    }

    public void setDcId(UUID dcId) {
        this.dcId = dcId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getClinicId() {
        return clinicId;
    }

    public void setClinicId(UUID clinicId) {
        this.clinicId = clinicId;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getConsultationFeeSar() {
        return consultationFeeSar;
    }

    public void setConsultationFeeSar(BigDecimal consultationFeeSar) {
        this.consultationFeeSar = consultationFeeSar;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static DoctorClinicResponseDto fromEntity(DoctorClinic dc) {
        if (dc == null) {
            return null;
        }
        DoctorClinicResponseDto dto = new DoctorClinicResponseDto();
        dto.setDcId(dc.getDcId());
        if (dc.getDoctor() != null) {
            dto.setDoctorId(dc.getDoctor().getDoctorId());
        }
        if (dc.getClinic() != null) {
            dto.setClinicId(dc.getClinic().getClinicId());
        }
        if (dc.getBranch() != null) {
            dto.setBranchId(dc.getBranch().getBranchId());
        }
        dto.setDepartment(dc.getDepartment());
        dto.setConsultationFeeSar(dc.getConsultationFeeSar());
        dto.setIsPrimary(dc.getIsPrimary());
        dto.setStartDate(dc.getStartDate());
        dto.setEndDate(dc.getEndDate());
        dto.setIsActive(dc.getIsActive());
        dto.setCreatedAt(dc.getCreatedAt());
        dto.setUpdatedAt(dc.getUpdatedAt());
        return dto;
    }
}
