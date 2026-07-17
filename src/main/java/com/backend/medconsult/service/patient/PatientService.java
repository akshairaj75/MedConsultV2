package com.backend.medconsult.service.patient;

import java.nio.file.attribute.UserPrincipal;

import com.backend.medconsult.dto.patient.PatientRequestDto;
import com.backend.medconsult.dto.patient.PatientResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface PatientService {

    PatientResponseDto createProfile(CustomUserPrincipal principal, PatientRequestDto request);

}
