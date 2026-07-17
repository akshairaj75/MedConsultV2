package com.backend.medconsult.service.references;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.references.SpecialtyRequestDto;
import com.backend.medconsult.dto.references.SpecialtyResponseDto;
import com.backend.medconsult.dto.references.SubSpecialtyRequestDto;
import com.backend.medconsult.dto.references.SubSpecialtyResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface SpecialtyService {

    SpecialtyResponseDto addSpecialty(SpecialtyRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    List<SpecialtyResponseDto> getAllSpecialties();

    List<SubSpecialtyResponseDto> getAllSubSpecialties(UUID specialityId);

    SpecialtyResponseDto updateSpecialty(UUID specialityId, SpecialtyRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String deleteSpecialty(UUID specialityId, CustomUserPrincipal authUser, HttpServletRequest request);

    SubSpecialtyResponseDto addSubSpecialty(SubSpecialtyRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    SubSpecialtyResponseDto updateSubSpecialty(UUID subSpecialtyId, SubSpecialtyRequestDto dto,
            CustomUserPrincipal authUser, HttpServletRequest request);

    String deleteSubSpecialty(UUID subSpecialtyId, CustomUserPrincipal authUser, HttpServletRequest request);

}
