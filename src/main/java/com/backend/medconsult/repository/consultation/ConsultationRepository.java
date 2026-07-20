package com.backend.medconsult.repository.consultation;

import com.backend.medconsult.entity.consultation.Consultation;
import com.backend.medconsult.enums.consultation.ConsultationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {

    Page<Consultation> findByPatient_PatientIdOrderByLastMessageAtDesc(UUID patientId, Pageable pageable);

    Page<Consultation> findByDoctor_DoctorIdOrderByLastMessageAtDesc(UUID doctorId, Pageable pageable);

    @Query("SELECT c FROM Consultation c WHERE " +
           "(:patientId IS NULL OR c.patient.patientId = :patientId) AND " +
           "(:doctorId IS NULL OR c.doctor.doctorId = :doctorId) AND " +
           "(:status IS NULL OR c.status = :status) AND " +
           "(:isUrgent IS NULL OR c.isUrgent = :isUrgent) AND " +
           "(cast(:fromDate as timestamp) IS NULL OR c.openedAt >= :fromDate) AND " +
           "(cast(:toDate as timestamp) IS NULL OR c.openedAt <= :toDate)")
    Page<Consultation> search(
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("status") ConsultationStatus status,
            @Param("isUrgent") Boolean isUrgent,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable);
}
