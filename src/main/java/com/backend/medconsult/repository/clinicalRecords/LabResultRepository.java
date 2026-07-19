package com.backend.medconsult.repository.clinicalRecords;

import com.backend.medconsult.entity.clinicalRecords.LabResult;
import com.backend.medconsult.enums.clinicalRecords.LabResultStatus;
import com.backend.medconsult.enums.clinicalRecords.ResultFlag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LabResultRepository extends JpaRepository<LabResult, UUID> {

    List<LabResult> findByPatient_PatientId(UUID patientId);

    @Query(value = """
        SELECT l FROM LabResult l
        WHERE (:patientId IS NULL OR l.patient.patientId = :patientId)
          AND (:orderedById IS NULL OR l.orderedBy.doctorId = :orderedById)
          AND (:status IS NULL OR l.status = :status)
          AND (:overallFlag IS NULL OR l.overallFlag = :overallFlag)
    """,
    countQuery = """
        SELECT COUNT(l) FROM LabResult l
        WHERE (:patientId IS NULL OR l.patient.patientId = :patientId)
          AND (:orderedById IS NULL OR l.orderedBy.doctorId = :orderedById)
          AND (:status IS NULL OR l.status = :status)
          AND (:overallFlag IS NULL OR l.overallFlag = :overallFlag)
    """)
    Page<LabResult> searchLabResults(
            @Param("patientId") UUID patientId,
            @Param("orderedById") UUID orderedById,
            @Param("status") LabResultStatus status,
            @Param("overallFlag") ResultFlag overallFlag,
            Pageable pageable);
}
