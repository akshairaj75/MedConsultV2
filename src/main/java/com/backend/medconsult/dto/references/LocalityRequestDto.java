package com.backend.medconsult.dto.references;

import java.math.BigDecimal;
import java.util.UUID;

public class LocalityRequestDto {

    private UUID cityId;
    private String nameEn;
    private String nameAr;
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isActive;

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
