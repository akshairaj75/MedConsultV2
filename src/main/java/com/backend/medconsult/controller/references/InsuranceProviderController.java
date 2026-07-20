package com.backend.medconsult.controller.references;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.references.InsuranceProviderRequestDto;
import com.backend.medconsult.dto.references.InsuranceProviderResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.references.InsuranceServiceProvider;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/medconsult/insurance-providers")
public class InsuranceProviderController {

    @Autowired
    InsuranceServiceProvider insuranceServiceProvider;

    @GetMapping("/all")
    public ResponseEntity<List<InsuranceProviderResponseDto>> getAllProviders() {
        List<InsuranceProviderResponseDto> providers = insuranceServiceProvider.getAllProviders();
        return ResponseEntity.ok(providers);
    }

    @PostMapping("/add-provider")
    public InsuranceProviderResponseDto addProvider(
            @RequestPart("body") InsuranceProviderRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return insuranceServiceProvider.addProvider(dto, file, authUser, request);
    }

    @PutMapping("/{id}/update")
    public InsuranceProviderResponseDto updateProvider(
            @PathVariable UUID id,
            @RequestPart("body") InsuranceProviderRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return insuranceServiceProvider.updateProvider(id, dto, file, authUser, request);
    }

    @GetMapping("/{id}")
    public InsuranceProviderResponseDto getProvider(@PathVariable UUID id) {
        return insuranceServiceProvider.getProvider(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProvider(@PathVariable UUID id) {
        return insuranceServiceProvider.deleteProvider(id);
    }

}
