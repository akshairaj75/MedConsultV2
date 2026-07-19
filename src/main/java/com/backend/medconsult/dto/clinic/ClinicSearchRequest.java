package com.backend.medconsult.dto.clinic;

import java.util.UUID;

/**
 * Search filter DTO for paginated clinic search.
 * All fields are optional; null means "no filter".
 */
public class ClinicSearchRequest {

    private String name;
    private UUID specialtyId;
    private Boolean isActive;
    private int page = 0;
    private int size = 10;
    private String sortBy = "nameEn";
    private String sortDir = "asc";

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UUID getSpecialtyId() { return specialtyId; }
    public void setSpecialtyId(UUID specialtyId) { this.specialtyId = specialtyId; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortDir() { return sortDir; }
    public void setSortDir(String sortDir) { this.sortDir = sortDir; }
}
