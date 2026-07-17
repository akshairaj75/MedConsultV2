package com.backend.medconsult.dto.references;

import com.backend.medconsult.entity.references.City;
import java.time.LocalDateTime;
import java.util.UUID;

public class CityResponseDto {

    private UUID cityId;
    private String countryCode;
    private String nameEn;
    private String nameAr;
    private Boolean isActive;
    private Short sortOrder;
    private LocalDateTime createdAt;

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Short getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Short sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static CityResponseDto fromEntity(City city) {
        if (city == null) {
            return null;
        }
        CityResponseDto dto = new CityResponseDto();
        dto.setCityId(city.getCityId());
        dto.setCountryCode(city.getCountryCode());
        dto.setNameEn(city.getNameEn());
        dto.setNameAr(city.getNameAr());
        dto.setIsActive(city.getIsActive());
        dto.setSortOrder(city.getSortOrder());
        dto.setCreatedAt(city.getCreatedAt());
        return dto;
    }
}
