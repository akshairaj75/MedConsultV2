package com.backend.medconsult.dto.appointment;

import com.backend.medconsult.enums.appointments.AppointmentStatus;
import com.backend.medconsult.enums.appointments.AppointmentType;
import com.backend.medconsult.enums.doctor.SessionType;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Search/filter criteria for querying appointments with pagination.
 * All fields are optional; omitted fields are ignored in the query.
 */
public class AppointmentSearchRequest {

    private UUID patientId;
    private UUID doctorId;
    private UUID dcId;
    private AppointmentStatus status;
    private AppointmentType appointmentType;
    private SessionType sessionType;
    private LocalDate fromDate;
    private LocalDate toDate;

    // Pagination
    private int page = 0;
    private int size = 10;

    // Sort
    private String sortBy = "scheduledDate";
    private String sortDir = "DESC";

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

    public UUID getDcId() {
        return dcId;
    }

    public void setDcId(UUID dcId) {
        this.dcId = dcId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
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
