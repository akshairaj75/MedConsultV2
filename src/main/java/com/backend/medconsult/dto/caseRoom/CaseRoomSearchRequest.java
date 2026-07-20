package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.enums.caseRoom.CasePriority;
import com.backend.medconsult.enums.caseRoom.CaseRoomStatus;

import java.util.UUID;

public class CaseRoomSearchRequest {

    private UUID patientId;
    private UUID doctorId;
    private CaseRoomStatus status;
    private CasePriority priority;

    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDir = "DESC";

    public CaseRoomSearchRequest() {
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public CaseRoomStatus getStatus() {
        return status;
    }

    public void setStatus(CaseRoomStatus status) {
        this.status = status;
    }

    public CasePriority getPriority() {
        return priority;
    }

    public void setPriority(CasePriority priority) {
        this.priority = priority;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
