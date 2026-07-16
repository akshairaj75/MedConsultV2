package com.backend.medconsult.repository.clinicalRecords;

import com.backend.medconsult.entity.clinicalRecords.Vital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VitalRepository extends JpaRepository<Vital, UUID> {
}
