package com.backend.medconsult.controller.clinicalRecord;

import com.backend.medconsult.dto.clinicRecord.MedicationAdherenceRequestDto;
import com.backend.medconsult.dto.clinicRecord.MedicationAdherenceResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.clinicRecord.MedicationAdherenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/adherence")
public class MedicationAdherenceController {

    @Autowired
    private MedicationAdherenceService medicationAdherenceService;

    @GetMapping("/search")
    public ResponseEntity<Page<MedicationAdherenceResponseDto>> searchAdherence(
            @RequestParam(name = "patientId", required = false) UUID patientId,
            @RequestParam(name = "rxItemId", required = false) UUID rxItemId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "logDate") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir) {

        return ResponseEntity.ok(medicationAdherenceService.searchAdherence(
                patientId, rxItemId, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationAdherenceResponseDto> getAdherenceById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(medicationAdherenceService.getAdherenceById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<MedicationAdherenceResponseDto> createAdherence(
            @RequestBody MedicationAdherenceRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(medicationAdherenceService.createAdherence(dto, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationAdherenceResponseDto> updateAdherence(
            @PathVariable("id") UUID id,
            @RequestBody MedicationAdherenceRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(medicationAdherenceService.updateAdherence(id, dto, principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdherence(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        medicationAdherenceService.deleteAdherence(id, principal);
        return ResponseEntity.noContent().build();
    }
}
