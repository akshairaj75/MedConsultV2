package com.backend.medconsult.service.clinicRecord;

import com.backend.medconsult.dto.clinicRecord.PrescriptionItemRequestDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionItemResponseDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionRequestDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionResponseDto;
import com.backend.medconsult.enums.clinicalRecords.PrescriptionStatus;
import com.backend.medconsult.security.CustomUserPrincipal;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {

    Page<PrescriptionResponseDto> searchPrescriptions(UUID patientId, UUID doctorId, PrescriptionStatus status, int page, int size, String sortBy, String sortDir);

    PrescriptionResponseDto getPrescriptionById(UUID id);

    PrescriptionResponseDto createPrescription(PrescriptionRequestDto dto, CustomUserPrincipal principal);

    PrescriptionResponseDto updatePrescription(UUID id, PrescriptionRequestDto dto, CustomUserPrincipal principal);

    void deletePrescription(UUID id, CustomUserPrincipal principal);

    List<PrescriptionItemResponseDto> getPrescriptionItems(UUID prescriptionId);

    PrescriptionItemResponseDto addPrescriptionItem(UUID prescriptionId, PrescriptionItemRequestDto dto, CustomUserPrincipal principal);

    PrescriptionItemResponseDto updatePrescriptionItem(UUID itemId, PrescriptionItemRequestDto dto, CustomUserPrincipal principal);

    void deletePrescriptionItem(UUID itemId, CustomUserPrincipal principal);
}
