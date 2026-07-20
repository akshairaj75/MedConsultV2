package com.backend.medconsult.controller.platformAndCompliance;

import com.backend.medconsult.dto.platformAndCompliance.FileMetadataResponseDto;
import com.backend.medconsult.dto.platformAndCompliance.FileUploadRequestDto;
import com.backend.medconsult.enums.platformAndCompliance.FileCategory;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.platformAndCompliance.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<FileMetadataResponseDto> uploadFile(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute FileUploadRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        FileMetadataResponseDto response = fileService.uploadFile(file, dto, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/metadata")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<FileMetadataResponseDto> getFileMetadata(
            @PathVariable("id") UUID fileId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        FileMetadataResponseDto response = fileService.getFileMetadata(fileId, authUser, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("id") UUID fileId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Resource resource = fileService.downloadFile(fileId, authUser, request);

        // Fetch metadata to properly set the filename and mime type in the download header
        FileMetadataResponseDto metadata = fileService.getFileMetadata(fileId, authUser, request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getOriginalFilename() + "\"")
                .contentType(MediaType.parseMediaType(metadata.getMimeType()))
                .body(resource);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<FileMetadataResponseDto>> getFilesByPatient(
            @PathVariable UUID patientId,
            @RequestParam(required = false) FileCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<FileMetadataResponseDto> response = fileService.getFilesByPatient(patientId, category, page, size, authUser, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Void> deleteFile(
            @PathVariable("id") UUID fileId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        fileService.softDeleteFile(fileId, authUser, request);
        return ResponseEntity.noContent().build();
    }
}
