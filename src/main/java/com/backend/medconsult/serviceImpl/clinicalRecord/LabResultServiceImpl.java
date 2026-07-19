package com.backend.medconsult.serviceImpl.clinicalRecord;

import com.backend.medconsult.dto.clinicRecord.LabItemRequestDto;
import com.backend.medconsult.dto.clinicRecord.LabItemResponseDto;
import com.backend.medconsult.dto.clinicRecord.LabResultRequestDto;
import com.backend.medconsult.dto.clinicRecord.LabResultResponseDto;
import com.backend.medconsult.entity.clinicalRecords.LabItem;
import com.backend.medconsult.entity.clinicalRecords.LabResult;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.enums.clinicalRecords.LabResultStatus;
import com.backend.medconsult.enums.clinicalRecords.ResultFlag;
import com.backend.medconsult.enums.platformAndCompliance.FileCategory;
import com.backend.medconsult.repository.clinicalRecords.LabItemRepository;
import com.backend.medconsult.repository.clinicalRecords.LabResultRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.platformAndCompliance.FileMetadataRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.FileStorageService;
import com.backend.medconsult.service.clinicRecord.LabResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LabResultServiceImpl implements LabResultService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("reportDate", "createdAt", "status");

    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private LabItemRepository labItemRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Page<LabResultResponseDto> searchLabResults(UUID patientId, UUID orderedById, LabResultStatus status, ResultFlag overallFlag, int page, int size, String sortBy, String sortDir) {
        String sortField = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "reportDate";
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return labResultRepository.searchLabResults(patientId, orderedById, status, overallFlag, pageable)
                .map(LabResultResponseDto::fromEntity);
    }

    @Override
    public LabResultResponseDto getLabResultById(UUID id) {
        LabResult labResult = findLabResultOrThrow(id);
        return LabResultResponseDto.fromEntity(labResult);
    }

    @Transactional
    @Override
    public LabResultResponseDto createLabResult(LabResultRequestDto dto, MultipartFile file, CustomUserPrincipal principal) {
        LabResult labResult = new LabResult();
        
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
        labResult.setPatient(patient);

        if (dto.getOrderedById() != null) {
            Doctor orderedBy = doctorRepository.findById(dto.getOrderedById())
                    .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getOrderedById()));
            labResult.setOrderedBy(orderedBy);
        }

        applyDtoToLabResult(labResult, dto);

        if (file != null && !file.isEmpty()) {
            FileMetadata fileMetadata = storeLabResultFile(file);
            labResult.setFile(fileMetadata);
        }

        return LabResultResponseDto.fromEntity(labResultRepository.save(labResult));
    }

    @Transactional
    @Override
    public LabResultResponseDto updateLabResult(UUID id, LabResultRequestDto dto, MultipartFile file, CustomUserPrincipal principal) {
        LabResult labResult = findLabResultOrThrow(id);

        if (dto.getPatientId() != null && !dto.getPatientId().equals(labResult.getPatient().getPatientId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
            labResult.setPatient(patient);
        }

        if (dto.getOrderedById() != null) {
            if (labResult.getOrderedBy() == null || !dto.getOrderedById().equals(labResult.getOrderedBy().getDoctorId())) {
                Doctor orderedBy = doctorRepository.findById(dto.getOrderedById())
                        .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getOrderedById()));
                labResult.setOrderedBy(orderedBy);
            }
        }

        if (dto.getAnnotatedById() != null) {
             Doctor annotatedBy = doctorRepository.findById(dto.getAnnotatedById())
                        .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getAnnotatedById()));
             labResult.setAnnotatedBy(annotatedBy);
        }

        applyDtoToLabResult(labResult, dto);

        if (file != null && !file.isEmpty()) {
            FileMetadata fileMetadata = storeLabResultFile(file);
            labResult.setFile(fileMetadata);
        } else if (dto.getFileId() != null) {
             FileMetadata existingFile = fileMetadataRepository.findById(dto.getFileId())
                    .orElseThrow(() -> new RuntimeException("File not found: " + dto.getFileId()));
             labResult.setFile(existingFile);
        }

        return LabResultResponseDto.fromEntity(labResultRepository.save(labResult));
    }

    @Transactional
    @Override
    public void deleteLabResult(UUID id, CustomUserPrincipal principal) {
        LabResult labResult = findLabResultOrThrow(id);
        labResultRepository.delete(labResult);
    }

    @Override
    public List<LabItemResponseDto> getLabItems(UUID labResultId) {
        return labItemRepository.findByLabResult_LabResultIdOrderBySortOrderAsc(labResultId)
                .stream()
                .map(LabItemResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public LabItemResponseDto addLabItem(UUID labResultId, LabItemRequestDto dto, CustomUserPrincipal principal) {
        LabResult labResult = findLabResultOrThrow(labResultId);

        LabItem item = new LabItem();
        item.setLabResult(labResult);
        applyDtoToLabItem(item, dto);

        return LabItemResponseDto.fromEntity(labItemRepository.save(item));
    }

    @Transactional
    @Override
    public LabItemResponseDto updateLabItem(UUID itemId, LabItemRequestDto dto, CustomUserPrincipal principal) {
        LabItem item = labItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Lab Item not found: " + itemId));

        applyDtoToLabItem(item, dto);

        return LabItemResponseDto.fromEntity(labItemRepository.save(item));
    }

    @Transactional
    @Override
    public void deleteLabItem(UUID itemId, CustomUserPrincipal principal) {
        LabItem item = labItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Lab Item not found: " + itemId));
        labItemRepository.delete(item);
    }

    private LabResult findLabResultOrThrow(UUID id) {
        return labResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lab Result not found: " + id));
    }

    private void applyDtoToLabResult(LabResult labResult, LabResultRequestDto dto) {
        if (dto.getLabName() != null) labResult.setLabName(dto.getLabName());
        if (dto.getReportType() != null) labResult.setReportType(dto.getReportType());
        if (dto.getReportDate() != null) labResult.setReportDate(dto.getReportDate());
        if (dto.getStatus() != null) labResult.setStatus(dto.getStatus());
        if (dto.getOverallFlag() != null) labResult.setOverallFlag(dto.getOverallFlag());
        if (dto.getDoctorAnnotation() != null) labResult.setDoctorAnnotation(dto.getDoctorAnnotation());
        if (dto.getAnnotatedAt() != null) labResult.setAnnotatedAt(dto.getAnnotatedAt());
        if (dto.getNaphiesReference() != null) labResult.setNaphiesReference(dto.getNaphiesReference());
    }

    private void applyDtoToLabItem(LabItem item, LabItemRequestDto dto) {
        if (dto.getTestName() != null) item.setTestName(dto.getTestName());
        if (dto.getLoincCode() != null) item.setLoincCode(dto.getLoincCode());
        if (dto.getValue() != null) item.setValue(dto.getValue());
        if (dto.getUnit() != null) item.setUnit(dto.getUnit());
        if (dto.getReferenceLow() != null) item.setReferenceLow(dto.getReferenceLow());
        if (dto.getReferenceHigh() != null) item.setReferenceHigh(dto.getReferenceHigh());
        if (dto.getFlag() != null) item.setFlag(dto.getFlag());
        if (dto.getSortOrder() != null) item.setSortOrder(dto.getSortOrder());
    }

    private FileMetadata storeLabResultFile(MultipartFile file) {
        try {
            String storageKey = fileStorageService.storeFile(file, "clinicalRecords/labResults");
            FileMetadata metadata = new FileMetadata();
            metadata.setOriginalFilename(file.getOriginalFilename());
            metadata.setStorageKey(storageKey);
            metadata.setMimeType(file.getContentType());
            metadata.setSizeBytes(file.getSize());
            metadata.setCategory(FileCategory.LAB_REPORT);
            metadata.setIsEncrypted(true);
            return fileMetadataRepository.save(metadata);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store lab result file", e);
        }
    }
}
