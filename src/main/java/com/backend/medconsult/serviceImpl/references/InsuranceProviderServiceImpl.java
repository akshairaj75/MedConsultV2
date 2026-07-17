package com.backend.medconsult.serviceImpl.references;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.references.InsuranceProviderRequestDto;
import com.backend.medconsult.dto.references.InsuranceProviderResponseDto;
import com.backend.medconsult.entity.references.InsuranceProvider;
import com.backend.medconsult.repository.references.InsuranceProviderRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.FileStorageService;
import com.backend.medconsult.service.references.InsuranceServiceProvider;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class InsuranceProviderServiceImpl implements InsuranceServiceProvider {

    @Autowired
    InsuranceProviderRepository insuranceProviderRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    public InsuranceProviderResponseDto addProvider(InsuranceProviderRequestDto dto,
            MultipartFile file,
            CustomUserPrincipal authUser, HttpServletRequest request) {

        InsuranceProvider provider = new InsuranceProvider();
        provider.setNameEn(dto.getNameEn());
        provider.setNameAr(dto.getNameAr());

        if (file != null && !file.isEmpty()) {
            try {
                String filePath = fileStorageService.storeFile(file, "insuranceProviderLogo");

                provider.setLogoUrl(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }

        }
        InsuranceProvider savedProvider = insuranceProviderRepository.save(provider);
        return InsuranceProviderResponseDto.fromEntity(savedProvider);
    }

    @Override
    public List<InsuranceProviderResponseDto> getAllProviders() {
        List<InsuranceProvider> providers = insuranceProviderRepository.findAll();
        if (providers.isEmpty()) {
            throw new RuntimeException("No insurance providers found");
        }
        return providers.stream().map(InsuranceProviderResponseDto::fromEntity).toList();
    }

    @Override
    public InsuranceProviderResponseDto updateProvider(UUID id, InsuranceProviderRequestDto dto, MultipartFile file,
            CustomUserPrincipal authUser, HttpServletRequest request) {
        InsuranceProvider provider = insuranceProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance provider not found"));

        if (dto.getNameEn() != null) {
            provider.setNameEn(dto.getNameEn());
        }

        if (dto.getNameAr() != null) {
            provider.setNameAr(dto.getNameAr());
        }

        if (file != null && !file.isEmpty()) {
            try {
                if (provider.getLogoUrl() != null) {
                    fileStorageService.deleteFile(provider.getLogoUrl());
                }
                String filePath = fileStorageService.storeFile(file, "insuranceProviderLogo");
                provider.setLogoUrl(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update file", e);
            }
        }

        InsuranceProvider savedProvider = insuranceProviderRepository.save(provider);
        return InsuranceProviderResponseDto.fromEntity(savedProvider);
    }

    @Override
    public InsuranceProviderResponseDto getProvider(UUID id) {
        InsuranceProvider provider = insuranceProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance provider not found"));
        return InsuranceProviderResponseDto.fromEntity(provider);
    }

    @Override
    public String deleteProvider(UUID id) {

        InsuranceProvider provider = insuranceProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance provider not found"));

        insuranceProviderRepository.delete(provider);
        return "Insurance provider deleted successfully";
    }
}
