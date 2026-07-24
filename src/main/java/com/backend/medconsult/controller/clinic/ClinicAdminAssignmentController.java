package com.backend.medconsult.controller.clinic;

import com.backend.medconsult.dto.clinic.ClinicAdminRequestDto;
import com.backend.medconsult.dto.clinic.ClinicAdminResponseDto;
import com.backend.medconsult.service.clinic.ClinicAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clinic-admins")
public class ClinicAdminAssignmentController {

    @Autowired
    private ClinicAdminService clinicAdminService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<ClinicAdminResponseDto> assignAdmin(@Valid @RequestBody ClinicAdminRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicAdminService.assignAdmin(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<Void> removeAdmin(@PathVariable("id") UUID id) {
        clinicAdminService.removeAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clinic/{clinicId}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN')")
    public ResponseEntity<List<ClinicAdminResponseDto>> getClinicAdmins(@PathVariable("clinicId") UUID clinicId) {
        return ResponseEntity.ok(clinicAdminService.getClinicAdmins(clinicId));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN')")
    public ResponseEntity<List<ClinicAdminResponseDto>> getManagedClinics(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(clinicAdminService.getManagedClinics(userId));
    }

    @PutMapping("/{clinicId}/primary/{userId}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<Void> changePrimaryAdmin(
            @PathVariable("clinicId") UUID clinicId,
            @PathVariable("userId") UUID userId) {
        clinicAdminService.changePrimaryAdmin(clinicId, userId);
        return ResponseEntity.noContent().build();
    }
}
