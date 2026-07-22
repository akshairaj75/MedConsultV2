package com.backend.medconsult.controller.consultation;

import com.backend.medconsult.dto.consultation.ConsultationRequestDto;
import com.backend.medconsult.dto.consultation.ConsultationResponseDto;
import com.backend.medconsult.dto.consultation.ConsultationSearchRequest;
import com.backend.medconsult.dto.consultation.UpdateConsultationStatusRequest;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.consultation.ConsultationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @PostMapping("/book")
    @PreAuthorize("hasAnyRole('PATIENT', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ConsultationResponseDto> openConsultation(
            @Valid @RequestBody ConsultationRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        ConsultationResponseDto response = consultationService.openConsultation(dto, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ConsultationResponseDto> getConsultationById(
            @PathVariable("id") UUID consultationId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        ConsultationResponseDto response = consultationService.getConsultationById(consultationId, authUser, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my/doctor")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<ConsultationResponseDto>> getMyDoctorConsultations(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Page<ConsultationResponseDto> result = consultationService.getMyDoctorConsultations(authUser, page, size, request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<ConsultationResponseDto>> searchConsultations(
            @RequestBody ConsultationSearchRequest searchRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<ConsultationResponseDto> page = consultationService.searchConsultations(searchRequest, authUser, request);
        return ResponseEntity.ok(page);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<ConsultationResponseDto> updateStatus(
            @PathVariable("id") UUID consultationId,
            @Valid @RequestBody UpdateConsultationStatusRequest statusRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        ConsultationResponseDto response = consultationService.updateStatus(consultationId, statusRequest, authUser, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<ConsultationResponseDto>> getConsultationsByPatient(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<ConsultationResponseDto> result = consultationService.getConsultationsByPatient(patientId, page, size, authUser, request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<ConsultationResponseDto>> getConsultationsByDoctor(
            @PathVariable UUID doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<ConsultationResponseDto> result = consultationService.getConsultationsByDoctor(doctorId, page, size, authUser, request);
        return ResponseEntity.ok(result);
    }
}
