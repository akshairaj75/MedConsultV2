package com.backend.medconsult.controller.consultation;

import com.backend.medconsult.dto.consultation.ConsultationMessageRequestDto;
import com.backend.medconsult.dto.consultation.ConsultationMessageResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.consultation.ConsultationMessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/consultations/messages")
public class ConsultationMessageController {

    @Autowired
    private ConsultationMessageService consultationMessageService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<ConsultationMessageResponseDto> sendMessage(
            @Valid @RequestBody ConsultationMessageRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        ConsultationMessageResponseDto response = consultationMessageService.sendMessage(dto, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/consultation/{consultationId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<List<ConsultationMessageResponseDto>> getMessagesForConsultation(
            @PathVariable("consultationId") UUID consultationId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        List<ConsultationMessageResponseDto> response = consultationMessageService.getMessagesForConsultation(consultationId, authUser, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{messageId}/read")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<ConsultationMessageResponseDto> markAsRead(
            @PathVariable("messageId") UUID messageId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        ConsultationMessageResponseDto response = consultationMessageService.markAsRead(messageId, authUser, request);
        return ResponseEntity.ok(response);
    }
}
