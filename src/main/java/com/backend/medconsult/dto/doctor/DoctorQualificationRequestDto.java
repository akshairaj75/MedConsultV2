package com.backend.medconsult.dto.doctor;

import java.util.UUID;

public class DoctorQualificationRequestDto {

    private UUID doctorId;
    private String degree;
    private String institution;
    private String country;
    private Integer yearObtained;
    private Byte sortOrder;

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getYearObtained() {
        return yearObtained;
    }

    public void setYearObtained(Integer yearObtained) {
        this.yearObtained = yearObtained;
    }

    public Byte getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Byte sortOrder) {
        this.sortOrder = sortOrder;
    }
}
