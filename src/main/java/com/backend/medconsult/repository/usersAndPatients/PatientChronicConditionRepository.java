package com.backend.medconsult.repository.usersAndPatients;

import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.PatientChronicCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientChronicConditionRepository extends JpaRepository<PatientChronicCondition, UUID> {

    List<PatientChronicCondition> findByPatient(Patient patient);
}
