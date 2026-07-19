package com.backend.medconsult.repository.clinic;

import com.backend.medconsult.entity.clinic.ClinicSpecialty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClinicSpecialtyRepository extends JpaRepository<ClinicSpecialty, UUID> {

    List<ClinicSpecialty> findByClinic_ClinicId(UUID clinicId);

    boolean existsByClinic_ClinicIdAndSpecialty_SpecialtyId(UUID clinicId, UUID specialtyId);

    void deleteByClinic_ClinicIdAndSpecialty_SpecialtyId(UUID clinicId, UUID specialtyId);
}
