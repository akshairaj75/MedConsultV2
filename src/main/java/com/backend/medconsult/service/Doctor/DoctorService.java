package com.backend.medconsult.service.Doctor;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.doctor.DoctorDetailResponse;
import com.backend.medconsult.dto.doctor.DoctorRequestDto;
import com.backend.medconsult.dto.doctor.DoctorResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface DoctorService {

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto addDoctor(DoctorRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    DoctorResponseDto updateDoctor(UUID id, DoctorRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String deleteDoctor(UUID id, CustomUserPrincipal authUser, HttpServletRequest request);

    DoctorDetailResponse getDoctorProfile(UUID id, CustomUserPrincipal authUser, HttpServletRequest request);

}
