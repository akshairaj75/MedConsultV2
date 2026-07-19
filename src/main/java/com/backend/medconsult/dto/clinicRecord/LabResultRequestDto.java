package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.enums.clinicalRecords.LabResultStatus;
import com.backend.medconsult.enums.clinicalRecords.ResultFlag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class LabResultRequestDto {

    private UUID patientId;
    private UUID orderedById;
    private String labName;
    private String reportType;
    private LocalDate reportDate;
    private LabResultStatus status;
    private ResultFlag overallFlag;
    private UUID fileId;
    private String doctorAnnotation;
    private UUID annotatedById;
    private LocalDateTime annotatedAt;
    private String naphiesReference;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getOrderedById() {
        return orderedById;
    }

    public void setOrderedById(UUID orderedById) {
        this.orderedById = orderedById;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public LabResultStatus getStatus() {
        return status;
    }

    public void setStatus(LabResultStatus status) {
        this.status = status;
    }

    public ResultFlag getOverallFlag() {
        return overallFlag;
    }

    public void setOverallFlag(ResultFlag overallFlag) {
        this.overallFlag = overallFlag;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public String getDoctorAnnotation() {
        return doctorAnnotation;
    }

    public void setDoctorAnnotation(String doctorAnnotation) {
        this.doctorAnnotation = doctorAnnotation;
    }

    public UUID getAnnotatedById() {
        return annotatedById;
    }

    public void setAnnotatedById(UUID annotatedById) {
        this.annotatedById = annotatedById;
    }

    public LocalDateTime getAnnotatedAt() {
        return annotatedAt;
    }

    public void setAnnotatedAt(LocalDateTime annotatedAt) {
        this.annotatedAt = annotatedAt;
    }

    public String getNaphiesReference() {
        return naphiesReference;
    }

    public void setNaphiesReference(String naphiesReference) {
        this.naphiesReference = naphiesReference;
    }
}
