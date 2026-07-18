package com.backend.medconsult.controller.clinic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping
public class ClinicAdminController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping("/api/clinics")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicResponseDto> createClinic(
            @RequestPart("body") ClinicRequestDto dto,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.createClinic(dto, logo, principal));
    }

    @PatchMapping("/api/clinics/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicResponseDto> updateClinic(
            @PathVariable UUID id,
            @RequestPart("body") ClinicRequestDto dto,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.updateClinic(id, dto,logo, principal));
    }

    // @PatchMapping("/api/admin/clinics/{id}/verify")
    // @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    // public ResponseEntity<ClinicResponseDto> verifyClinic(
    //         @PathVariable UUID id,
    //         @AuthenticationPrincipal CustomUserPrincipal principal) {
    //     return ResponseEntity.ok(clinicService.verifyClinic(id, principal));
    // }

    @PostMapping("/api/clinics/{id}/branches")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicBranchResponseDto> createClinicBranch(
            @PathVariable UUID id,
            @RequestBody ClinicBranchRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.createClinicBranch(id, dto, principal));
    }

    @PatchMapping("/api/branches/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicBranchResponseDto> updateClinicBranch(
            @PathVariable UUID id,
            @RequestBody ClinicBranchRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.updateClinicBranch(id, dto, principal));
    }

    @DeleteMapping("/api/branches/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<Void> deleteClinicBranch(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        clinicService.deleteClinicBranch(id, principal);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/branches/{id}/hours")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<List<ClinicOperatingHourResponseDto>> updateBranchHours(
            @PathVariable UUID id,
            @RequestBody List<ClinicOperatingHourRequestDto> dtos,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.updateBranchHours(id, dtos, principal));
    }

    @PostMapping("/api/clinics/{id}/specialties/{specialtyId}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicSpecialtyResponseDto> addClinicSpecialty(
            @PathVariable UUID clinicId,
            @PathVariable UUID specialtyId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.addClinicSpecialty(clinicId, specialtyId, principal));
    }

    // @DeleteMapping("/api/clinics/{id}/specialties/{specialtyId}")
    // @PreAuthorize("hasRole('CLINIC_ADMIN')")
    // public ResponseEntity<Void> deleteClinicSpecialty(
    //         @PathVariable UUID id,
    //         @PathVariable UUID specialtyId,
    //         @AuthenticationPrincipal CustomUserPrincipal principal) {
    //     clinicService.deleteClinicSpecialty(id, specialtyId, principal);
    //     return ResponseEntity.noContent().build();
    // }

    @PostMapping("/api/clinics/{id}/insurance/{providerId}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicInsuranceResponseDto> addClinicInsurance(
            @PathVariable UUID id,
            @PathVariable UUID providerId,
            @RequestBody(required = false) ClinicInsuranceRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.addClinicInsurance(id, providerId, dto, principal));
    }

    // @DeleteMapping("/api/clinics/{id}/insurance/{providerId}")
    // @PreAuthorize("hasRole('CLINIC_ADMIN')")
    // public ResponseEntity<Void> deleteClinicInsurance(
    //         @PathVariable UUID id,
    //         @PathVariable UUID providerId,
    //         @AuthenticationPrincipal CustomUserPrincipal principal) {
    //     clinicService.deleteClinicInsurance(id, providerId, principal);
    //     return ResponseEntity.noContent().build();
    // }

    @PostMapping("/api/clinics/{id}/languages/{languageId}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<ClinicLanguageResponseDto> addClinicLanguage(
            @PathVariable UUID id,
            @PathVariable UUID languageId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(clinicService.addClinicLanguage(id, languageId, principal));
    }

    // @DeleteMapping("/api/clinics/{id}/languages/{languageId}")
    // @PreAuthorize("hasRole('CLINIC_ADMIN')")
    // public ResponseEntity<Void> deleteClinicLanguage(
    //         @PathVariable UUID id,
    //         @PathVariable UUID languageId,
    //         @AuthenticationPrincipal CustomUserPrincipal principal) {
    //     clinicService.deleteClinicLanguage(id, languageId, principal);
    //     return ResponseEntity.noContent().build();
    // }
}
