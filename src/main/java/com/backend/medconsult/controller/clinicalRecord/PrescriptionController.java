package com.backend.medconsult.controller.clinicalRecord;

import com.backend.medconsult.dto.clinicRecord.PrescriptionItemRequestDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionItemResponseDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionRequestDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionResponseDto;
import com.backend.medconsult.enums.clinicalRecords.PrescriptionStatus;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.clinicRecord.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/search")
    public ResponseEntity<Page<PrescriptionResponseDto>> searchPrescriptions(
            @RequestParam(name = "patientId", required = false) UUID patientId,
            @RequestParam(name = "doctorId", required = false) UUID doctorId,
            @RequestParam(name = "status", required = false) PrescriptionStatus status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "issuedDate") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir) {

        return ResponseEntity.ok(prescriptionService.searchPrescriptions(
                patientId, doctorId, status, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDto> getPrescriptionById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<PrescriptionResponseDto> createPrescription(
            @RequestBody PrescriptionRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(prescriptionService.createPrescription(dto, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDto> updatePrescription(
            @PathVariable("id") UUID id,
            @RequestBody PrescriptionRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(prescriptionService.updatePrescription(id, dto, principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        prescriptionService.deletePrescription(id, principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<PrescriptionItemResponseDto>> getPrescriptionItems(@PathVariable("id") UUID prescriptionId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionItems(prescriptionId));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<PrescriptionItemResponseDto> addPrescriptionItem(
            @PathVariable("id") UUID prescriptionId,
            @RequestBody PrescriptionItemRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(prescriptionService.addPrescriptionItem(prescriptionId, dto, principal));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<PrescriptionItemResponseDto> updatePrescriptionItem(
            @PathVariable("itemId") UUID itemId,
            @RequestBody PrescriptionItemRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(prescriptionService.updatePrescriptionItem(itemId, dto, principal));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deletePrescriptionItem(
            @PathVariable("itemId") UUID itemId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        prescriptionService.deletePrescriptionItem(itemId, principal);
        return ResponseEntity.noContent().build();
    }
}
