package com.backend.medconsult.controller.clinic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
import com.backend.medconsult.service.clinic.ClinicService;

import jakarta.validation.Valid;

/**
 * Admin-only write endpoints for the Clinic module.
 * Base URL: /api/medconsult/clinics
 *
 * All endpoints require CLINIC_ADMIN or SYSTEM_ADMIN role unless noted.
 */
@RestController
@RequestMapping("/api/medconsult/clinics")
public class ClinicAdminController {

    @Autowired
    private ClinicService clinicService;

    // ══════════════════════════════════════════════════════════════════
    // ─── Core Clinic CRUD ─────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ClinicResponseDto> createClinic(
            @RequestPart("body") @Valid ClinicRequestDto dto,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicService.createClinic(dto, logo, principal));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ClinicResponseDto> updateClinic(
            @PathVariable("id") UUID id,
            @RequestPart("body") ClinicRequestDto dto,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.updateClinic(id, dto, logo, principal));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<Void> deleteClinic(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        clinicService.deleteClinic(id, principal);
        return ResponseEntity.noContent().build();
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Branches ─────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @PostMapping("/{id}/branches")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ClinicBranchResponseDto> createClinicBranch(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ClinicBranchRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicService.createClinicBranch(id, dto, principal));
    }

    @PatchMapping("/branches/{id}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ClinicBranchResponseDto> updateClinicBranch(
            @PathVariable("id") UUID id,
            @RequestBody ClinicBranchRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.updateClinicBranch(id, dto, principal));
    }

    @DeleteMapping("/branches/{id}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Void> deleteClinicBranch(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        clinicService.deleteClinicBranch(id, principal);
        return ResponseEntity.noContent().build();
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Operating Hours ──────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @PutMapping("/branches/{id}/hours")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<List<ClinicOperatingHourResponseDto>> updateBranchHours(
            @PathVariable("id") UUID id,
            @RequestBody List<ClinicOperatingHourRequestDto> dtos,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.updateBranchHours(id, dtos, principal));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Specialties ──────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    /**
     * POST /api/medconsult/clinics/{id}/specialties/{specialtyId}
     * Link a specialty to a clinic.
     */
    @PostMapping("/{id}/specialties/{specialtyId}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ClinicSpecialtyResponseDto> addClinicSpecialty(
            @PathVariable("id") UUID id,
            @PathVariable("specialtyId") UUID specialtyId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicService.addClinicSpecialty(id, specialtyId, principal));
    }

    /**
     * DELETE /api/medconsult/clinics/{id}/specialties/{specialtyId}
     * Unlink a specialty from a clinic.
     */
    @DeleteMapping("/{id}/specialties/{specialtyId}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Void> deleteClinicSpecialty(
            @PathVariable("id") UUID id,
            @PathVariable("specialtyId") UUID specialtyId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        clinicService.deleteClinicSpecialty(id, specialtyId, principal);
        return ResponseEntity.noContent().build();
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Insurance ────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    /**
     * POST /api/medconsult/clinics/{id}/insurance/{providerId}
     */
    @PostMapping("/{id}/insurance/{providerId}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ClinicInsuranceResponseDto> addClinicInsurance(
            @PathVariable("id") UUID id,
            @PathVariable("providerId") UUID providerId,
            @RequestBody(required = false) ClinicInsuranceRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicService.addClinicInsurance(id, providerId, dto, principal));
    }

    /**
     * DELETE /api/medconsult/clinics/{id}/insurance/{providerId}
     */
    @DeleteMapping("/{id}/insurance/{providerId}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Void> deleteClinicInsurance(
            @PathVariable("id") UUID id,
            @PathVariable("providerId") UUID providerId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        clinicService.deleteClinicInsurance(id, providerId, principal);
        return ResponseEntity.noContent().build();
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Languages ────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    /**
     * POST /api/medconsult/clinics/{id}/languages/{languageId}
     */
    @PostMapping("/{id}/languages/{languageId}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ClinicLanguageResponseDto> addClinicLanguage(
            @PathVariable("id") UUID id,
            @PathVariable("languageId") UUID languageId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicService.addClinicLanguage(id, languageId, principal));
    }

    /**
     * DELETE /api/medconsult/clinics/{id}/languages/{languageId}
     */
    @DeleteMapping("/{id}/languages/{languageId}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Void> deleteClinicLanguage(
            @PathVariable("id") UUID id,
            @PathVariable("languageId") UUID languageId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        clinicService.deleteClinicLanguage(id, languageId, principal);
        return ResponseEntity.noContent().build();
    }
}
