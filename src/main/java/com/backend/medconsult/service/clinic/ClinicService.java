package com.backend.medconsult.service.clinic;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinic.ClinicBranchRequestDto;
import com.backend.medconsult.dto.clinic.ClinicBranchResponseDto;
import com.backend.medconsult.dto.clinic.ClinicInsuranceRequestDto;
import com.backend.medconsult.dto.clinic.ClinicInsuranceResponseDto;
import com.backend.medconsult.dto.clinic.ClinicLanguageResponseDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourRequestDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourResponseDto;
import com.backend.medconsult.dto.clinic.ClinicRequestDto;
import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.dto.clinic.ClinicSpecialtyResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface ClinicService {

    List<ClinicResponseDto> getAllClinics();

    ClinicResponseDto getClinicById(UUID id);

    ClinicResponseDto createClinic(ClinicRequestDto dto, MultipartFile logo, CustomUserPrincipal principal);

    ClinicResponseDto updateClinic(UUID id, ClinicRequestDto dto, MultipartFile logo, CustomUserPrincipal principal);

    // ClinicResponseDto verifyClinic(UUID id, CustomUserPrincipal principal);

    List<ClinicBranchResponseDto> getClinicBranches(UUID id);

    ClinicBranchResponseDto createClinicBranch(UUID id, ClinicBranchRequestDto dto, CustomUserPrincipal principal);

    ClinicBranchResponseDto updateClinicBranch(UUID id, ClinicBranchRequestDto dto, CustomUserPrincipal principal);

    void deleteClinicBranch(UUID id, CustomUserPrincipal principal);

    List<ClinicOperatingHourResponseDto> updateBranchHours(UUID id, List<ClinicOperatingHourRequestDto> dtos,
            CustomUserPrincipal principal);

    List<ClinicOperatingHourResponseDto> getBranchHours(UUID branchId);

    ClinicSpecialtyResponseDto addClinicSpecialty(UUID id, UUID specialtyId, CustomUserPrincipal principal);

    // void deleteClinicSpecialty(UUID id, UUID specialtyId, CustomUserPrincipal principal);

    ClinicInsuranceResponseDto addClinicInsurance(UUID id, UUID providerId, ClinicInsuranceRequestDto dto,
            CustomUserPrincipal principal);

    // void deleteClinicInsurance(UUID id, UUID providerId, CustomUserPrincipal principal);

    ClinicLanguageResponseDto addClinicLanguage(UUID id, UUID languageId, CustomUserPrincipal principal);

    // void deleteClinicLanguage(UUID id, UUID languageId, CustomUserPrincipal principal);

}
