package com.backend.medconsult.service.clinic;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinic.ClinicBranchRequestDto;
import com.backend.medconsult.dto.clinic.ClinicBranchResponseDto;
import com.backend.medconsult.dto.clinic.ClinicDetailResponse;
import com.backend.medconsult.dto.clinic.ClinicInsuranceRequestDto;
import com.backend.medconsult.dto.clinic.ClinicInsuranceResponseDto;
import com.backend.medconsult.dto.clinic.ClinicLanguageResponseDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourRequestDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourResponseDto;
import com.backend.medconsult.dto.clinic.ClinicRequestDto;
import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.dto.clinic.ClinicSearchRequest;
import com.backend.medconsult.dto.clinic.ClinicSpecialtyResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.validation.Valid;

public interface ClinicService {

    // ── Core Clinic CRUD ───────────────────────────────────────────────

    Page<ClinicResponseDto> searchClinics(ClinicSearchRequest request);

    List<ClinicResponseDto> getAllClinics();

    ClinicResponseDto getClinicById(UUID id);

    ClinicDetailResponse getClinicDetail(UUID id);

    ClinicResponseDto createClinic(ClinicRequestDto dto, MultipartFile logo, CustomUserPrincipal principal);

    ClinicResponseDto updateClinic(UUID id, ClinicRequestDto dto, MultipartFile logo, CustomUserPrincipal principal);

    void deleteClinic(UUID id, CustomUserPrincipal principal);

    // ── Branches ───────────────────────────────────────────────────────

    List<ClinicBranchResponseDto> getClinicBranches(UUID clinicId);

    ClinicBranchResponseDto createClinicBranch(UUID clinicId, ClinicBranchRequestDto dto, CustomUserPrincipal principal);

    ClinicBranchResponseDto updateClinicBranch(UUID branchId, ClinicBranchRequestDto dto, CustomUserPrincipal principal);

    void deleteClinicBranch(UUID branchId, CustomUserPrincipal principal);

    // ── Operating Hours ────────────────────────────────────────────────

    List<ClinicOperatingHourResponseDto> getBranchHours(UUID branchId);

    List<ClinicOperatingHourResponseDto> updateBranchHours(UUID branchId, List<ClinicOperatingHourRequestDto> dtos,
            CustomUserPrincipal principal);

    // ── Specialties ────────────────────────────────────────────────────

    List<ClinicSpecialtyResponseDto> getClinicSpecialties(UUID clinicId);

    ClinicSpecialtyResponseDto addClinicSpecialty(UUID clinicId, UUID specialtyId, CustomUserPrincipal principal);

    void deleteClinicSpecialty(UUID clinicId, UUID specialtyId, CustomUserPrincipal principal);

    // ── Insurance ──────────────────────────────────────────────────────

    List<ClinicInsuranceResponseDto> getClinicInsurances(UUID clinicId);

    ClinicInsuranceResponseDto addClinicInsurance(UUID clinicId, UUID providerId, ClinicInsuranceRequestDto dto,
            CustomUserPrincipal principal);

    void deleteClinicInsurance(UUID clinicId, UUID providerId, CustomUserPrincipal principal);

    // ── Languages ──────────────────────────────────────────────────────

    List<ClinicLanguageResponseDto> getClinicLanguages(UUID clinicId);

    ClinicLanguageResponseDto addClinicLanguage(UUID clinicId, UUID languageId, CustomUserPrincipal principal);

    void deleteClinicLanguage(UUID clinicId, UUID languageId, CustomUserPrincipal principal);

    ClinicResponseDto registerClinic(ClinicRequestDto dto, MultipartFile logo, CustomUserPrincipal principal);
}
