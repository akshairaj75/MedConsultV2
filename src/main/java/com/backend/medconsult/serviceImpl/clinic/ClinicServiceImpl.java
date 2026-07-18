package com.backend.medconsult.serviceImpl.clinic;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinic.ClinicBranchRequestDto;
import com.backend.medconsult.dto.clinic.ClinicBranchResponseDto;
import com.backend.medconsult.dto.clinic.ClinicInsuranceRequestDto;
import com.backend.medconsult.dto.clinic.ClinicInsuranceResponseDto;
import com.backend.medconsult.dto.clinic.ClinicLanguageResponseDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourRequestDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourResponseDto;
import com.backend.medconsult.dto.clinic.ClinicRequestDto;
import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.dto.clinic.ClinicSpecialtyResponseDto;
import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.clinic.ClinicBranch;
import com.backend.medconsult.entity.clinic.ClinicInsurance;
import com.backend.medconsult.entity.clinic.ClinicLanguage;
import com.backend.medconsult.entity.clinic.ClinicOperatingHour;
import com.backend.medconsult.entity.clinic.ClinicSpecialty;
import com.backend.medconsult.entity.references.City;
import com.backend.medconsult.entity.references.InsuranceProvider;
import com.backend.medconsult.entity.references.Language;
import com.backend.medconsult.entity.references.Locality;
import com.backend.medconsult.entity.references.Specialty;
import com.backend.medconsult.repository.clinic.ClinicBranchRepository;
import com.backend.medconsult.repository.clinic.ClinicInsuranceRepository;
import com.backend.medconsult.repository.clinic.ClinicLanguageRepository;
import com.backend.medconsult.repository.clinic.ClinicOperatingHourRepository;
import com.backend.medconsult.repository.clinic.ClinicRepository;
import com.backend.medconsult.repository.clinic.ClinicSpecialtyRepository;
import com.backend.medconsult.repository.references.CityRepository;
import com.backend.medconsult.repository.references.InsuranceProviderRepository;
import com.backend.medconsult.repository.references.LanguageRepository;
import com.backend.medconsult.repository.references.LocalityRepository;
import com.backend.medconsult.repository.references.SpecialtyRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.FileStorageService;
import com.backend.medconsult.service.clinic.ClinicService;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    ClinicBranchRepository clinicBranchRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    LocalityRepository localityRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    SpecialtyRepository specialtyRepository;

    @Autowired
    ClinicSpecialtyRepository clinicSpecialtyRepository;

    @Autowired
    ClinicInsuranceRepository insuranceRepository;

    @Autowired
    InsuranceProviderRepository insuranceProviderRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    ClinicLanguageRepository clinicLanguageRepository;

    @Override
    public List<ClinicResponseDto> getAllClinics() {
        return clinicRepository.findAll()
                .stream()
                .map(ClinicResponseDto::fromEntity)
                .toList();
    }

    @Autowired
    ClinicOperatingHourRepository hourRepository;

    @Override
    public ClinicResponseDto getClinicById(UUID id) {
        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new RuntimeException("Clinic not found"));
        return ClinicResponseDto.fromEntity(clinic);
    }

    @Transactional
    @Override
    public ClinicResponseDto createClinic(ClinicRequestDto dto, MultipartFile logo, CustomUserPrincipal principal) {
        Clinic clinic = new Clinic();

        clinic.setClinicId(UUID.randomUUID());
        clinic.setNameEn(dto.getNameEn());
        clinic.setNameAr(dto.getNameAr());
        clinic.setDescriptionEn(dto.getDescriptionEn());
        clinic.setDescriptionAr(dto.getDescriptionAr());

        clinic.setWebsite(dto.getWebsite());
        clinic.setEmail(dto.getEmail());
        clinic.setPhonePrimary(dto.getPhonePrimary());
        clinic.setPhoneSecondary(dto.getPhoneSecondary());
        clinic.setMohLicenseNumber(dto.getMohLicenseNumber());
        clinic.setMohVerified(dto.getMohVerified());
        clinic.setMohVerifiedAt(dto.getMohVerifiedAt());
        clinic.setNaphiesFacilityId(dto.getNaphiesFacilityId());

        clinic.setEmail(dto.getEmail());
        clinic.setWebsite(dto.getWebsite());

        if (logo != null && !logo.isEmpty()) {
            try {
                String filePath = fileStorageService.storeFile(logo, "clinic/clinicLogo");
                clinic.setLogoUrl(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }

        Clinic saved = clinicRepository.save(clinic);
        return ClinicResponseDto.fromEntity(saved);
    }

    @Transactional
    @Override
    public ClinicResponseDto updateClinic(UUID id, ClinicRequestDto dto, MultipartFile logo,
            CustomUserPrincipal principal) {

        Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new RuntimeException("Clinic not found"));

        if (dto == null) {
            throw new RuntimeException("Update data is null");
        }

        if (dto.getNameEn() != null)
            clinic.setNameEn(dto.getNameEn());
        if (dto.getNameAr() != null)
            clinic.setNameAr(dto.getNameAr());
        if (dto.getDescriptionEn() != null)
            clinic.setDescriptionEn(dto.getDescriptionEn());
        if (dto.getDescriptionAr() != null)
            clinic.setDescriptionAr(dto.getDescriptionAr());

        if (dto.getWebsite() != null)
            clinic.setWebsite(dto.getWebsite());
        if (dto.getEmail() != null)
            clinic.setEmail(dto.getEmail());
        if (dto.getPhonePrimary() != null)
            clinic.setPhonePrimary(dto.getPhonePrimary());
        if (dto.getPhoneSecondary() != null)
            clinic.setPhoneSecondary(dto.getPhoneSecondary());
        if (dto.getMohLicenseNumber() != null)
            clinic.setMohLicenseNumber(dto.getMohLicenseNumber());
        if (dto.getMohVerified() != null)
            clinic.setMohVerified(dto.getMohVerified());
        if (dto.getMohVerifiedAt() != null)
            clinic.setMohVerifiedAt(dto.getMohVerifiedAt());
        if (dto.getNaphiesFacilityId() != null)
            clinic.setNaphiesFacilityId(dto.getNaphiesFacilityId());

        clinic.setEmail(dto.getEmail());
        clinic.setWebsite(dto.getWebsite());

        if (logo != null && !logo.isEmpty()) {
            try {
                String filePath = fileStorageService.storeFile(logo, "clinic/clinicLogo");
                clinic.setLogoUrl(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }

        Clinic saved = clinicRepository.save(clinic);
        return ClinicResponseDto.fromEntity(saved);
    }

    // @Override
    // public ClinicResponseDto verifyClinic(UUID id, CustomUserPrincipal principal)
    // {
    // Clinic clinic = clinicRepository.findById(id).orElseThrow(() -> new
    // RuntimeException("Clinic not found"));

    // clinic.setMohVerified(!clinic.getMohVerified());
    // clinic.setMohVerifiedAt(new Date());

    // Clinic saved = clinicRepository.save(clinic);
    // return ClinicResponseDto.fromEntity(saved);
    // }

    @Override
    public List<ClinicBranchResponseDto> getClinicBranches(UUID id) {
        List<ClinicBranch> branches = clinicBranchRepository.findByClinic_ClinicId(id);
        List<ClinicBranchResponseDto> responseDto = branches.stream()
                .map(ClinicBranchResponseDto::fromEntity)
                .toList();
        return responseDto;
    }

    @Transactional
    @Override
    public ClinicBranchResponseDto createClinicBranch(UUID id,
            ClinicBranchRequestDto dto,
            CustomUserPrincipal principal) {

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        Locality locality = localityRepository.findById(dto.getLocalityId())
                .orElseThrow(() -> new RuntimeException("Locality not found"));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        ClinicBranch clinicBranch = new ClinicBranch();
        clinicBranch.setBranchId(UUID.randomUUID());
        clinicBranch.setBranchNameEn(dto.getBranchNameEn());
        clinicBranch.setBranchNameAr(dto.getBranchNameAr());
        clinicBranch.setLocality(locality);
        clinicBranch.setClinic(clinic);
        clinicBranch.setCity(city);
        clinicBranch.setAddressLine1(dto.getAddressLine1());
        clinicBranch.setAddressLine2(dto.getAddressLine2());
        clinicBranch.setPostalCode(dto.getPostalCode());
        clinicBranch.setLatitude(dto.getLatitude());
        clinicBranch.setLongitude(dto.getLongitude());
        clinicBranch.setMapsUrl(dto.getMapsUrl());
        clinicBranch.setPhone(dto.getPhone());
        clinicBranch.setIsPrimary(dto.getIsPrimary());
        clinicBranch.setIsActive(dto.getIsActive());
        ClinicBranch savedClinicBranch = clinicBranchRepository.save(clinicBranch);
        return ClinicBranchResponseDto.fromEntity(savedClinicBranch);
    }

    @Override
    public ClinicBranchResponseDto updateClinicBranch(UUID id,
            ClinicBranchRequestDto dto,
            CustomUserPrincipal principal) {
        ClinicBranch clinicBranch = clinicBranchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic Branch not found"));
        if (dto.getBranchNameEn() != null) {
            clinicBranch.setBranchNameEn(dto.getBranchNameEn());
        }
        if (dto.getBranchNameAr() != null) {
            clinicBranch.setBranchNameAr(dto.getBranchNameAr());
        }
        if (dto.getAddressLine1() != null) {
            clinicBranch.setAddressLine1(dto.getAddressLine1());
        }
        if (dto.getAddressLine2() != null) {
            clinicBranch.setAddressLine2(dto.getAddressLine2());
        }
        if (dto.getPostalCode() != null) {
            clinicBranch.setPostalCode(dto.getPostalCode());
        }
        if (dto.getLatitude() != null) {
            clinicBranch.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != null) {
            clinicBranch.setLongitude(dto.getLongitude());
        }
        if (dto.getMapsUrl() != null) {
            clinicBranch.setMapsUrl(dto.getMapsUrl());
        }
        if (dto.getPhone() != null) {
            clinicBranch.setPhone(dto.getPhone());
        }
        if (dto.getIsPrimary() != null) {
            clinicBranch.setIsPrimary(dto.getIsPrimary());
        }
        if (dto.getIsActive() != null) {
            clinicBranch.setIsActive(dto.getIsActive());
        }
        ClinicBranch savedClinicBranch = clinicBranchRepository.save(clinicBranch);
        return ClinicBranchResponseDto.fromEntity(savedClinicBranch);
    }

    @Override
    public void deleteClinicBranch(UUID id, CustomUserPrincipal principal) {
        ClinicBranch branch = clinicBranchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic Branch not found"));

        clinicBranchRepository.delete(branch);
    }

    @Override
    public List<ClinicOperatingHourResponseDto> updateBranchHours(UUID id,
            List<ClinicOperatingHourRequestDto> dtos,
            CustomUserPrincipal principal) {
        ClinicBranch branch = clinicBranchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic Branch not found"));

        List<ClinicOperatingHour> operatingHours = hourRepository.findAllByBranch(branch);

        for (ClinicOperatingHourRequestDto dto : dtos) {
            ClinicOperatingHour operatingHour = operatingHours.stream()
                    .filter(oh -> oh.getDayOfWeek().equals(dto.getDayOfWeek()))
                    .findFirst()
                    .orElseGet(ClinicOperatingHour::new);

            operatingHour.setBranch(branch);
            operatingHour.setDayOfWeek(dto.getDayOfWeek());
            operatingHour.setOpenTime(dto.getOpenTime());
            operatingHour.setCloseTime(dto.getCloseTime());
            operatingHour.setIsClosed(dto.getIsClosed());

            hourRepository.save(operatingHour);
        }
        return operatingHours.stream()
                .map(ClinicOperatingHourResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<ClinicOperatingHourResponseDto> getBranchHours(UUID branchId) {

        ClinicBranch clinicBranch = clinicBranchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Clinic Branch not found"));

        List<ClinicOperatingHour> operatingHours = hourRepository.findAllByBranch(clinicBranch);

        List<ClinicOperatingHourResponseDto> responseDto = operatingHours.stream()
                .map(ClinicOperatingHourResponseDto::fromEntity)
                .toList();

        return responseDto;
    }

    @Override
    public ClinicSpecialtyResponseDto addClinicSpecialty(UUID clinicId, UUID specialtyId,
            CustomUserPrincipal principal) {
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));

        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        ClinicSpecialty clinicSpecialty = new ClinicSpecialty();
        clinicSpecialty.setSpecialty(specialty);
        clinicSpecialty.setClinic(clinic);

        ClinicSpecialty savedClinicSpecialty = clinicSpecialtyRepository.save(clinicSpecialty);
        return ClinicSpecialtyResponseDto.fromEntity(savedClinicSpecialty);
    }

    // @Override
    // public void deleteClinicSpecialty(UUID id, UUID specialtyId,
    //         CustomUserPrincipal principal) {

    // }

    @Override
    public ClinicInsuranceResponseDto addClinicInsurance(UUID id, UUID
    providerId, ClinicInsuranceRequestDto dto,
    CustomUserPrincipal principal) {

        ClinicInsurance clinicInsurance = new ClinicInsurance();

        Clinic clinic = clinicRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Clinic not found"));

        InsuranceProvider insuranceProvider = insuranceProviderRepository.findById(providerId)
        .orElseThrow(() -> new RuntimeException("Insurance Provider not found"));

        clinicInsurance.setClinic(clinic);
        clinicInsurance.setProvider(insuranceProvider);
        clinicInsurance.setNetworkClass(dto.getNetworkClass());
        return ClinicInsuranceResponseDto.fromEntity(insuranceRepository.save(clinicInsurance));        
    }

    // @Override
    // public void deleteClinicInsurance(UUID id, UUID providerId,
    // CustomUserPrincipal principal) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'deleteClinicInsurance'");
    // }

    @Override
    public ClinicLanguageResponseDto addClinicLanguage(UUID id, UUID languageId,
    CustomUserPrincipal principal) {

        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found"));

        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        ClinicLanguage clinicLanguage = new ClinicLanguage();
        clinicLanguage.setLanguage(language);
        clinicLanguage.setClinic(clinic);

        ClinicLanguage savedClinicLanguage = clinicLanguageRepository.save(clinicLanguage);
        return ClinicLanguageResponseDto.fromEntity(savedClinicLanguage);
    }

    // @Override
    // public void deleteClinicLanguage(UUID id, UUID languageId,
    // CustomUserPrincipal principal) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'deleteClinicLanguage'");
    // }

}
