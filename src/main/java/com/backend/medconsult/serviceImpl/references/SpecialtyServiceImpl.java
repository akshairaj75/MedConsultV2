package com.backend.medconsult.serviceImpl.references;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.medconsult.dto.references.SpecialtyRequestDto;
import com.backend.medconsult.dto.references.SpecialtyResponseDto;
import com.backend.medconsult.dto.references.SubSpecialtyRequestDto;
import com.backend.medconsult.dto.references.SubSpecialtyResponseDto;
import com.backend.medconsult.entity.references.Specialty;
import com.backend.medconsult.entity.references.SubSpecialty;
import com.backend.medconsult.repository.references.SpecialtyRepository;
import com.backend.medconsult.repository.references.SubSpecialtyRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.references.SpecialtyService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    @Autowired
    SpecialtyRepository specialtyRepository;

    @Autowired
    SubSpecialtyRepository subSpecialtyRepository;

    @Transactional
    @Override
    public SpecialtyResponseDto addSpecialty(SpecialtyRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Specialty specialty = new Specialty();
        specialty.setNameEn(dto.getNameEn());
        specialty.setNameAr(dto.getNameAr());
        specialty.setCategory(dto.getCategory());
        specialty.setIconSlug(dto.getIconSlug());
        specialty.setCode(dto.getCode());

        Specialty savedSpecialty = specialtyRepository.save(specialty);
        return SpecialtyResponseDto.fromEntity(savedSpecialty);
    }

    @Override
    public List<SpecialtyResponseDto> getAllSpecialties() {
        List<Specialty> specialties = specialtyRepository.findAll();
        return specialties.stream().map(SpecialtyResponseDto::fromEntity).toList();
    }

    @Override
    public List<SubSpecialtyResponseDto> getAllSubSpecialties(UUID specialityId) {
        List<SubSpecialty> subSpecialties = subSpecialtyRepository.findBySpecialty_SpecialtyId(specialityId);
        return subSpecialties.stream().map(SubSpecialtyResponseDto::fromEntity).toList();
    }

    @Transactional
    @Override
    public SpecialtyResponseDto updateSpecialty(UUID specialityId, SpecialtyRequestDto dto,
            CustomUserPrincipal authUser, HttpServletRequest request) {
        Specialty specialty = specialtyRepository.findById(specialityId)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
        if (dto.getNameEn() != null) {
            specialty.setNameEn(dto.getNameEn());
        }
        if (dto.getNameAr() != null) {
            specialty.setNameAr(dto.getNameAr());
        }
        if (dto.getCategory() != null) {
            specialty.setCategory(dto.getCategory());
        }
        if (dto.getIconSlug() != null) {
            specialty.setIconSlug(dto.getIconSlug());
        }
        if (dto.getCode() != null) {
            specialty.setCode(dto.getCode());
        }
        Specialty savedSpecialty = specialtyRepository.save(specialty);
        return SpecialtyResponseDto.fromEntity(savedSpecialty);
    }

    @Transactional
    @Override
    public String deleteSpecialty(UUID specialityId, CustomUserPrincipal authUser,
            HttpServletRequest request) {
        Specialty specialty = specialtyRepository.findById(specialityId)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
        specialtyRepository.delete(specialty);
        return "Specialty deleted successfully";
    }

    @Transactional
    @Override
    public SubSpecialtyResponseDto addSubSpecialty(SubSpecialtyRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Specialty specialty = specialtyRepository.findById(dto.getSpecialtyId())
                .orElseThrow(() -> new RuntimeException("Specialty not found"));

        SubSpecialty subSpecialty = new SubSpecialty();
        subSpecialty.setNameEn(dto.getNameEn());
        subSpecialty.setNameAr(dto.getNameAr());
        subSpecialty.setSpecialty(specialty);
        subSpecialty.setIsActive(dto.getIsActive());

        SubSpecialty savedSubSpecialty = subSpecialtyRepository.save(subSpecialty);
        return SubSpecialtyResponseDto.fromEntity(savedSubSpecialty);
    }

    @Transactional
    @Override
    public SubSpecialtyResponseDto updateSubSpecialty(UUID subSpecialtyId, SubSpecialtyRequestDto dto,
            CustomUserPrincipal authUser, HttpServletRequest request) {

        SubSpecialty subSpecialty = subSpecialtyRepository.findById(subSpecialtyId)
                .orElseThrow(() -> new RuntimeException("Sub Specialty not found"));

        if (dto.getNameEn() != null) {
            subSpecialty.setNameEn(dto.getNameEn());
        }

        if (dto.getNameAr() != null) {
            subSpecialty.setNameAr(dto.getNameAr());
        }

        if (dto.getIsActive() != null) {
            subSpecialty.setIsActive(dto.getIsActive());
        }

        if (dto.getSpecialtyId() != null) {
            Specialty specialty = specialtyRepository.findById(dto.getSpecialtyId())
                    .orElseThrow(() -> new RuntimeException("Specialty not found"));
            subSpecialty.setSpecialty(specialty);
        }

        SubSpecialty savedSubSpecialty = subSpecialtyRepository.save(subSpecialty);
        return SubSpecialtyResponseDto.fromEntity(savedSubSpecialty);
    }

    @Transactional
    @Override
    public String deleteSubSpecialty(UUID subSpecialtyId, CustomUserPrincipal authUser,
            HttpServletRequest request) {

        SubSpecialty subSpecialty = subSpecialtyRepository.findById(subSpecialtyId)
                .orElseThrow(() -> new RuntimeException("Sub Specialty not found"));

        subSpecialtyRepository.delete(subSpecialty);
        return "Sub Specialty deleted successfully";
    }

}
