package com.backend.medconsult.repository.usersAndPatients;

import com.backend.medconsult.entity.usersAndPatients.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
