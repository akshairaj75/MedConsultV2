package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorLanguageRepository extends JpaRepository<DoctorLanguage, UUID> {

    /** All languages for a given doctor. */
    List<DoctorLanguage> findByDoctor_DoctorId(UUID doctorId);

    /** Check if a doctor already has a particular language. */
    boolean existsByDoctor_DoctorIdAndLanguage_LanguageId(UUID doctorId, UUID languageId);
}
