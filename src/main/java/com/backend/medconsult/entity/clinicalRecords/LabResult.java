package com.backend.medconsult.entity.clinicalRecords;

import jakarta.persistence.*;

import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.enums.clinicalRecords.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "lab_results",
    indexes = {
        @Index(name = "idx_lab_patient", columnList = "patient_id, report_date DESC"),
        @Index(name = "idx_lab_status", columnList = "status, overall_flag"),
        @Index(name = "idx_lab_doctor", columnList = "ordered_by")
    }
)
public class LabResult {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "lab_result_id", nullable = false, updatable = false, length = 36)
    private UUID labResultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lab_results_patient"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ordered_by", foreignKey = @ForeignKey(name = "fk_lab_results_orderer"))
    private Doctor orderedBy;

    @Column(name = "lab_name", length = 200)
    private String labName;

    @Column(name = "report_type", nullable = false, length = 100)
    private String reportType;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LabResultStatus status = LabResultStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "overall_flag", nullable = false)
    private ResultFlag overallFlag = ResultFlag.NORMAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "fk_lab_results_file"))
    private FileMetadata file;

    @Column(name = "doctor_annotation", columnDefinition = "TEXT")
    private String doctorAnnotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annotated_by", foreignKey = @ForeignKey(name = "fk_lab_results_annotator"))
    private Doctor annotatedBy;

    @Column(name = "annotated_at")
    private LocalDateTime annotatedAt;

    @Column(name = "naphies_reference", length = 100)
    private String naphiesReference;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public LabResult() {
    }

    public UUID getLabResultId() {
        return labResultId;
    }

    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(Doctor orderedBy) {
        this.orderedBy = orderedBy;
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

    public FileMetadata getFile() {
        return file;
    }

    public void setFile(FileMetadata file) {
        this.file = file;
    }

    public String getDoctorAnnotation() {
        return doctorAnnotation;
    }

    public void setDoctorAnnotation(String doctorAnnotation) {
        this.doctorAnnotation = doctorAnnotation;
    }

    public Doctor getAnnotatedBy() {
        return annotatedBy;
    }

    public void setAnnotatedBy(Doctor annotatedBy) {
        this.annotatedBy = annotatedBy;
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
}
