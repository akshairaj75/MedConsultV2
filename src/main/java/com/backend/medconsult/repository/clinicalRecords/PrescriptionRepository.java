package com.backend.medconsult.repository.clinicalRecords;

import com.backend.medconsult.entity.clinicalRecords.Prescription;
import com.backend.medconsult.enums.clinicalRecords.PrescriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    List<Prescription> findByPatient_PatientId(UUID patientId);

    List<Prescription> findByDoctor_DoctorId(UUID doctorId);

    @Query(value = """
        SELECT p FROM Prescription p
        WHERE (:patientId IS NULL OR p.patient.patientId = :patientId)
          AND (:doctorId IS NULL OR p.doctor.doctorId = :doctorId)
          AND (:status IS NULL OR p.status = :status)
    """,
    countQuery = """
        SELECT COUNT(p) FROM Prescription p
        WHERE (:patientId IS NULL OR p.patient.patientId = :patientId)
          AND (:doctorId IS NULL OR p.doctor.doctorId = :doctorId)
          AND (:status IS NULL OR p.status = :status)
    """)
    Page<Prescription> searchPrescriptions(
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("status") PrescriptionStatus status,
            Pageable pageable);
}
