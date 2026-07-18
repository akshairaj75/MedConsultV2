package com.backend.medconsult.controller.patient;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.patient.PatientAllergyRequestDto;
import com.backend.medconsult.dto.patient.PatientAllergyResponseDto;
import com.backend.medconsult.dto.patient.PatientChronicConditionRequestDto;
import com.backend.medconsult.dto.patient.PatientChronicConditionResponseDto;
import com.backend.medconsult.dto.patient.PatientHealthProfileRequestDto;
import com.backend.medconsult.dto.patient.PatientHealthProfileResponseDto;
import com.backend.medconsult.dto.patient.PatientRequestDto;
import com.backend.medconsult.dto.patient.PatientResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.patient.PatientService;

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

    @GetMapping("/me")
    public ResponseEntity<PatientResponseDto> getProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        PatientResponseDto res = patientService.getMyProfile(principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(res);
    }

    @PatchMapping("/me/update")
    public ResponseEntity<PatientResponseDto> updateProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody PatientRequestDto request) {
        PatientResponseDto res = patientService.updateProfile(principal, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/me/health-profile")
    public ResponseEntity<PatientHealthProfileResponseDto> getHealthProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        PatientHealthProfileResponseDto healthProfile = patientService.getHealthProfile(principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(healthProfile);
    }

    @PostMapping("/me/health-profile/add")
    public ResponseEntity<PatientHealthProfileResponseDto> addHealthProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody PatientHealthProfileRequestDto request) {
        PatientHealthProfileResponseDto healthProfile = patientService.addHealthProfile(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(healthProfile);
    }

    @PutMapping("/me/health-profile/update")
    public ResponseEntity<PatientHealthProfileResponseDto> updateHealthProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody PatientHealthProfileRequestDto request) {
        PatientHealthProfileResponseDto healthProfile = patientService.updateHealthProfile(principal, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(healthProfile);
    }

    @GetMapping("/me/allergies")
    public ResponseEntity<List<PatientAllergyResponseDto>> getAllergies(
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        List<PatientAllergyResponseDto> allergies = patientService.getAllergies(principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(allergies);
    }

    @PostMapping("/me/allergies/add")
    public ResponseEntity<PatientAllergyResponseDto> addAllergy(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody PatientAllergyRequestDto request) {
        PatientAllergyResponseDto allergy = patientService.addAllergy(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(allergy);
    }

    @PutMapping("/me/allergies/{allergyId}/update")
    public ResponseEntity<PatientAllergyResponseDto> updateAllergy(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID allergyId,
            @RequestBody PatientAllergyRequestDto request) {
        PatientAllergyResponseDto allergy = patientService.updateAllergy(principal, allergyId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(allergy);
    }

    @DeleteMapping("/me/allergies/{allergyId}")
    public ResponseEntity<Void> deleteHealthProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID allergyId) {
        patientService.deleteAllergy(principal, allergyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/me/chronic-conditions")
    public ResponseEntity<List<PatientChronicConditionResponseDto>> getChronicConditions(
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        List<PatientChronicConditionResponseDto> chronicConditions = patientService.getChronicConditions(principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(chronicConditions);
    }

    @PostMapping("/me/add-chronic-condition")
    public ResponseEntity<PatientChronicConditionResponseDto> addChronicCondition(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @RequestBody PatientChronicConditionRequestDto request) {
        PatientChronicConditionResponseDto chronicCondition = patientService.addChronicCondition(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chronicCondition);
    }

    @PutMapping("/me/chronic-condition/{chronicConditionId}/update")
    public ResponseEntity<PatientChronicConditionResponseDto> updateChronicCondition(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID chronicConditionId,
            @RequestBody PatientChronicConditionRequestDto request) {
        PatientChronicConditionResponseDto chronicCondition = patientService.updateChronicCondition(principal,
                chronicConditionId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(chronicCondition);
    }

    @DeleteMapping("/me/chronic-condition/{chronicConditionId}")
    public ResponseEntity<Void> deleteChronicCondition(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID chronicConditionId) {
        patientService.deleteChronicCondition(principal, chronicConditionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }




    // DOCTOR VIEW
    //-----------------------------------------

    @GetMapping("/{patientId}/health-profile")
    public ResponseEntity<PatientHealthProfileResponseDto> getPatientHealthProfile(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID patientId) {
        PatientHealthProfileResponseDto healthProfile = patientService.getPatientHealthProfile(principal, patientId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(healthProfile);
    }

    @GetMapping("/{patientId}/allergies")
    public ResponseEntity<List<PatientAllergyResponseDto>> getPatientAllergies(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID patientId) {
        List<PatientAllergyResponseDto> allergies = patientService.getPatientAllergies(principal, patientId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(allergies);
    }

    @GetMapping("/{patientId}/chronic-conditions")
    public ResponseEntity<List<PatientChronicConditionResponseDto>> getPatientChronicConditions(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable UUID patientId) {
        List<PatientChronicConditionResponseDto> chronicConditions = patientService.getPatientChronicConditions(principal,
                patientId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(chronicConditions);
    }

}
