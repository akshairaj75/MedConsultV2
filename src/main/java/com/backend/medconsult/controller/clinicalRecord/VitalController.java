package com.backend.medconsult.controller.clinicalRecord;

import com.backend.medconsult.dto.clinicRecord.VitalRequestDto;
import com.backend.medconsult.dto.clinicRecord.VitalResponseDto;
import com.backend.medconsult.enums.clinicalRecords.VitalSource;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.clinicRecord.VitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/vitals")
public class VitalController {

    @Autowired
    private VitalService vitalService;

    @GetMapping("/search")
    public ResponseEntity<Page<VitalResponseDto>> searchVitals(
            @RequestParam(name = "patientId", required = false) UUID patientId,
            @RequestParam(name = "source", required = false) VitalSource source,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "recordedAt") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "desc") String sortDir) {

        return ResponseEntity.ok(vitalService.searchVitals(
                patientId, source, page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VitalResponseDto> getVitalById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(vitalService.getVitalById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<VitalResponseDto> createVital(
            @RequestBody VitalRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(vitalService.createVital(dto, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VitalResponseDto> updateVital(
            @PathVariable("id") UUID id,
            @RequestBody VitalRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return ResponseEntity.ok(vitalService.updateVital(id, dto, principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVital(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        vitalService.deleteVital(id, principal);
        return ResponseEntity.noContent().build();
    }
}
