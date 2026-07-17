package com.backend.medconsult.dto.references;

import com.backend.medconsult.entity.references.Locality;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class LocalityResponseDto {

    private UUID localityId;
    private UUID cityId;
    private String nameEn;
    private String nameAr;
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isActive;
    private LocalDateTime createdAt;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static LocalityResponseDto fromEntity(Locality locality) {
        if (locality == null) {
            return null;
        }
        LocalityResponseDto dto = new LocalityResponseDto();
        dto.setLocalityId(locality.getLocalityId());
        if (locality.getCity() != null) {
            dto.setCityId(locality.getCity().getCityId());
        }
        dto.setNameEn(locality.getNameEn());
        dto.setNameAr(locality.getNameAr());
        dto.setPostalCode(locality.getPostalCode());
        dto.setLatitude(locality.getLatitude());
        dto.setLongitude(locality.getLongitude());
        dto.setIsActive(locality.getIsActive());
        dto.setCreatedAt(locality.getCreatedAt());
        return dto;
    }
}
