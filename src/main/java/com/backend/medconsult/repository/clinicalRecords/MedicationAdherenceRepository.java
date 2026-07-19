package com.backend.medconsult.repository.clinicalRecords;

import com.backend.medconsult.entity.clinicalRecords.MedicationAdherence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MedicationAdherenceRepository extends JpaRepository<MedicationAdherence, UUID> {

    List<MedicationAdherence> findByPatient_PatientId(UUID patientId);

    List<MedicationAdherence> findByRxItem_ItemId(UUID rxItemId);

    @Query(value = """
        SELECT m FROM MedicationAdherence m
        WHERE (:patientId IS NULL OR m.patient.patientId = :patientId)
          AND (:rxItemId IS NULL OR m.rxItem.itemId = :rxItemId)
    """,
    countQuery = """
        SELECT COUNT(m) FROM MedicationAdherence m
        WHERE (:patientId IS NULL OR m.patient.patientId = :patientId)
          AND (:rxItemId IS NULL OR m.rxItem.itemId = :rxItemId)
    """)
    Page<MedicationAdherence> searchAdherence(
            @Param("patientId") UUID patientId,
            @Param("rxItemId") UUID rxItemId,
            Pageable pageable);
}
