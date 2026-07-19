package com.backend.medconsult.repository.clinicalRecords;

import com.backend.medconsult.entity.clinicalRecords.LabItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LabItemRepository extends JpaRepository<LabItem, UUID> {
    List<LabItem> findByLabResult_LabResultIdOrderBySortOrderAsc(UUID labResultId);

}
