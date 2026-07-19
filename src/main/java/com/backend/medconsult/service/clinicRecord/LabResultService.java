package com.backend.medconsult.service.clinicRecord;

import com.backend.medconsult.dto.clinicRecord.LabItemRequestDto;
import com.backend.medconsult.dto.clinicRecord.LabItemResponseDto;
import com.backend.medconsult.dto.clinicRecord.LabResultRequestDto;
import com.backend.medconsult.dto.clinicRecord.LabResultResponseDto;
import com.backend.medconsult.enums.clinicalRecords.LabResultStatus;
import com.backend.medconsult.enums.clinicalRecords.ResultFlag;
import com.backend.medconsult.security.CustomUserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface LabResultService {

    Page<LabResultResponseDto> searchLabResults(UUID patientId, UUID orderedById, LabResultStatus status, ResultFlag overallFlag, int page, int size, String sortBy, String sortDir);

    LabResultResponseDto getLabResultById(UUID id);

    LabResultResponseDto createLabResult(LabResultRequestDto dto, MultipartFile file, CustomUserPrincipal principal);

    LabResultResponseDto updateLabResult(UUID id, LabResultRequestDto dto, MultipartFile file, CustomUserPrincipal principal);

    void deleteLabResult(UUID id, CustomUserPrincipal principal);

    List<LabItemResponseDto> getLabItems(UUID labResultId);

    LabItemResponseDto addLabItem(UUID labResultId, LabItemRequestDto dto, CustomUserPrincipal principal);

    LabItemResponseDto updateLabItem(UUID itemId, LabItemRequestDto dto, CustomUserPrincipal principal);

    void deleteLabItem(UUID itemId, CustomUserPrincipal principal);
}
