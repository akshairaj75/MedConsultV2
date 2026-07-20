package com.backend.medconsult.controller.references;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.references.SpecialtyRequestDto;
import com.backend.medconsult.dto.references.SpecialtyResponseDto;
import com.backend.medconsult.dto.references.SubSpecialtyRequestDto;
import com.backend.medconsult.dto.references.SubSpecialtyResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.references.SpecialtyService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/medconsult/specialties")
public class SpecialtyController {

    @Autowired
    SpecialtyService specialtyService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<SpecialtyResponseDto> addSpecialty(@RequestBody SpecialtyRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        SpecialtyResponseDto specialty = specialtyService.addSpecialty(dto, authUser, request);
        return ResponseEntity.ok(specialty);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SpecialtyResponseDto>> getAllSpecialties() {
        List<SpecialtyResponseDto> specialties = specialtyService.getAllSpecialties();
        return ResponseEntity.ok(specialties);
    }

    @GetMapping("/{specialityId}/sub-specialities")
    public ResponseEntity<List<SubSpecialtyResponseDto>> getAllSubSpecialties(
            @PathVariable UUID specialityId) {
        List<SubSpecialtyResponseDto> subSpecialties = specialtyService.getAllSubSpecialties(specialityId);
        return ResponseEntity.ok(subSpecialties);
    }

    @PatchMapping("/{specialityId}/edit")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<SpecialtyResponseDto> updateSpecialty(@PathVariable UUID specialityId, @RequestBody SpecialtyRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        SpecialtyResponseDto specialty = specialtyService.updateSpecialty(specialityId, dto, authUser, request);
        return ResponseEntity.ok(specialty);
    }

    @DeleteMapping("/{specialityId}/delete")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<String> deleteSpecialty(@PathVariable UUID specialityId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        String message = specialtyService.deleteSpecialty(specialityId, authUser, request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/sub/add")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<SubSpecialtyResponseDto> addSubSpecialty(@RequestBody SubSpecialtyRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        SubSpecialtyResponseDto subSpecialty = specialtyService.addSubSpecialty(dto, authUser, request);
        return ResponseEntity.ok(subSpecialty);
    }

    @PatchMapping("/sub/{subSpecialtyId}/edit")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<SubSpecialtyResponseDto> updateSubSpecialty(@PathVariable UUID subSpecialtyId, @RequestBody SubSpecialtyRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        SubSpecialtyResponseDto subSpecialty = specialtyService.updateSubSpecialty(subSpecialtyId, dto, authUser, request);
        return ResponseEntity.ok(subSpecialty);
    }

    @DeleteMapping("/sub/{subSpecialtyId}/delete")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public ResponseEntity<String> deleteSubSpecialty(@PathVariable UUID subSpecialtyId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        String message = specialtyService.deleteSubSpecialty(subSpecialtyId, authUser, request);
        return ResponseEntity.ok(message);
    }



}
