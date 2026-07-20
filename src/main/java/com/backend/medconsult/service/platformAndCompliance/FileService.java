package com.backend.medconsult.service.platformAndCompliance;

import com.backend.medconsult.dto.platformAndCompliance.FileMetadataResponseDto;
import com.backend.medconsult.dto.platformAndCompliance.FileUploadRequestDto;
import com.backend.medconsult.enums.platformAndCompliance.FileCategory;
import com.backend.medconsult.security.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    FileMetadataResponseDto uploadFile(MultipartFile file, FileUploadRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    FileMetadataResponseDto getFileMetadata(UUID fileId, CustomUserPrincipal authUser, HttpServletRequest request);

    Resource downloadFile(UUID fileId, CustomUserPrincipal authUser, HttpServletRequest request);

    Page<FileMetadataResponseDto> getFilesByPatient(UUID patientId, FileCategory category, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request);

    void softDeleteFile(UUID fileId, CustomUserPrincipal authUser, HttpServletRequest request);
}
