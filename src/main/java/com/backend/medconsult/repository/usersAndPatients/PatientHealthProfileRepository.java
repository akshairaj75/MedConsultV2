package com.backend.medconsult.repository.usersAndPatients;

import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.PatientHealthProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientHealthProfileRepository extends JpaRepository<PatientHealthProfile, UUID> {

    Optional<PatientHealthProfile> findByPatient_PatientId(UUID patientId);

    Optional<PatientHealthProfile> findByPatient(Patient patient);

    boolean existsByPatient(Patient patient);
}
