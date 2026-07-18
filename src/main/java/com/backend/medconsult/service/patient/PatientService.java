package com.backend.medconsult.service.patient;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.patient.PatientAllergyRequestDto;
import com.backend.medconsult.dto.patient.PatientAllergyResponseDto;
import com.backend.medconsult.dto.patient.PatientChronicConditionRequestDto;
import com.backend.medconsult.dto.patient.PatientChronicConditionResponseDto;
import com.backend.medconsult.dto.patient.PatientHealthProfileRequestDto;
import com.backend.medconsult.dto.patient.PatientHealthProfileResponseDto;
import com.backend.medconsult.dto.patient.PatientRequestDto;
import com.backend.medconsult.dto.patient.PatientResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface PatientService {

    PatientResponseDto createProfile(CustomUserPrincipal principal, PatientRequestDto request);

    PatientResponseDto getMyProfile(CustomUserPrincipal principal);

    PatientResponseDto updateProfile(CustomUserPrincipal principal, PatientRequestDto request);

    PatientHealthProfileResponseDto getHealthProfile(CustomUserPrincipal principal);

    PatientHealthProfileResponseDto addHealthProfile(CustomUserPrincipal principal,
            PatientHealthProfileRequestDto request);

    PatientHealthProfileResponseDto updateHealthProfile(CustomUserPrincipal principal,
            PatientHealthProfileRequestDto request);

    List<PatientAllergyResponseDto> getAllergies(CustomUserPrincipal principal);

    PatientAllergyResponseDto addAllergy(CustomUserPrincipal principal, PatientAllergyRequestDto request);

    PatientAllergyResponseDto updateAllergy(CustomUserPrincipal principal, UUID allergyId, PatientAllergyRequestDto request);

    void deleteAllergy(CustomUserPrincipal principal, UUID allergyId);

    List<PatientChronicConditionResponseDto> getChronicConditions(CustomUserPrincipal principal);

    PatientChronicConditionResponseDto addChronicCondition(CustomUserPrincipal principal,
            PatientChronicConditionRequestDto request);

    PatientChronicConditionResponseDto updateChronicCondition(CustomUserPrincipal principal, UUID chronicConditionId,
            PatientChronicConditionRequestDto request);

    void deleteChronicCondition(CustomUserPrincipal principal, UUID chronicConditionId);

    PatientHealthProfileResponseDto getPatientHealthProfile(CustomUserPrincipal principal, UUID patientId);

    List<PatientAllergyResponseDto> getPatientAllergies(CustomUserPrincipal principal, UUID patientId);

    List<PatientChronicConditionResponseDto> getPatientChronicConditions(CustomUserPrincipal principal, UUID patientId);

}
