package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty, UUID> {

    /** All specialties for a doctor. */
    List<DoctorSpecialty> findByDoctor_DoctorId(UUID doctorId);

    /** Prevent duplicate doctor-specialty combos. */
    boolean existsByDoctor_DoctorIdAndSpecialty_SpecialtyId(UUID doctorId, UUID specialtyId);

    /** Find the current primary specialty (if any). */
    java.util.Optional<DoctorSpecialty> findByDoctor_DoctorIdAndIsPrimaryTrue(UUID doctorId);
}
