package com.backend.medconsult.repository.usersAndPatients;

import com.backend.medconsult.entity.usersAndPatients.PatientAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PatientAllergyRepository extends JpaRepository<PatientAllergy, UUID> {
}
