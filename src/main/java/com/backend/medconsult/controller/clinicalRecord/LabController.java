package com.backend.medconsult.controller.clinicalRecord;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinicRecord.LabItemRequestDto;
import com.backend.medconsult.dto.clinicRecord.LabItemResponseDto;
import com.backend.medconsult.dto.clinicRecord.LabResultRequestDto;
import com.backend.medconsult.dto.clinicRecord.LabResultResponseDto;
import com.backend.medconsult.enums.clinicalRecords.LabResultStatus;
import com.backend.medconsult.enums.clinicalRecords.ResultFlag;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.clinicRecord.LabResultService;

@RestController
@RequestMapping("/api/medconsult/lab-results")
public class LabController {

    @Autowired
    private LabResultService labResultService;

    @GetMapping("/search")
    public ResponseEntity<Page<LabResultResponseDto>> searchLabResults(
            @RequestParam(name = "patientId", required = false) UUID patientId,
            @RequestParam(name = "orderedById", required = false) UUID orderedById,
            @RequestParam(name = "status", required = false) LabResultStatus status,
            @RequestParam(name = "overallFlag", required = false) ResultFlag overallFlag,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "reportDate") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir) {

        return ResponseEntity.ok(labResultService.searchLabResults(
                patientId, orderedById, status, overallFlag, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabResultResponseDto> getLabResultById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(labResultService.getLabResultById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<LabResultResponseDto> createLabResult(
            @RequestPart("dto") LabResultRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(labResultService.createLabResult(dto, file, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabResultResponseDto> updateLabResult(
            @PathVariable("id") UUID id,
            @RequestPart("dto") LabResultRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(labResultService.updateLabResult(id, dto, file, principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabResult(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        labResultService.deleteLabResult(id, principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<LabItemResponseDto>> getLabItems(@PathVariable("id") UUID labResultId) {
        return ResponseEntity.ok(labResultService.getLabItems(labResultId));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<LabItemResponseDto> addLabItem(
            @PathVariable("id") UUID labResultId,
            @RequestBody LabItemRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(labResultService.addLabItem(labResultId, dto, principal));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<LabItemResponseDto> updateLabItem(
            @PathVariable("itemId") UUID itemId,
            @RequestBody LabItemRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(labResultService.updateLabItem(itemId, dto, principal));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteLabItem(
            @PathVariable("itemId") UUID itemId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        labResultService.deleteLabItem(itemId, principal);
        return ResponseEntity.noContent().build();
    }
}

