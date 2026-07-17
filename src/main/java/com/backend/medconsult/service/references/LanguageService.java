package com.backend.medconsult.service.references;

import java.util.List;
import java.util.UUID;

import com.backend.medconsult.dto.references.LanguageRequestDto;
import com.backend.medconsult.dto.references.LanguageResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface LanguageService {

    List<LanguageResponseDto> getAllLanguages();

    LanguageResponseDto addLanguage(LanguageRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    LanguageResponseDto updateLanguage(UUID id, LanguageRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String deleteLanguage(UUID id, CustomUserPrincipal authUser, HttpServletRequest request);

}
