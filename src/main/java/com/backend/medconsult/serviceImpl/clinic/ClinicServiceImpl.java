package com.backend.medconsult.serviceImpl.clinic;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.backend.medconsult.dto.clinic.ClinicBranchRequestDto;
import com.backend.medconsult.dto.clinic.ClinicBranchResponseDto;
import com.backend.medconsult.dto.clinic.ClinicDetailResponse;
import com.backend.medconsult.dto.clinic.ClinicInsuranceRequestDto;
import com.backend.medconsult.dto.clinic.ClinicInsuranceResponseDto;
import com.backend.medconsult.dto.clinic.ClinicLanguageResponseDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourRequestDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourResponseDto;
import com.backend.medconsult.dto.clinic.ClinicRequestDto;
import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.dto.clinic.ClinicSearchRequest;
import com.backend.medconsult.dto.clinic.ClinicSpecialtyResponseDto;
import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.clinic.ClinicBranch;
import com.backend.medconsult.entity.clinic.ClinicInsurance;
import com.backend.medconsult.entity.clinic.ClinicLanguage;
import com.backend.medconsult.entity.clinic.ClinicOperatingHour;
import com.backend.medconsult.entity.clinic.ClinicSpecialty;
import com.backend.medconsult.entity.clinic.ClinicAdmin;
import com.backend.medconsult.repository.clinic.ClinicAdminRepository;
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

/**
 * Production-quality implementation of {@link ClinicService}.
 *
 * <p>Design principles:
 * <ul>
 *   <li>All writes are wrapped in {@code @Transactional} to ensure atomicity.</li>
 *   <li>Duplicate sub-resources are rejected before persisting (existence check).</li>
 *   <li>Soft-delete on {@code Clinic} is handled by Hibernate {@code @SQLDelete}.</li>
 *   <li>No Lombok — explicit getters/setters throughout the domain layer.</li>
 * </ul>
 */
@Service
public class ClinicServiceImpl implements ClinicService {

    // ── Allowed sort-by fields (prevents JPQL injection) ──────────────
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("nameEn", "nameAr", "overallRating", "reviewCount", "createdAt");

    // ── Repositories ──────────────────────────────────────────────────

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;

    @Autowired
    private ClinicBranchRepository clinicBranchRepository;

    @Autowired
    private ClinicOperatingHourRepository hourRepository;

    @Autowired
    private ClinicSpecialtyRepository clinicSpecialtyRepository;

    @Autowired
    private ClinicInsuranceRepository insuranceRepository;

    @Autowired
    private ClinicLanguageRepository clinicLanguageRepository;

    // ── Reference Repositories ────────────────────────────────────────

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private InsuranceProviderRepository insuranceProviderRepository;

    @Autowired
    private LanguageRepository languageRepository;

    // ── Services ──────────────────────────────────────────────────────

    @Autowired
    private FileStorageService fileStorageService;

