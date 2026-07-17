package com.backend.medconsult.controller.references;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.references.LanguageRequestDto;
import com.backend.medconsult.dto.references.LanguageResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.references.LanguageService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/medconsult/languages")
public class LanguageController {

    @Autowired
    LanguageService languageService;

    @GetMapping("/all")
    public ResponseEntity<List<LanguageResponseDto>> getAllLanguages() {
        List<LanguageResponseDto> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(languages);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<LanguageResponseDto> addLanguage(
        @RequestBody LanguageRequestDto dto,
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request
    ) {
        LanguageResponseDto language = languageService.addLanguage(dto, authUser, request);
        return ResponseEntity.ok(language);
    }

    @PatchMapping("/{id}/edit")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<LanguageResponseDto> updateLanguage(
        @PathVariable UUID id,
        @RequestBody LanguageRequestDto dto,
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request
    ) {
        LanguageResponseDto language = languageService.updateLanguage(id, dto, authUser, request);
        return ResponseEntity.ok(language);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<String> deleteLanguage(
        @PathVariable UUID id,
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request
    ) {
        String message = languageService.deleteLanguage(id, authUser, request);
        return ResponseEntity.ok(message);
    }
}
