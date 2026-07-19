package com.backend.medconsult.controller.clinic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.clinic.ClinicBranchResponseDto;
import com.backend.medconsult.dto.clinic.ClinicDetailResponse;
import com.backend.medconsult.dto.clinic.ClinicInsuranceResponseDto;
import com.backend.medconsult.dto.clinic.ClinicLanguageResponseDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourResponseDto;
import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.dto.clinic.ClinicSearchRequest;
import com.backend.medconsult.dto.clinic.ClinicSpecialtyResponseDto;
import com.backend.medconsult.service.clinic.ClinicService;

/**
 * Public read-only endpoints for the Clinic module.
 * Base URL: /api/medconsult/clinics
 */
@RestController
@RequestMapping("/api/medconsult/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    // ── Search / List ──────────────────────────────────────────────────

    /**
     * GET /api/medconsult/clinics/search
     * Paginated, filterable clinic search.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ClinicResponseDto>> searchClinics(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "specialtyId", required = false) UUID specialtyId,
            @RequestParam(name = "isActive", required = false) Boolean isActive,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "nameEn") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {

        ClinicSearchRequest request = new ClinicSearchRequest();
        request.setName(name);
        request.setSpecialtyId(specialtyId);
        request.setIsActive(isActive);
        request.setPage(page);
        request.setSize(size);
        request.setSortBy(sortBy);
        request.setSortDir(sortDir);

        return ResponseEntity.ok(clinicService.searchClinics(request));
    }

    /**
     * GET /api/medconsult/clinics/all
     * List all clinics (no pagination).
     */
    @GetMapping("/all")
    public ResponseEntity<List<ClinicResponseDto>> getAllClinics() {
        return ResponseEntity.ok(clinicService.getAllClinics());
    }

    // ── Single Clinic ──────────────────────────────────────────────────

    /**
     * GET /api/medconsult/clinics/{id}
     * Lightweight clinic summary.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDto> getClinicById(
            @PathVariable("id") UUID id) {
        return ResponseEntity.ok(clinicService.getClinicById(id));
    }

    /**
     * GET /api/medconsult/clinics/{id}/detail
     * Full clinic profile with branches, specialties, languages, and insurances.
     */
    @GetMapping("/{id}/detail")
    public ResponseEntity<ClinicDetailResponse> getClinicDetail(
            @PathVariable("id") UUID id) {
        return ResponseEntity.ok(clinicService.getClinicDetail(id));
    }

    // ── Branches ───────────────────────────────────────────────────────

    /**
     * GET /api/medconsult/clinics/{id}/branches
     */
    @GetMapping("/{id}/branches")
    public ResponseEntity<List<ClinicBranchResponseDto>> getClinicBranches(
            @PathVariable("id") UUID id) {
        return ResponseEntity.ok(clinicService.getClinicBranches(id));
    }

    /**
     * GET /api/medconsult/clinics/branches/{branchId}/hours
     */
    @GetMapping("/branches/{branchId}/hours")
    public ResponseEntity<List<ClinicOperatingHourResponseDto>> getBranchHours(
            @PathVariable("branchId") UUID branchId) {
        return ResponseEntity.ok(clinicService.getBranchHours(branchId));
    }

    // ── Sub-Resources (read-only) ──────────────────────────────────────

    /**
     * GET /api/medconsult/clinics/{id}/specialties
     */
    @GetMapping("/{id}/specialties")
    public ResponseEntity<List<ClinicSpecialtyResponseDto>> getClinicSpecialties(
            @PathVariable("id") UUID id) {
        return ResponseEntity.ok(clinicService.getClinicSpecialties(id));
    }

    /**
     * GET /api/medconsult/clinics/{id}/insurance
     */
    @GetMapping("/{id}/insurance")
    public ResponseEntity<List<ClinicInsuranceResponseDto>> getClinicInsurances(
            @PathVariable("id") UUID id) {
        return ResponseEntity.ok(clinicService.getClinicInsurances(id));
    }

    /**
     * GET /api/medconsult/clinics/{id}/languages
     */
    @GetMapping("/{id}/languages")
    public ResponseEntity<List<ClinicLanguageResponseDto>> getClinicLanguages(
            @PathVariable("id") UUID id) {
        return ResponseEntity.ok(clinicService.getClinicLanguages(id));
    }
}
