package com.backend.medconsult.dto.clinic;

import com.backend.medconsult.entity.clinic.ClinicBranch;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ClinicBranchResponseDto {

    private UUID branchId;
    private UUID clinicId;
    private String branchNameEn;
    private String branchNameAr;
    private UUID localityId;
    private UUID cityId;
    private String addressLine1;
    private String addressLine2;
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String mapsUrl;
    private String phone;
    private Boolean isPrimary;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public UUID getClinicId() {
        return clinicId;
    }

    public void setClinicId(UUID clinicId) {
        this.clinicId = clinicId;
    }

    public String getBranchNameEn() {
        return branchNameEn;
    }

    public void setBranchNameEn(String branchNameEn) {
        this.branchNameEn = branchNameEn;
    }

    public String getBranchNameAr() {
        return branchNameAr;
    }

    public void setBranchNameAr(String branchNameAr) {
        this.branchNameAr = branchNameAr;
    }

    public UUID getLocalityId() {
        return localityId;
    }

    public void setLocalityId(UUID localityId) {
        this.localityId = localityId;
    }

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getMapsUrl() {
        return mapsUrl;
    }

    public void setMapsUrl(String mapsUrl) {
        this.mapsUrl = mapsUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
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

    public static ClinicBranchResponseDto fromEntity(ClinicBranch branch) {
        if (branch == null) {
            return null;
        }
        ClinicBranchResponseDto dto = new ClinicBranchResponseDto();
        dto.setBranchId(branch.getBranchId());
        if (branch.getClinic() != null) {
            dto.setClinicId(branch.getClinic().getClinicId());
        }
        dto.setBranchNameEn(branch.getBranchNameEn());
        dto.setBranchNameAr(branch.getBranchNameAr());
        if (branch.getLocality() != null) {
            dto.setLocalityId(branch.getLocality().getLocalityId());
        }
        if (branch.getCity() != null) {
            dto.setCityId(branch.getCity().getCityId());
        }
        dto.setAddressLine1(branch.getAddressLine1());
        dto.setAddressLine2(branch.getAddressLine2());
        dto.setPostalCode(branch.getPostalCode());
        dto.setLatitude(branch.getLatitude());
        dto.setLongitude(branch.getLongitude());
        dto.setMapsUrl(branch.getMapsUrl());
        dto.setPhone(branch.getPhone());
        dto.setIsPrimary(branch.getIsPrimary());
        dto.setIsActive(branch.getIsActive());
        dto.setCreatedAt(branch.getCreatedAt());
        dto.setUpdatedAt(branch.getUpdatedAt());
        return dto;
    }
}
