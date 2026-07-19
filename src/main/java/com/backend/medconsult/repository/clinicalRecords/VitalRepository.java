package com.backend.medconsult.repository.clinicalRecords;

import com.backend.medconsult.entity.clinicalRecords.Vital;
import com.backend.medconsult.enums.clinicalRecords.VitalSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VitalRepository extends JpaRepository<Vital, UUID> {

    List<Vital> findByPatient_PatientId(UUID patientId);

    @Query(value = """
        SELECT v FROM Vital v
        WHERE (:patientId IS NULL OR v.patient.patientId = :patientId)
          AND (:source IS NULL OR v.source = :source)
    """,
    countQuery = """
        SELECT COUNT(v) FROM Vital v
        WHERE (:patientId IS NULL OR v.patient.patientId = :patientId)
          AND (:source IS NULL OR v.source = :source)
    """)
    Page<Vital> searchVitals(
            @Param("patientId") UUID patientId,
            @Param("source") VitalSource source,
            Pageable pageable);
}
