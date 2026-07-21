package com.backend.medconsult.dto.clinic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class ClinicBranchRequestDto {

    private UUID clinicId;

    @NotBlank(message = "English branch name is required")
    @Size(max = 150, message = "English branch name must not exceed 150 characters")
    private String branchNameEn;

    @NotBlank(message = "Arabic branch name is required")
    @Size(max = 150, message = "Arabic branch name must not exceed 150 characters")
    private String branchNameAr;

    @NotNull(message = "Locality ID is required")
    private UUID localityId;

    @NotNull(message = "City ID is required")
    private UUID cityId;

    @NotBlank(message = "Address line 1 is required")
    @Size(max = 300, message = "Address line 1 must not exceed 300 characters")
    private String addressLine1;

    @Size(max = 300, message = "Address line 2 must not exceed 300 characters")
    private String addressLine2;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Size(max = 500, message = "Maps URL must not exceed 500 characters")
    private String mapsUrl;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    private Boolean isPrimary;
    private Boolean isActive;

    // ── Getters & Setters ──────────────────────────────────────────────

    public UUID getClinicId() { return clinicId; }
    public void setClinicId(UUID clinicId) { this.clinicId = clinicId; }

    public String getBranchNameEn() { return branchNameEn; }
    public void setBranchNameEn(String branchNameEn) { this.branchNameEn = branchNameEn; }

    public String getBranchNameAr() { return branchNameAr; }
    public void setBranchNameAr(String branchNameAr) { this.branchNameAr = branchNameAr; }

    public UUID getLocalityId() { return localityId; }
    public void setLocalityId(UUID localityId) { this.localityId = localityId; }

    public UUID getCityId() { return cityId; }
    public void setCityId(UUID cityId) { this.cityId = cityId; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public String getMapsUrl() { return mapsUrl; }
    public void setMapsUrl(String mapsUrl) { this.mapsUrl = mapsUrl; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
