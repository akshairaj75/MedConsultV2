package com.backend.medconsult.service.clinicRecord;

import com.backend.medconsult.dto.clinicRecord.VitalRequestDto;
import com.backend.medconsult.dto.clinicRecord.VitalResponseDto;
import com.backend.medconsult.enums.clinicalRecords.VitalSource;
import com.backend.medconsult.security.CustomUserPrincipal;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface VitalService {

    Page<VitalResponseDto> searchVitals(UUID patientId, VitalSource source, int page, int size, String sortBy, String sortDir);

    VitalResponseDto getVitalById(UUID id);

    VitalResponseDto createVital(VitalRequestDto dto, CustomUserPrincipal principal);

    VitalResponseDto updateVital(UUID id, VitalRequestDto dto, CustomUserPrincipal principal);

    void deleteVital(UUID id, CustomUserPrincipal principal);
}
