package com.backend.medconsult.repository.platformAndCompliance;

import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.enums.platformAndCompliance.FileCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {

    Page<FileMetadata> findByPatient_PatientIdAndCategory(UUID patientId, FileCategory category, Pageable pageable);

    Page<FileMetadata> findByPatient_PatientId(UUID patientId, Pageable pageable);

    Page<FileMetadata> findByUploadedBy_UserId(UUID userId, Pageable pageable);
}
