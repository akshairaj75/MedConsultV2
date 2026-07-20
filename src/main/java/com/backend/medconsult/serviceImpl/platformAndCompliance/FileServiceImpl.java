package com.backend.medconsult.serviceImpl.platformAndCompliance;

import com.backend.medconsult.dto.platformAndCompliance.FileMetadataResponseDto;
import com.backend.medconsult.dto.platformAndCompliance.FileUploadRequestDto;
import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.FileCategory;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.platformAndCompliance.FileMetadataRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import com.backend.medconsult.service.platformAndCompliance.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AccessLogService accessLogService;

    @Transactional
    @Override
    public FileMetadataResponseDto uploadFile(MultipartFile file, FileUploadRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {
        User uploader = authUser.getUser();
        Patient patient = null;

        if (dto.getPatientId() != null) {
            patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));
        }

        FileMetadata metadata = new FileMetadata();
        metadata.setUploadedBy(uploader);
        metadata.setOriginalFilename(file.getOriginalFilename());
        metadata.setMimeType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
        metadata.setSizeBytes(file.getSize());
        metadata.setCategory(dto.getCategory() != null ? dto.getCategory() : FileCategory.OTHER);
        metadata.setPatient(patient);
        metadata.setIsEncrypted(true);
        metadata.setStorageKey("mocked-storage-path/" + UUID.randomUUID() + "-" + file.getOriginalFilename());

        try {
            metadata.setChecksumSha256(calculateChecksum(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file bytes for checksum calculation");
        }

        // Simulating the actual file storage process here
        // If this were an S3 integration, we'd upload the file.getBytes() to the storageKey path.

        FileMetadata saved = fileMetadataRepository.save(metadata);

        accessLogService.log(
                uploader,
                patient,
                AuditAction.CREATE,
                ResourceType.FILE,
                null,
                AuditOutcome.SUCCESS);

        return FileMetadataResponseDto.fromEntity(saved);
    }

    @Override
    public FileMetadataResponseDto getFileMetadata(UUID fileId, CustomUserPrincipal authUser, HttpServletRequest request) {
        FileMetadata metadata = findFileOrThrow(fileId);

        accessLogService.log(
                authUser.getUser(),
                metadata.getPatient(),
                AuditAction.VIEW,
                ResourceType.FILE,
                null,
                AuditOutcome.SUCCESS);

        return FileMetadataResponseDto.fromEntity(metadata);
    }

    @Override
    public Resource downloadFile(UUID fileId, CustomUserPrincipal authUser, HttpServletRequest request) {
        FileMetadata metadata = findFileOrThrow(fileId);

        // Simulation: Since we don't have an actual S3 bucket, we return a mock ByteArrayResource 
        // representing the file contents. In reality, we'd stream from metadata.getStorageKey()
        byte[] mockFileContent = ("Mock file content for " + metadata.getOriginalFilename()).getBytes();
        Resource resource = new ByteArrayResource(mockFileContent);

        accessLogService.log(
                authUser.getUser(),
                metadata.getPatient(),
                AuditAction.DOWNLOAD,
                ResourceType.FILE,
                null,
                AuditOutcome.SUCCESS);

        return resource;
    }

    @Override
    public Page<FileMetadataResponseDto> getFilesByPatient(UUID patientId, FileCategory category, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<FileMetadata> filesPage;

        if (category != null) {
            filesPage = fileMetadataRepository.findByPatient_PatientIdAndCategory(patientId, category, pageable);
        } else {
            filesPage = fileMetadataRepository.findByPatient_PatientId(patientId, pageable);
        }

        accessLogService.log(
                authUser.getUser(),
                patient,
                AuditAction.VIEW,
                ResourceType.FILE,
                null,
                AuditOutcome.SUCCESS);

        return filesPage.map(FileMetadataResponseDto::fromEntity);
    }

    @Transactional
    @Override
    public void softDeleteFile(UUID fileId, CustomUserPrincipal authUser, HttpServletRequest request) {
        FileMetadata metadata = findFileOrThrow(fileId);

        fileMetadataRepository.delete(metadata);

        accessLogService.log(
                authUser.getUser(),
                metadata.getPatient(),
                AuditAction.DELETE,
                ResourceType.FILE,
                null,
                AuditOutcome.SUCCESS);
    }

    private FileMetadata findFileOrThrow(UUID fileId) {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + fileId));
    }

    private String calculateChecksum(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes);
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available");
        }
    }
}
