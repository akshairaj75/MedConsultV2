package com.backend.medconsult.repository.clinicalRecords;

import com.backend.medconsult.entity.clinicalRecords.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface LabResultRepository extends JpaRepository<LabResult, UUID> {
}
