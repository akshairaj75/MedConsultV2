package com.backend.medconsult.controller.doctor;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.doctor.DoctorDetailResponse;
import com.backend.medconsult.dto.doctor.DoctorRequestDto;
import com.backend.medconsult.dto.doctor.DoctorResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.Doctor.DoctorService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/medconsult/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        List<DoctorResponseDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @PostMapping("/add")
    public ResponseEntity<DoctorResponseDto> addDoctor(
        @RequestBody DoctorRequestDto dto,
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request) {
        DoctorResponseDto doctor = doctorService.addDoctor(dto, authUser, request);
        return ResponseEntity.ok(doctor);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<DoctorResponseDto> updateDoctor(
        @PathVariable UUID id,
        @RequestBody DoctorRequestDto dto,
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request) {
        DoctorResponseDto doctor = doctorService.updateDoctor(id, dto, authUser, request);
        return ResponseEntity.ok(doctor);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteDoctor(
        @PathVariable UUID id,
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request) {
        String doctor = doctorService.deleteDoctor(id, authUser, request);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<DoctorDetailResponse> getDoctorProfile(
        @PathVariable UUID id,
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request) {
        DoctorDetailResponse doctor = doctorService.getDoctorProfile(id, authUser, request);
        return ResponseEntity.ok(doctor);
    }



}
