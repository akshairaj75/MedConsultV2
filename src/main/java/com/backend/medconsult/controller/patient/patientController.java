package com.backend.medconsult.controller.patient;

import java.nio.file.attribute.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.patient.PatientRequestDto;
import com.backend.medconsult.dto.patient.PatientResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.patient.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class patientController {

    @Autowired
    PatientService patientService;

    @PostMapping("/add-profile")
    public ResponseEntity<PatientResponseDto> addProfile( 
        @AuthenticationPrincipal CustomUserPrincipal principal,
        @RequestBody PatientRequestDto request) {
            PatientResponseDto res = patientService.createProfile(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(res);
    }



}
