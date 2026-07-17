package com.backend.medconsult.serviceImpl.references;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.medconsult.dto.references.LanguageRequestDto;
import com.backend.medconsult.dto.references.LanguageResponseDto;
import com.backend.medconsult.entity.references.Language;
import com.backend.medconsult.repository.references.LanguageRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.references.LanguageService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public List<LanguageResponseDto> getAllLanguages() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream().map(LanguageResponseDto::fromEntity).toList();
    }

    @Override
    @Transactional
    public LanguageResponseDto addLanguage(LanguageRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {
        Language language = new Language();
        language.setCode(dto.getCode());
        language.setNameAr(dto.getNameAr());
        language.setNameEn(dto.getNameEn());
        language.setIsActive(dto.getIsActive());

        Language savedLanguage = languageRepository.save(language);
        return LanguageResponseDto.fromEntity(savedLanguage);
    }

    @Override
    public LanguageResponseDto updateLanguage(UUID id, LanguageRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {
        Language language = languageRepository.findById(id).orElseThrow();
        language.setCode(dto.getCode());
        language.setNameAr(dto.getNameAr());
        language.setNameEn(dto.getNameEn());
        language.setIsActive(dto.getIsActive());
        Language savedLanguage = languageRepository.save(language);
        return LanguageResponseDto.fromEntity(savedLanguage);
    }

    @Override
    public String deleteLanguage(UUID id, CustomUserPrincipal authUser, HttpServletRequest request) {
        Language language = languageRepository.findById(id).orElseThrow();
        languageRepository.delete(language);
        return "Language deleted successfully";
    }
}
