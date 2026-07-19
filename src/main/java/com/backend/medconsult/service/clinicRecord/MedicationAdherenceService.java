package com.backend.medconsult.service.clinicRecord;

import com.backend.medconsult.dto.clinicRecord.MedicationAdherenceRequestDto;
import com.backend.medconsult.dto.clinicRecord.MedicationAdherenceResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MedicationAdherenceService {

    Page<MedicationAdherenceResponseDto> searchAdherence(UUID patientId, UUID rxItemId, int page, int size, String sortBy, String sortDir);

    MedicationAdherenceResponseDto getAdherenceById(UUID id);

    MedicationAdherenceResponseDto createAdherence(MedicationAdherenceRequestDto dto, CustomUserPrincipal principal);

    MedicationAdherenceResponseDto updateAdherence(UUID id, MedicationAdherenceRequestDto dto, CustomUserPrincipal principal);

    void deleteAdherence(UUID id, CustomUserPrincipal principal);
}
