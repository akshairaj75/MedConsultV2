package com.backend.medconsult.repository.clinic;

import com.backend.medconsult.entity.clinic.ClinicLanguage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClinicLanguageRepository extends JpaRepository<ClinicLanguage, UUID> {

    List<ClinicLanguage> findByClinic_ClinicId(UUID clinicId);

    boolean existsByClinic_ClinicIdAndLanguage_LanguageId(UUID clinicId, UUID languageId);

    void deleteByClinic_ClinicIdAndLanguage_LanguageId(UUID clinicId, UUID languageId);
}