    // ══════════════════════════════════════════════════════════════════
    // ─── Core Clinic CRUD ─────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Override
    public Page<ClinicResponseDto> searchClinics(ClinicSearchRequest request) {
        String sortField = ALLOWED_SORT_FIELDS.contains(request.getSortBy())
                ? request.getSortBy() : "nameEn";
        Sort.Direction direction = "desc".equalsIgnoreCase(request.getSortDir())
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),
                Sort.by(direction, sortField));

        return clinicRepository.searchClinics(
                trimOrNull(request.getName()),
                request.getSpecialtyId(),
                request.getIsActive(),
                request.getClinicIds(),
                pageable
        ).map(ClinicResponseDto::fromEntity);
    }

    @Override
    public List<ClinicResponseDto> getAllClinics() {
        return clinicRepository.findAll()
                .stream()
                .map(ClinicResponseDto::fromEntity)
                .toList();
    }

    @Override
    public ClinicResponseDto getClinicById(UUID id) {
        Clinic clinic = findClinicOrThrow(id);
        return ClinicResponseDto.fromEntity(clinic);
    }

    @Override
    public ClinicDetailResponse getClinicDetail(UUID id) {
        Clinic clinic = findClinicOrThrow(id);

        ClinicDetailResponse detail = ClinicDetailResponse.fromEntity(clinic);

        detail.setBranches(
                clinicBranchRepository.findByClinic_ClinicId(id)
                        .stream().map(ClinicBranchResponseDto::fromEntity).toList());

        detail.setSpecialties(
                clinicSpecialtyRepository.findByClinic_ClinicId(id)
                        .stream().map(ClinicSpecialtyResponseDto::fromEntity).toList());

        detail.setLanguages(
                clinicLanguageRepository.findByClinic_ClinicId(id)
                        .stream().map(ClinicLanguageResponseDto::fromEntity).toList());

        detail.setInsurances(
                insuranceRepository.findByClinic_ClinicId(id)
                        .stream().map(ClinicInsuranceResponseDto::fromEntity).toList());

        return detail;
    }

    @Transactional
    @Override
    public ClinicResponseDto createClinic(ClinicRequestDto dto, MultipartFile logo,
            CustomUserPrincipal principal) {

        if (clinicRepository.existsByMohLicenseNumber(dto.getMohLicenseNumber())) {
            throw new IllegalArgumentException(
                    "A clinic with MOH license '" + dto.getMohLicenseNumber() + "' already exists.");
        }

        Clinic clinic = new Clinic();
        applyDtoToClinic(clinic, dto);

        if (logo != null && !logo.isEmpty()) {
            clinic.setLogoUrl(storeLogoOrThrow(logo));
        }

        Clinic saved = clinicRepository.save(clinic);

        if (principal != null && principal.getUser().getRole() == com.backend.medconsult.enums.usersAndPatients.UserRole.CLINIC_ADMIN) {
            ClinicAdmin ca = new ClinicAdmin();
            ca.setClinic(saved);
            ca.setUser(principal.getUser());
            ca.setIsPrimary(true);
            clinicAdminRepository.save(ca);
        }

        return ClinicResponseDto.fromEntity(saved);
    }

    @Transactional
    @Override
    public ClinicResponseDto updateClinic(UUID id, ClinicRequestDto dto, MultipartFile logo,
            CustomUserPrincipal principal) {

        Clinic clinic = findClinicOrThrow(id);

        // Check for MOH license collision only when the number actually changed
        if (dto.getMohLicenseNumber() != null
                && !dto.getMohLicenseNumber().equals(clinic.getMohLicenseNumber())
                && clinicRepository.existsByMohLicenseNumber(dto.getMohLicenseNumber())) {
            throw new IllegalArgumentException(
                    "A clinic with MOH license '" + dto.getMohLicenseNumber() + "' already exists.");
        }

        applyPartialDtoToClinic(clinic, dto);

        if (logo != null && !logo.isEmpty()) {
            clinic.setLogoUrl(storeLogoOrThrow(logo));
        }

        return ClinicResponseDto.fromEntity(clinicRepository.save(clinic));
    }

    @Transactional
    @Override
    public void deleteClinic(UUID id, CustomUserPrincipal principal) {
        Clinic clinic = findClinicOrThrow(id);
        // @SQLDelete on the entity triggers a soft-delete UPDATE
        clinicRepository.delete(clinic);
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Branches ─────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Override
    public List<ClinicBranchResponseDto> getClinicBranches(UUID clinicId) {
        return clinicBranchRepository.findByClinic_ClinicId(clinicId)
                .stream()
                .map(ClinicBranchResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public ClinicBranchResponseDto createClinicBranch(UUID clinicId, ClinicBranchRequestDto dto,
            CustomUserPrincipal principal) {

        Clinic clinic = clinicRepository.findById(clinicId)
        .orElseThrow(() -> new RuntimeException("Clinic not found: " + clinicId));

        Locality locality = localityRepository.findById(dto.getLocalityId())
                .orElseThrow(() -> new RuntimeException("Locality not found: " + dto.getLocalityId()));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found: " + dto.getCityId()));

        ClinicBranch branch = new ClinicBranch();
        branch.setClinic(clinic);
        applyDtoToBranch(branch, dto, locality, city);

        return ClinicBranchResponseDto.fromEntity(clinicBranchRepository.save(branch));
    }

    @Transactional
    @Override
    public ClinicBranchResponseDto updateClinicBranch(UUID branchId, ClinicBranchRequestDto dto,
            CustomUserPrincipal principal) {

        ClinicBranch branch = clinicBranchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Clinic branch not found: " + branchId));

        if (dto.getLocalityId() != null) {
            branch.setLocality(localityRepository.findById(dto.getLocalityId())
                    .orElseThrow(() -> new RuntimeException("Locality not found: " + dto.getLocalityId())));
        }
        if (dto.getCityId() != null) {
            branch.setCity(cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new RuntimeException("City not found: " + dto.getCityId())));
        }

        if (dto.getBranchNameEn() != null) branch.setBranchNameEn(dto.getBranchNameEn());
        if (dto.getBranchNameAr() != null) branch.setBranchNameAr(dto.getBranchNameAr());
        if (dto.getAddressLine1() != null) branch.setAddressLine1(dto.getAddressLine1());
        if (dto.getAddressLine2() != null) branch.setAddressLine2(dto.getAddressLine2());
        if (dto.getPostalCode() != null) branch.setPostalCode(dto.getPostalCode());
        if (dto.getLatitude() != null) branch.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) branch.setLongitude(dto.getLongitude());
        if (dto.getMapsUrl() != null) branch.setMapsUrl(dto.getMapsUrl());
        if (dto.getPhone() != null) branch.setPhone(dto.getPhone());
        if (dto.getIsPrimary() != null) branch.setIsPrimary(dto.getIsPrimary());
        if (dto.getIsActive() != null) branch.setIsActive(dto.getIsActive());

        return ClinicBranchResponseDto.fromEntity(clinicBranchRepository.save(branch));
    }

    @Transactional
    @Override
    public void deleteClinicBranch(UUID branchId, CustomUserPrincipal principal) {
        ClinicBranch branch = clinicBranchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Clinic branch not found: " + branchId));
        clinicBranchRepository.delete(branch);
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Operating Hours ──────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Override
    public List<ClinicOperatingHourResponseDto> getBranchHours(UUID branchId) {
        ClinicBranch branch = clinicBranchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Clinic branch not found: " + branchId));
        return hourRepository.findAllByBranch(branch)
                .stream()
                .map(ClinicOperatingHourResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public List<ClinicOperatingHourResponseDto> updateBranchHours(UUID branchId,
            List<ClinicOperatingHourRequestDto> dtos, CustomUserPrincipal principal) {

        ClinicBranch branch = clinicBranchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Clinic branch not found: " + branchId));

        List<ClinicOperatingHour> existing = hourRepository.findAllByBranch(branch);

        for (ClinicOperatingHourRequestDto dto : dtos) {
            ClinicOperatingHour hour = existing.stream()
                    .filter(oh -> oh.getDayOfWeek().equals(dto.getDayOfWeek()))
                    .findFirst()
                    .orElseGet(ClinicOperatingHour::new);

            hour.setBranch(branch);
            hour.setDayOfWeek(dto.getDayOfWeek());
            hour.setOpenTime(dto.getOpenTime());
            hour.setCloseTime(dto.getCloseTime());
            hour.setIsClosed(dto.getIsClosed() != null ? dto.getIsClosed() : false);
            hour.setBreakStart(dto.getBreakStart());
            hour.setBreakEnd(dto.getBreakEnd());
            hour.setNotes(dto.getNotes());

            hourRepository.save(hour);
        }

        return hourRepository.findAllByBranch(branch)
                .stream()
                .map(ClinicOperatingHourResponseDto::fromEntity)
                .toList();
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Specialties ──────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Override
    public List<ClinicSpecialtyResponseDto> getClinicSpecialties(UUID clinicId) {
        return clinicSpecialtyRepository.findByClinic_ClinicId(clinicId)
                .stream()
                .map(ClinicSpecialtyResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public ClinicSpecialtyResponseDto addClinicSpecialty(UUID clinicId, UUID specialtyId,
            CustomUserPrincipal principal) {

        if (clinicSpecialtyRepository.existsByClinic_ClinicIdAndSpecialty_SpecialtyId(clinicId, specialtyId)) {
            throw new IllegalArgumentException("Specialty already linked to this clinic.");
        }

        Clinic clinic = findClinicOrThrow(clinicId);
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty not found: " + specialtyId));

        ClinicSpecialty cs = new ClinicSpecialty();
        cs.setClinic(clinic);
        cs.setSpecialty(specialty);

        return ClinicSpecialtyResponseDto.fromEntity(clinicSpecialtyRepository.save(cs));
    }

    @Transactional
    @Override
    public void deleteClinicSpecialty(UUID clinicId, UUID specialtyId,
            CustomUserPrincipal principal) {

        if (!clinicSpecialtyRepository.existsByClinic_ClinicIdAndSpecialty_SpecialtyId(clinicId, specialtyId)) {
            throw new RuntimeException("Specialty not linked to clinic.");
        }
        clinicSpecialtyRepository.deleteByClinic_ClinicIdAndSpecialty_SpecialtyId(clinicId, specialtyId);
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Insurance ────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Override
    public List<ClinicInsuranceResponseDto> getClinicInsurances(UUID clinicId) {
        return insuranceRepository.findByClinic_ClinicId(clinicId)
                .stream()
                .map(ClinicInsuranceResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public ClinicInsuranceResponseDto addClinicInsurance(UUID clinicId, UUID providerId,
            ClinicInsuranceRequestDto dto, CustomUserPrincipal principal) {

        if (insuranceRepository.existsByClinic_ClinicIdAndProvider_ProviderId(clinicId, providerId)) {
            throw new IllegalArgumentException("Insurance provider already linked to this clinic.");
        }

        Clinic clinic = findClinicOrThrow(clinicId);
        InsuranceProvider provider = insuranceProviderRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Insurance provider not found: " + providerId));

        ClinicInsurance insurance = new ClinicInsurance();
        insurance.setClinic(clinic);
        insurance.setProvider(provider);
        if (dto != null) {
            insurance.setNetworkClass(dto.getNetworkClass());
            if (dto.getIsActive() != null) insurance.setIsActive(dto.getIsActive());
        }

        return ClinicInsuranceResponseDto.fromEntity(insuranceRepository.save(insurance));
    }

    @Transactional
    @Override
    public void deleteClinicInsurance(UUID clinicId, UUID providerId, CustomUserPrincipal principal) {
        if (!insuranceRepository.existsByClinic_ClinicIdAndProvider_ProviderId(clinicId, providerId)) {
            throw new RuntimeException("Insurance provider not linked to clinic.");
        }
        insuranceRepository.deleteByClinic_ClinicIdAndProvider_ProviderId(clinicId, providerId);
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Languages ────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Override
    public List<ClinicLanguageResponseDto> getClinicLanguages(UUID clinicId) {
        return clinicLanguageRepository.findByClinic_ClinicId(clinicId)
                .stream()
                .map(ClinicLanguageResponseDto::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    public ClinicLanguageResponseDto addClinicLanguage(UUID clinicId, UUID languageId,
            CustomUserPrincipal principal) {

        if (clinicLanguageRepository.existsByClinic_ClinicIdAndLanguage_LanguageId(clinicId, languageId)) {
            throw new IllegalArgumentException("Language already linked to this clinic.");
        }

        Clinic clinic = findClinicOrThrow(clinicId);
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found: " + languageId));

        ClinicLanguage cl = new ClinicLanguage();
        cl.setClinic(clinic);
        cl.setLanguage(language);

        return ClinicLanguageResponseDto.fromEntity(clinicLanguageRepository.save(cl));
    }

    @Transactional
    @Override
    public void deleteClinicLanguage(UUID clinicId, UUID languageId, CustomUserPrincipal principal) {
        if (!clinicLanguageRepository.existsByClinic_ClinicIdAndLanguage_LanguageId(clinicId, languageId)) {
            throw new RuntimeException("Language not linked to clinic.");
        }
        clinicLanguageRepository.deleteByClinic_ClinicIdAndLanguage_LanguageId(clinicId, languageId);
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Private helpers ──────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    private Clinic findClinicOrThrow(UUID id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found: " + id));
    }

    /** Full mapping used on create (all fields required). */
    private void applyDtoToClinic(Clinic clinic, ClinicRequestDto dto) {
        clinic.setNameEn(dto.getNameEn());
        clinic.setNameAr(dto.getNameAr());
        clinic.setDescriptionEn(dto.getDescriptionEn());
        clinic.setDescriptionAr(dto.getDescriptionAr());
        clinic.setWebsite(dto.getWebsite());
        clinic.setEmail(dto.getEmail());
        clinic.setPhonePrimary(dto.getPhonePrimary());
        clinic.setPhoneSecondary(dto.getPhoneSecondary());
        clinic.setMohLicenseNumber(dto.getMohLicenseNumber());
        if (dto.getMohVerified() != null) clinic.setMohVerified(dto.getMohVerified());
        clinic.setMohVerifiedAt(dto.getMohVerifiedAt());
        clinic.setNaphiesFacilityId(dto.getNaphiesFacilityId());
        if (dto.getIsActive() != null) clinic.setIsActive(dto.getIsActive());
    }

    /** Partial mapping used on update (null fields are skipped). */
    private void applyPartialDtoToClinic(Clinic clinic, ClinicRequestDto dto) {
        if (dto.getNameEn() != null) clinic.setNameEn(dto.getNameEn());
        if (dto.getNameAr() != null) clinic.setNameAr(dto.getNameAr());
        if (dto.getDescriptionEn() != null) clinic.setDescriptionEn(dto.getDescriptionEn());
        if (dto.getDescriptionAr() != null) clinic.setDescriptionAr(dto.getDescriptionAr());
        if (dto.getWebsite() != null) clinic.setWebsite(dto.getWebsite());
        if (dto.getEmail() != null) clinic.setEmail(dto.getEmail());
        if (dto.getPhonePrimary() != null) clinic.setPhonePrimary(dto.getPhonePrimary());
        if (dto.getPhoneSecondary() != null) clinic.setPhoneSecondary(dto.getPhoneSecondary());
        if (dto.getMohLicenseNumber() != null) clinic.setMohLicenseNumber(dto.getMohLicenseNumber());
        if (dto.getMohVerified() != null) clinic.setMohVerified(dto.getMohVerified());
        if (dto.getMohVerifiedAt() != null) clinic.setMohVerifiedAt(dto.getMohVerifiedAt());
        if (dto.getNaphiesFacilityId() != null) clinic.setNaphiesFacilityId(dto.getNaphiesFacilityId());
        if (dto.getIsActive() != null) clinic.setIsActive(dto.getIsActive());
    }

    private void applyDtoToBranch(ClinicBranch branch, ClinicBranchRequestDto dto,
            Locality locality, City city) {
        branch.setBranchNameEn(dto.getBranchNameEn());
        branch.setBranchNameAr(dto.getBranchNameAr());
        branch.setLocality(locality);
        branch.setCity(city);
        branch.setAddressLine1(dto.getAddressLine1());
        branch.setAddressLine2(dto.getAddressLine2());
        branch.setPostalCode(dto.getPostalCode());
        branch.setLatitude(dto.getLatitude());
        branch.setLongitude(dto.getLongitude());
        branch.setMapsUrl(dto.getMapsUrl());
        branch.setPhone(dto.getPhone());
        branch.setIsPrimary(dto.getIsPrimary() != null ? dto.getIsPrimary() : false);
        branch.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
    }

    private String storeLogoOrThrow(MultipartFile logo) {
        try {
            return fileStorageService.storeFile(logo, "clinic/clinicLogo");
        } catch (IOException e) {
            throw new RuntimeException("Failed to store clinic logo", e);
        }
    }

    private String trimOrNull(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }
}
