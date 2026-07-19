package com.backend.medconsult.serviceImpl.clinic;

import com.backend.medconsult.dto.clinic.ClinicBranchRequestDto;
import com.backend.medconsult.dto.clinic.ClinicBranchResponseDto;
import com.backend.medconsult.dto.clinic.ClinicDetailResponse;
import com.backend.medconsult.dto.clinic.ClinicRequestDto;
import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.dto.clinic.ClinicSearchRequest;
import com.backend.medconsult.dto.clinic.ClinicSpecialtyResponseDto;
import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.clinic.ClinicBranch;
import com.backend.medconsult.entity.clinic.ClinicSpecialty;
import com.backend.medconsult.entity.references.City;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ClinicServiceImpl}.
 *
 * All external dependencies are mocked so no database or Spring context is needed.
 */
@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    // ── SUT ────────────────────────────────────────────────────────────
    @InjectMocks
    private ClinicServiceImpl clinicService;

    // ── Mocks ──────────────────────────────────────────────────────────
    @Mock private ClinicRepository clinicRepository;
    @Mock private ClinicBranchRepository clinicBranchRepository;
    @Mock private ClinicOperatingHourRepository hourRepository;
    @Mock private ClinicSpecialtyRepository clinicSpecialtyRepository;
    @Mock private ClinicInsuranceRepository insuranceRepository;
    @Mock private ClinicLanguageRepository clinicLanguageRepository;
    @Mock private LocalityRepository localityRepository;
    @Mock private CityRepository cityRepository;
    @Mock private SpecialtyRepository specialtyRepository;
    @Mock private InsuranceProviderRepository insuranceProviderRepository;
    @Mock private LanguageRepository languageRepository;
    @Mock private FileStorageService fileStorageService;
    @Mock private CustomUserPrincipal principal;

    // ── Fixtures ───────────────────────────────────────────────────────
    private UUID clinicId;
    private Clinic clinic;

    @BeforeEach
    void setUp() {
        clinicId = UUID.randomUUID();

        clinic = new Clinic();
        clinic.setClinicId(clinicId);
        clinic.setNameEn("Test Clinic EN");
        clinic.setNameAr("Test Clinic AR");
        clinic.setPhonePrimary("+966500000000");
        clinic.setMohLicenseNumber("MOH-001");
        clinic.setOverallRating(BigDecimal.ZERO);
        clinic.setReviewCount(0);
        clinic.setIsActive(true);
        clinic.setMohVerified(false);
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── searchClinics ────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("searchClinics")
    class SearchClinicsTests {

        @Test
        @DisplayName("returns paged results when clinics exist")
        void shouldReturnPagedResults() {
            Page<Clinic> mockPage = new PageImpl<>(List.of(clinic));
            when(clinicRepository.searchClinics(isNull(), isNull(), isNull(), any(Pageable.class)))
                    .thenReturn(mockPage);

            ClinicSearchRequest req = new ClinicSearchRequest();
            Page<ClinicResponseDto> result = clinicService.searchClinics(req);

            assertThat(result.getTotalElements()).isEqualTo(1);
            assertThat(result.getContent().get(0).getNameEn()).isEqualTo("Test Clinic EN");
        }

        @Test
        @DisplayName("defaults to safe sort field when invalid sortBy provided")
        void shouldDefaultToSafeSort() {
            Page<Clinic> mockPage = new PageImpl<>(List.of());
            when(clinicRepository.searchClinics(any(), any(), any(), any(Pageable.class)))
                    .thenReturn(mockPage);

            ClinicSearchRequest req = new ClinicSearchRequest();
            req.setSortBy("DROP TABLE clinics; --");   // injection attempt
            req.setSortDir("desc");

            Page<ClinicResponseDto> result = clinicService.searchClinics(req);
            assertThat(result).isNotNull();
        }
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── getClinicById ────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("getClinicById")
    class GetClinicByIdTests {

        @Test
        @DisplayName("returns DTO when clinic exists")
        void shouldReturnDtoWhenExists() {
            when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));

            ClinicResponseDto result = clinicService.getClinicById(clinicId);

            assertThat(result.getClinicId()).isEqualTo(clinicId);
            assertThat(result.getNameEn()).isEqualTo("Test Clinic EN");
        }

        @Test
        @DisplayName("throws RuntimeException when clinic not found")
        void shouldThrowWhenNotFound() {
            when(clinicRepository.findById(clinicId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clinicService.getClinicById(clinicId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("not found");
        }
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── getClinicDetail ──────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("getClinicDetail")
    class GetClinicDetailTests {

        @Test
        @DisplayName("returns full detail with empty sub-resource lists")
        void shouldReturnDetailWithLists() {
            when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));
            when(clinicBranchRepository.findByClinic_ClinicId(clinicId)).thenReturn(List.of());
            when(clinicSpecialtyRepository.findByClinic_ClinicId(clinicId)).thenReturn(List.of());
            when(clinicLanguageRepository.findByClinic_ClinicId(clinicId)).thenReturn(List.of());
            when(insuranceRepository.findByClinic_ClinicId(clinicId)).thenReturn(List.of());

            ClinicDetailResponse result = clinicService.getClinicDetail(clinicId);

            assertThat(result.getClinicId()).isEqualTo(clinicId);
            assertThat(result.getBranches()).isEmpty();
            assertThat(result.getSpecialties()).isEmpty();
        }
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── createClinic ─────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("createClinic")
    class CreateClinicTests {

        @Test
        @DisplayName("saves and returns DTO when MOH license is unique")
        void shouldSaveWhenLicenseIsUnique() {
            when(clinicRepository.existsByMohLicenseNumber("MOH-001")).thenReturn(false);
            when(clinicRepository.save(any(Clinic.class))).thenReturn(clinic);

            ClinicRequestDto dto = buildDto("MOH-001");
            ClinicResponseDto result = clinicService.createClinic(dto, null, principal);

            assertThat(result.getMohLicenseNumber()).isEqualTo("MOH-001");
            verify(clinicRepository).save(any(Clinic.class));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when MOH license already exists")
        void shouldThrowWhenDuplicateLicense() {
            when(clinicRepository.existsByMohLicenseNumber("MOH-001")).thenReturn(true);

            ClinicRequestDto dto = buildDto("MOH-001");
            assertThatThrownBy(() -> clinicService.createClinic(dto, null, principal))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("MOH-001");

            verify(clinicRepository, never()).save(any());
        }

        private ClinicRequestDto buildDto(String license) {
            ClinicRequestDto dto = new ClinicRequestDto();
            dto.setNameEn("Test Clinic EN");
            dto.setNameAr("Test Clinic AR");
            dto.setPhonePrimary("+966500000000");
            dto.setMohLicenseNumber(license);
            return dto;
        }
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── deleteClinic ─────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("deleteClinic")
    class DeleteClinicTests {

        @Test
        @DisplayName("soft-deletes clinic when it exists")
        void shouldDeleteWhenExists() {
            when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));

            clinicService.deleteClinic(clinicId, principal);

            verify(clinicRepository).delete(clinic);
        }

        @Test
        @DisplayName("throws when clinic not found")
        void shouldThrowWhenNotFound() {
            when(clinicRepository.findById(clinicId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clinicService.deleteClinic(clinicId, principal))
                    .isInstanceOf(RuntimeException.class);
        }
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── createClinicBranch ───────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("createClinicBranch")
    class CreateClinicBranchTests {

        @Test
        @DisplayName("saves branch and returns DTO")
        void shouldSaveBranch() {
            UUID localityId = UUID.randomUUID();
            UUID cityId = UUID.randomUUID();

            Locality locality = new Locality();
            City city = new City();

            ClinicBranch branch = new ClinicBranch();
            branch.setBranchId(UUID.randomUUID());
            branch.setClinic(clinic);
            branch.setBranchNameEn("Main Branch");
            branch.setBranchNameAr("الفرع الرئيسي");
            branch.setLocality(locality);
            branch.setCity(city);
            branch.setAddressLine1("123 Main St");

            when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));
            when(localityRepository.findById(localityId)).thenReturn(Optional.of(locality));
            when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
            when(clinicBranchRepository.save(any(ClinicBranch.class))).thenReturn(branch);

            ClinicBranchRequestDto dto = new ClinicBranchRequestDto();
            dto.setClinicId(clinicId);
            dto.setLocalityId(localityId);
            dto.setCityId(cityId);
            dto.setBranchNameEn("Main Branch");
            dto.setBranchNameAr("الفرع الرئيسي");
            dto.setAddressLine1("123 Main St");

            ClinicBranchResponseDto result = clinicService.createClinicBranch(clinicId, dto, principal);

            assertThat(result).isNotNull();
            assertThat(result.getBranchNameEn()).isEqualTo("Main Branch");
            verify(clinicBranchRepository).save(any(ClinicBranch.class));
        }
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── addClinicSpecialty ───────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("addClinicSpecialty")
    class AddClinicSpecialtyTests {

        private UUID specialtyId;

        @BeforeEach
        void setUp2() {
            specialtyId = UUID.randomUUID();
        }

        @Test
        @DisplayName("links specialty when not already linked")
        void shouldLinkWhenNotDuplicate() {
            Specialty specialty = new Specialty();
            ClinicSpecialty cs = new ClinicSpecialty();
            cs.setClinic(clinic);
            cs.setSpecialty(specialty);

            when(clinicSpecialtyRepository
                    .existsByClinic_ClinicIdAndSpecialty_SpecialtyId(clinicId, specialtyId))
                    .thenReturn(false);
            when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));
            when(specialtyRepository.findById(specialtyId)).thenReturn(Optional.of(specialty));
            when(clinicSpecialtyRepository.save(any(ClinicSpecialty.class))).thenReturn(cs);

            ClinicSpecialtyResponseDto result =
                    clinicService.addClinicSpecialty(clinicId, specialtyId, principal);

            assertThat(result).isNotNull();
            verify(clinicSpecialtyRepository).save(any(ClinicSpecialty.class));
        }

        @Test
        @DisplayName("throws IllegalArgumentException when specialty already linked")
        void shouldThrowWhenDuplicate() {
            when(clinicSpecialtyRepository
                    .existsByClinic_ClinicIdAndSpecialty_SpecialtyId(clinicId, specialtyId))
                    .thenReturn(true);

            assertThatThrownBy(() ->
                    clinicService.addClinicSpecialty(clinicId, specialtyId, principal))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("already linked");

            verify(clinicSpecialtyRepository, never()).save(any());
        }
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── deleteClinicBranch ───────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("deleteClinicBranch")
    class DeleteClinicBranchTests {

        @Test
        @DisplayName("deletes branch when it exists")
        void shouldDeleteBranch() {
            UUID branchId = UUID.randomUUID();
            ClinicBranch branch = new ClinicBranch();
            branch.setBranchId(branchId);

            when(clinicBranchRepository.findById(branchId)).thenReturn(Optional.of(branch));

            clinicService.deleteClinicBranch(branchId, principal);

            verify(clinicBranchRepository).delete(branch);
        }

        @Test
        @DisplayName("throws RuntimeException when branch not found")
        void shouldThrowWhenNotFound() {
            UUID branchId = UUID.randomUUID();
            when(clinicBranchRepository.findById(branchId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clinicService.deleteClinicBranch(branchId, principal))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("not found");
        }
    }
}
