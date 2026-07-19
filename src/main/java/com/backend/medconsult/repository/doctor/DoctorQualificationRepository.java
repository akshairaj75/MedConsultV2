package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorQualificationRepository extends JpaRepository<DoctorQualification, UUID> {

    /** All qualifications for a doctor ordered by sort order. */
    List<DoctorQualification> findByDoctor_DoctorIdOrderBySortOrderAsc(UUID doctorId);
}
