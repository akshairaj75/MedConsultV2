package com.backend.medconsult.service.references;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.references.InsuranceProviderRequestDto;
import com.backend.medconsult.dto.references.InsuranceProviderResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface InsuranceServiceProvider {

    InsuranceProviderResponseDto addProvider(InsuranceProviderRequestDto dto, MultipartFile file, CustomUserPrincipal authUser,
            HttpServletRequest request);

    List<InsuranceProviderResponseDto> getAllProviders();

    InsuranceProviderResponseDto updateProvider(UUID id, InsuranceProviderRequestDto dto, MultipartFile file,
            CustomUserPrincipal authUser, HttpServletRequest request);

    InsuranceProviderResponseDto getProvider(UUID id);

    String deleteProvider(UUID id);

}
