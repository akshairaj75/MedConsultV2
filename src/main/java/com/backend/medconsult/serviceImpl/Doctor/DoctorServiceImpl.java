package com.backend.medconsult.serviceImpl.Doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.medconsult.dto.doctor.AppointmentSlotRequestDto;
import com.backend.medconsult.dto.doctor.AppointmentSlotResponseDto;
import com.backend.medconsult.dto.doctor.DoctorClinicRequestDto;
import com.backend.medconsult.dto.doctor.DoctorClinicResponseDto;
import com.backend.medconsult.dto.doctor.DoctorDetailResponse;
import com.backend.medconsult.dto.doctor.DoctorLanguageRequestDto;
import com.backend.medconsult.dto.doctor.DoctorLanguageResponseDto;
import com.backend.medconsult.dto.doctor.DoctorLeaveRequestDto;
import com.backend.medconsult.dto.doctor.DoctorLeaveResponseDto;
import com.backend.medconsult.dto.doctor.DoctorQualificationRequestDto;
import com.backend.medconsult.dto.doctor.DoctorQualificationResponseDto;
import com.backend.medconsult.dto.doctor.DoctorRequestDto;
import com.backend.medconsult.dto.doctor.DoctorResponseDto;
import com.backend.medconsult.dto.doctor.DoctorScheduleRequestDto;
import com.backend.medconsult.dto.doctor.DoctorScheduleResponseDto;
import com.backend.medconsult.dto.doctor.DoctorSpecialtyRequestDto;
import com.backend.medconsult.dto.doctor.DoctorSpecialtyResponseDto;
import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.clinic.ClinicBranch;
import com.backend.medconsult.entity.doctor.AppointmentSlot;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.doctor.DoctorClinic;
import com.backend.medconsult.entity.doctor.DoctorLanguage;
import com.backend.medconsult.entity.doctor.DoctorLeave;
import com.backend.medconsult.entity.doctor.DoctorQualification;
import com.backend.medconsult.entity.doctor.DoctorSchedule;
import com.backend.medconsult.entity.doctor.DoctorSpecialty;
import com.backend.medconsult.entity.references.Language;
import com.backend.medconsult.entity.references.Specialty;
import com.backend.medconsult.entity.references.SubSpecialty;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.doctor.SlotStatus;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.enums.usersAndPatients.UserRole;
import com.backend.medconsult.repository.clinic.ClinicBranchRepository;
import com.backend.medconsult.repository.clinic.ClinicRepository;
import com.backend.medconsult.repository.doctor.AppointmentSlotRepository;
import com.backend.medconsult.repository.doctor.DoctorClinicRepository;
import com.backend.medconsult.repository.doctor.DoctorLanguageRepository;
import com.backend.medconsult.repository.doctor.DoctorLeaveRepository;
import com.backend.medconsult.repository.doctor.DoctorQualificationRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.doctor.DoctorScheduleRepository;
import com.backend.medconsult.repository.doctor.DoctorSpecialtyRepository;
import com.backend.medconsult.repository.references.LanguageRepository;
import com.backend.medconsult.repository.references.SpecialtyRepository;
import com.backend.medconsult.repository.references.SubSpecialtyRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.Doctor.DoctorService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DoctorServiceImpl implements DoctorService {

        // ─── Repositories ──────────────────────────────────────────────────

        @Autowired
        private DoctorRepository doctorRepository;
        @Autowired
        private DoctorClinicRepository doctorClinicRepository;
        @Autowired
        private DoctorLanguageRepository doctorLanguageRepository;
        @Autowired
        private DoctorSpecialtyRepository doctorSpecialtyRepository;
        @Autowired
        private DoctorQualificationRepository doctorQualificationRepository;
        @Autowired
        private DoctorScheduleRepository doctorScheduleRepository;
        @Autowired
        private DoctorLeaveRepository doctorLeaveRepository;
        @Autowired
        private AppointmentSlotRepository appointmentSlotRepository;

        @Autowired
        private ClinicRepository clinicRepository;
        @Autowired
        private ClinicBranchRepository clinicBranchRepository;
        @Autowired
        private LanguageRepository languageRepository;
        @Autowired
        private SpecialtyRepository specialtyRepository;
        @Autowired
        private SubSpecialtyRepository subSpecialtyRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AccessLogService accessLogService;

        // ══════════════════════════════════════════════════════════════════
        // ─── Core Doctor CRUD ─────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        // @Override
        // public Page<DoctorResponseDto> searchDoctors(String name, UUID specialtyId,
        // Boolean isActive, int page, int size, CustomUserPrincipal authUser,
        // HttpServletRequest request) {
        // Pageable pageable = PageRequest.of(page, size);
        // Page<Doctor> docs = doctorRepository.searchDoctors(name, specialtyId,
        // isActive, pageable);
        // return docs.map(DoctorResponseDto::fromEntity);
        // }

        @Override
        public List<DoctorResponseDto> getAllDoctors() {
                List<Doctor> docList = doctorRepository.findAll();
                // if (docList.isEmpty()) {
                // throw new RuntimeException("No doctors found");
                // }
                return docList.stream().map(DoctorResponseDto::fromEntity).toList();
        }

        @Transactional
        @Override
        public DoctorResponseDto addDoctor(DoctorRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                User user = authUser.getUser();
                String moh = dto.getMohRegistrationNumber();

                if (doctorRepository.existsByMohRegistrationNumber(moh)) {
                        throw new RuntimeException("Doctor with this MOH registration number already exists");
                }
                if (doctorRepository.existsByUser(user)) {
                        throw new RuntimeException("Doctor profile already exists for this user");
                }

                Doctor doc = new Doctor();
                doc.setUser(user);
                doc.setMohRegistrationNumber(moh);
                doc.setMohVerified(dto.getMohVerified());
                doc.setTitle(dto.getTitle());
                doc.setBioEn(dto.getBioEn());
                doc.setBioAr(dto.getBioAr());
                doc.setExperienceYears(dto.getExperienceYears());
                doc.setConsultationFeeSar(dto.getConsultationFeeSar());
                Doctor savedDoc = doctorRepository.save(doc);
                user.setRole(UserRole.DOCTOR);
                userRepository.save(user);

                accessLogService.log(user, null, AuditAction.CREATE, ResourceType.DOCTOR,
                                savedDoc.getDoctorId(), AuditOutcome.SUCCESS);

                return DoctorResponseDto.fromEntity(savedDoc);
        }

        @Transactional
        @Override
        public DoctorResponseDto updateDoctor(UUID id, DoctorRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Doctor doc = findDoctorOrThrow(id);
                User user = authUser.getUser();

                if (dto.getMohRegistrationNumber() != null) {
                        String moh = dto.getMohRegistrationNumber();

                        // MOH number uniqueness check (only if changed)
                        if (!doc.getMohRegistrationNumber().equals(moh)
                                        && doctorRepository.existsByMohRegistrationNumber(moh)) {
                                throw new RuntimeException("Another doctor already has this MOH registration number");
                        }
                        doc.setMohRegistrationNumber(moh);
                }
                if (dto.getMohVerified() != null) {
                        doc.setMohVerified(dto.getMohVerified());
                }
                if (dto.getTitle() != null) {
                        doc.setTitle(dto.getTitle());
                }
                if (dto.getBioEn() != null) {
                        doc.setBioEn(dto.getBioEn());
                }
                if (dto.getBioAr() != null) {
                        doc.setBioAr(dto.getBioAr());
                }
                if (dto.getExperienceYears() != null) {
                        doc.setExperienceYears(dto.getExperienceYears());
                }
                if (dto.getConsultationFeeSar() != null) {
                        doc.setConsultationFeeSar(dto.getConsultationFeeSar());
                }
                if (dto.getIsActive() != null) {
                        doc.setIsActive(dto.getIsActive());
                }

                Doctor updated = doctorRepository.save(doc);

                accessLogService.log(user, null, AuditAction.UPDATE, ResourceType.DOCTOR,
                                updated.getDoctorId(), AuditOutcome.SUCCESS);

                return DoctorResponseDto.fromEntity(updated);
        }

        @Transactional
        @Override
        public String deleteDoctor(UUID id, CustomUserPrincipal authUser, HttpServletRequest request) {
                Doctor doc = findDoctorOrThrow(id);
                User user = authUser.getUser();

                doctorRepository.delete(doc);

                accessLogService.log(user, null, AuditAction.DELETE, ResourceType.DOCTOR,
                                doc.getDoctorId(), AuditOutcome.SUCCESS);

                return "Doctor deleted successfully";
        }

        @Override
        public DoctorDetailResponse getDoctorProfile(UUID id, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Doctor doc = findDoctorOrThrow(id);
                User user = authUser.getUser();

                accessLogService.log(user, null, AuditAction.VIEW, ResourceType.DOCTOR,
                                doc.getDoctorId(), AuditOutcome.SUCCESS);

                return DoctorDetailResponse.fromEntity(doc);
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Doctor-Clinic ────────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        @Transactional
        @Override
        public DoctorClinicResponseDto addDoctorClinic(DoctorClinicRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Doctor doctor = findDoctorOrThrow(dto.getDoctorId());
                Clinic clinic = clinicRepository.findById(dto.getClinicId())
                                .orElseThrow(() -> new RuntimeException("Clinic not found: " + dto.getClinicId()));
                ClinicBranch branch = clinicBranchRepository.findById(dto.getBranchId())
                                .orElseThrow(() -> new RuntimeException("Branch not found: " + dto.getBranchId()));

                if (doctorClinicRepository.existsByDoctor_DoctorIdAndBranch_BranchId(
                                doctor.getDoctorId(), branch.getBranchId())) {
                        throw new RuntimeException("Doctor is already assigned to this branch");
                }

                DoctorClinic dc = new DoctorClinic();
                dc.setDoctor(doctor);
                dc.setClinic(clinic);
                dc.setBranch(branch);
                dc.setDepartment(dto.getDepartment());
                dc.setConsultationFeeSar(dto.getConsultationFeeSar());
                dc.setIsPrimary(dto.getIsPrimary() != null ? dto.getIsPrimary() : false);
                dc.setStartDate(dto.getStartDate());
                dc.setEndDate(dto.getEndDate());
                dc.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

                DoctorClinic saved = doctorClinicRepository.save(dc);

                accessLogService.log(authUser.getUser(), null, AuditAction.CREATE, ResourceType.DOCTOR,
                                saved.getDcId(), AuditOutcome.SUCCESS);

                return DoctorClinicResponseDto.fromEntity(saved);
        }

        @Transactional
        @Override
        public DoctorClinicResponseDto updateDoctorClinic(UUID dcId, DoctorClinicRequestDto dto,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorClinic dc = findDcOrThrow(dcId);

                dc.setDepartment(dto.getDepartment());
                dc.setConsultationFeeSar(dto.getConsultationFeeSar());
                if (dto.getIsPrimary() != null) {
                        dc.setIsPrimary(dto.getIsPrimary());
                }
                dc.setStartDate(dto.getStartDate());
                dc.setEndDate(dto.getEndDate());
                if (dto.getIsActive() != null) {
                        dc.setIsActive(dto.getIsActive());
                }

                DoctorClinic updated = doctorClinicRepository.save(dc);

                accessLogService.log(authUser.getUser(), null, AuditAction.UPDATE, ResourceType.DOCTOR,
                                updated.getDcId(), AuditOutcome.SUCCESS);

                return DoctorClinicResponseDto.fromEntity(updated);
        }

        @Transactional
        @Override
        public String removeDoctorClinic(UUID dcId, CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorClinic dc = findDcOrThrow(dcId);

                doctorClinicRepository.delete(dc);

                accessLogService.log(authUser.getUser(), null, AuditAction.DELETE, ResourceType.DOCTOR,
                                dcId, AuditOutcome.SUCCESS);

                return "Doctor-Clinic association removed successfully";
        }

        @Override
        public List<DoctorClinicResponseDto> getDoctorClinics(UUID doctorId, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                findDoctorOrThrow(doctorId);
                return doctorClinicRepository.findByDoctor_DoctorId(doctorId)
                                .stream().map(DoctorClinicResponseDto::fromEntity).toList();
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Specialties ──────────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        @Transactional
        @Override
        public DoctorSpecialtyResponseDto addSpecialty(DoctorSpecialtyRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Doctor doctor = findDoctorOrThrow(dto.getDoctorId());
                Specialty specialty = specialtyRepository.findById(dto.getSpecialtyId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Specialty not found: " + dto.getSpecialtyId()));

                if (doctorSpecialtyRepository.existsByDoctor_DoctorIdAndSpecialty_SpecialtyId(
                                doctor.getDoctorId(), specialty.getSpecialtyId())) {
                        throw new RuntimeException("Doctor already has this specialty assigned");
                }

                // If this one is primary, demote any existing primary
                if (Boolean.TRUE.equals(dto.getIsPrimary())) {
                        doctorSpecialtyRepository.findByDoctor_DoctorIdAndIsPrimaryTrue(doctor.getDoctorId())
                                        .ifPresent(existing -> {
                                                existing.setIsPrimary(false);
                                                doctorSpecialtyRepository.save(existing);
                                        });
                }

                DoctorSpecialty ds = new DoctorSpecialty();
                ds.setDoctor(doctor);
                ds.setSpecialty(specialty);
                ds.setIsPrimary(dto.getIsPrimary() != null ? dto.getIsPrimary() : false);

                if (dto.getSubSpecialtyId() != null) {
                        SubSpecialty sub = subSpecialtyRepository.findById(dto.getSubSpecialtyId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Sub-specialty not found: " + dto.getSubSpecialtyId()));
                        ds.setSubSpecialty(sub);
                }

                DoctorSpecialty saved = doctorSpecialtyRepository.save(ds);

                accessLogService.log(authUser.getUser(), null, AuditAction.CREATE, ResourceType.DOCTOR,
                                saved.getId(), AuditOutcome.SUCCESS);

                return DoctorSpecialtyResponseDto.fromEntity(saved);
        }

        @Transactional
        @Override
        public DoctorSpecialtyResponseDto updateSpecialty(UUID specialtyId, DoctorSpecialtyRequestDto dto,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorSpecialty ds = doctorSpecialtyRepository.findById(specialtyId)
                                .orElseThrow(() -> new RuntimeException("Doctor specialty not found: " + specialtyId));

                // If making primary, demote existing
                if (Boolean.TRUE.equals(dto.getIsPrimary()) && !Boolean.TRUE.equals(ds.getIsPrimary())) {
                        doctorSpecialtyRepository.findByDoctor_DoctorIdAndIsPrimaryTrue(ds.getDoctor().getDoctorId())
                                        .ifPresent(existing -> {
                                                existing.setIsPrimary(false);
                                                doctorSpecialtyRepository.save(existing);
                                        });
                }
                if (dto.getIsPrimary() != null) {
                        ds.setIsPrimary(dto.getIsPrimary());
                }
                if (dto.getSubSpecialtyId() != null) {
                        SubSpecialty sub = subSpecialtyRepository.findById(dto.getSubSpecialtyId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Sub-specialty not found: " + dto.getSubSpecialtyId()));
                        ds.setSubSpecialty(sub);
                }

                DoctorSpecialty updated = doctorSpecialtyRepository.save(ds);

                accessLogService.log(authUser.getUser(), null, AuditAction.UPDATE, ResourceType.DOCTOR,
                                updated.getId(), AuditOutcome.SUCCESS);

                return DoctorSpecialtyResponseDto.fromEntity(updated);
        }

        @Transactional
        @Override
        public String removeSpecialty(UUID specialtyId, CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorSpecialty ds = doctorSpecialtyRepository.findById(specialtyId)
                                .orElseThrow(() -> new RuntimeException("Doctor specialty not found: " + specialtyId));

                doctorSpecialtyRepository.delete(ds);

                accessLogService.log(authUser.getUser(), null, AuditAction.DELETE, ResourceType.DOCTOR,
                                specialtyId, AuditOutcome.SUCCESS);

                return "Specialty removed from doctor successfully";
        }

        @Override
        public List<DoctorSpecialtyResponseDto> getDoctorSpecialties(UUID doctorId, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                findDoctorOrThrow(doctorId);
                return doctorSpecialtyRepository.findByDoctor_DoctorId(doctorId)
                                .stream().map(DoctorSpecialtyResponseDto::fromEntity).toList();
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Languages ────────────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        @Transactional
        @Override
        public DoctorLanguageResponseDto addLanguage(DoctorLanguageRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Doctor doctor = findDoctorOrThrow(dto.getDoctorId());
                Language language = languageRepository.findById(dto.getLanguageId())
                                .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getLanguageId()));

                if (doctorLanguageRepository.existsByDoctor_DoctorIdAndLanguage_LanguageId(
                                doctor.getDoctorId(), language.getLanguageId())) {
                        throw new RuntimeException("Doctor already speaks this language");
                }

                DoctorLanguage dl = new DoctorLanguage();
                dl.setDoctor(doctor);
                dl.setLanguage(language);
                dl.setProficiency(dto.getProficiency());

                DoctorLanguage saved = doctorLanguageRepository.save(dl);

                accessLogService.log(authUser.getUser(), null, AuditAction.CREATE, ResourceType.DOCTOR,
                                saved.getId(), AuditOutcome.SUCCESS);

                return DoctorLanguageResponseDto.fromEntity(saved);
        }

        @Transactional
        @Override
        public String removeLanguage(UUID languageId, CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorLanguage dl = doctorLanguageRepository.findById(languageId)
                                .orElseThrow(() -> new RuntimeException("Doctor language not found: " + languageId));

                doctorLanguageRepository.delete(dl);

                accessLogService.log(authUser.getUser(), null, AuditAction.DELETE, ResourceType.DOCTOR,
                                languageId, AuditOutcome.SUCCESS);

                return "Language removed from doctor successfully";
        }

        @Override
        public List<DoctorLanguageResponseDto> getDoctorLanguages(UUID doctorId, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                findDoctorOrThrow(doctorId);
                return doctorLanguageRepository.findByDoctor_DoctorId(doctorId)
                                .stream().map(DoctorLanguageResponseDto::fromEntity).toList();
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Qualifications ───────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        @Transactional
        @Override
        public DoctorQualificationResponseDto addQualification(DoctorQualificationRequestDto dto,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                Doctor doctor = findDoctorOrThrow(dto.getDoctorId());

                DoctorQualification qual = new DoctorQualification();
                qual.setDoctor(doctor);
                qual.setDegree(dto.getDegree());
                qual.setInstitution(dto.getInstitution());
                qual.setCountry(dto.getCountry());
                qual.setYearObtained(dto.getYearObtained());
                qual.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : (byte) 0);

                DoctorQualification saved = doctorQualificationRepository.save(qual);

                accessLogService.log(authUser.getUser(), null, AuditAction.CREATE, ResourceType.DOCTOR,
                                saved.getQualId(), AuditOutcome.SUCCESS);

                return DoctorQualificationResponseDto.fromEntity(saved);
        }

        @Transactional
        @Override
        public DoctorQualificationResponseDto updateQualification(UUID qualId, DoctorQualificationRequestDto dto,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorQualification qual = doctorQualificationRepository.findById(qualId)
                                .orElseThrow(() -> new RuntimeException("Qualification not found: " + qualId));

                qual.setDegree(dto.getDegree());
                qual.setInstitution(dto.getInstitution());
                qual.setCountry(dto.getCountry());
                qual.setYearObtained(dto.getYearObtained());
                if (dto.getSortOrder() != null) {
                        qual.setSortOrder(dto.getSortOrder());
                }

                DoctorQualification updated = doctorQualificationRepository.save(qual);

                accessLogService.log(authUser.getUser(), null, AuditAction.UPDATE, ResourceType.DOCTOR,
                                updated.getQualId(), AuditOutcome.SUCCESS);

                return DoctorQualificationResponseDto.fromEntity(updated);
        }

        @Transactional
        @Override
        public String removeQualification(UUID qualId, CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorQualification qual = doctorQualificationRepository.findById(qualId)
                                .orElseThrow(() -> new RuntimeException("Qualification not found: " + qualId));

                doctorQualificationRepository.delete(qual);

                accessLogService.log(authUser.getUser(), null, AuditAction.DELETE, ResourceType.DOCTOR,
                                qualId, AuditOutcome.SUCCESS);

                return "Qualification removed successfully";
        }

        @Override
        public List<DoctorQualificationResponseDto> getDoctorQualifications(UUID doctorId,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                findDoctorOrThrow(doctorId);
                return doctorQualificationRepository.findByDoctor_DoctorIdOrderBySortOrderAsc(doctorId)
                                .stream().map(DoctorQualificationResponseDto::fromEntity).toList();
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Schedules ────────────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        @Transactional
        @Override
        public DoctorScheduleResponseDto addSchedule(DoctorScheduleRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                DoctorClinic dc = findDcOrThrow(dto.getDcId());

                // Basic validation: end time must be after start time
                if (dto.getStartTime() != null && dto.getEndTime() != null
                                && !dto.getEndTime().isAfter(dto.getStartTime())) {
                        throw new RuntimeException("End time must be after start time");
                }

                DoctorSchedule schedule = new DoctorSchedule();
                schedule.setDoctorClinic(dc);
                schedule.setDayOfWeek(dto.getDayOfWeek());
                schedule.setStartTime(dto.getStartTime());
                schedule.setEndTime(dto.getEndTime());
                schedule.setSlotDurationMin(dto.getSlotDurationMin() != null ? dto.getSlotDurationMin() : (short) 30);
                schedule.setMaxPatients(dto.getMaxPatients());
                schedule.setSessionType(dto.getSessionType());
                schedule.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
                schedule.setValidFrom(dto.getValidFrom());
                schedule.setValidUntil(dto.getValidUntil());

                DoctorSchedule saved = doctorScheduleRepository.save(schedule);

                accessLogService.log(authUser.getUser(), null, AuditAction.CREATE, ResourceType.DOCTOR,
                                saved.getScheduleId(), AuditOutcome.SUCCESS);

                return DoctorScheduleResponseDto.fromEntity(saved);
        }

        @Transactional
        @Override
        public DoctorScheduleResponseDto updateSchedule(UUID scheduleId, DoctorScheduleRequestDto dto,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                                .orElseThrow(() -> new RuntimeException("Schedule not found: " + scheduleId));

                if (dto.getStartTime() != null && dto.getEndTime() != null
                                && !dto.getEndTime().isAfter(dto.getStartTime())) {
                        throw new RuntimeException("End time must be after start time");
                }

                schedule.setDayOfWeek(dto.getDayOfWeek());
                schedule.setStartTime(dto.getStartTime());
                schedule.setEndTime(dto.getEndTime());
                if (dto.getSlotDurationMin() != null) {
                        schedule.setSlotDurationMin(dto.getSlotDurationMin());
                }
                schedule.setMaxPatients(dto.getMaxPatients());
                if (dto.getSessionType() != null) {
                        schedule.setSessionType(dto.getSessionType());
                }
                if (dto.getIsActive() != null) {
                        schedule.setIsActive(dto.getIsActive());
                }
                schedule.setValidFrom(dto.getValidFrom());
                schedule.setValidUntil(dto.getValidUntil());

                DoctorSchedule updated = doctorScheduleRepository.save(schedule);

                accessLogService.log(authUser.getUser(), null, AuditAction.UPDATE, ResourceType.DOCTOR,
                                updated.getScheduleId(), AuditOutcome.SUCCESS);

                return DoctorScheduleResponseDto.fromEntity(updated);
        }

        @Transactional
        @Override
        public String removeSchedule(UUID scheduleId, CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                                .orElseThrow(() -> new RuntimeException("Schedule not found: " + scheduleId));

                doctorScheduleRepository.delete(schedule);

                accessLogService.log(authUser.getUser(), null, AuditAction.DELETE, ResourceType.DOCTOR,
                                scheduleId, AuditOutcome.SUCCESS);

                return "Schedule removed successfully";
        }

        @Override
        public List<DoctorScheduleResponseDto> getDcSchedules(UUID dcId, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                findDcOrThrow(dcId);
                return doctorScheduleRepository.findByDoctorClinic_DcIdOrderByDayOfWeekAscStartTimeAsc(dcId)
                                .stream().map(DoctorScheduleResponseDto::fromEntity).toList();
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Leave ────────────────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        @Transactional
        @Override
        public DoctorLeaveResponseDto addLeave(DoctorLeaveRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                DoctorClinic dc = findDcOrThrow(dto.getDcId());

                if (dto.getStartDate() == null || dto.getEndDate() == null) {
                        throw new RuntimeException("Start date and end date are required for leave");
                }
                if (dto.getEndDate().isBefore(dto.getStartDate())) {
                        throw new RuntimeException("End date must not be before start date");
                }

                // Overlap check
                List<?> overlapping = doctorLeaveRepository
                                .findByDoctorClinic_DcIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                                                dc.getDcId(), dto.getEndDate(), dto.getStartDate());
                if (!overlapping.isEmpty()) {
                        throw new RuntimeException(
                                        "Leave overlaps with an existing leave record for this doctor-clinic");
                }

                DoctorLeave leave = new DoctorLeave();
                leave.setDoctorClinic(dc);
                leave.setLeaveType(dto.getLeaveType());
                leave.setStartDate(dto.getStartDate());
                leave.setEndDate(dto.getEndDate());
                leave.setIsApproved(dto.getIsApproved() != null ? dto.getIsApproved() : false);
                leave.setNotes(dto.getNotes());

                DoctorLeave saved = doctorLeaveRepository.save(leave);

                accessLogService.log(authUser.getUser(), null, AuditAction.CREATE, ResourceType.DOCTOR,
                                saved.getLeaveId(), AuditOutcome.SUCCESS);

                return DoctorLeaveResponseDto.fromEntity(saved);
        }

        @Transactional
        @Override
        public DoctorLeaveResponseDto updateLeave(UUID leaveId, DoctorLeaveRequestDto dto,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorLeave leave = doctorLeaveRepository.findById(leaveId)
                                .orElseThrow(() -> new RuntimeException("Leave record not found: " + leaveId));

                if (dto.getStartDate() != null && dto.getEndDate() != null
                                && dto.getEndDate().isBefore(dto.getStartDate())) {
                        throw new RuntimeException("End date must not be before start date");
                }

                if (dto.getLeaveType() != null) {
                        leave.setLeaveType(dto.getLeaveType());
                }
                if (dto.getStartDate() != null) {
                        leave.setStartDate(dto.getStartDate());
                }
                if (dto.getEndDate() != null) {
                        leave.setEndDate(dto.getEndDate());
                }
                if (dto.getIsApproved() != null) {
                        leave.setIsApproved(dto.getIsApproved());
                }
                leave.setNotes(dto.getNotes());

                DoctorLeave updated = doctorLeaveRepository.save(leave);

                accessLogService.log(authUser.getUser(), null, AuditAction.UPDATE, ResourceType.DOCTOR,
                                updated.getLeaveId(), AuditOutcome.SUCCESS);

                return DoctorLeaveResponseDto.fromEntity(updated);
        }

        @Transactional
        @Override
        public String removeLeave(UUID leaveId, CustomUserPrincipal authUser, HttpServletRequest request) {
                DoctorLeave leave = doctorLeaveRepository.findById(leaveId)
                                .orElseThrow(() -> new RuntimeException("Leave record not found: " + leaveId));

                doctorLeaveRepository.delete(leave);

                accessLogService.log(authUser.getUser(), null, AuditAction.DELETE, ResourceType.DOCTOR,
                                leaveId, AuditOutcome.SUCCESS);

                return "Leave record removed successfully";
        }

        @Override
        public List<DoctorLeaveResponseDto> getDcLeave(UUID dcId, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                findDcOrThrow(dcId);
                return doctorLeaveRepository.findByDoctorClinic_DcIdOrderByStartDateDesc(dcId)
                                .stream().map(DoctorLeaveResponseDto::fromEntity).toList();
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Appointment Slots ────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        @Transactional
        @Override
        public AppointmentSlotResponseDto addSlot(AppointmentSlotRequestDto dto, CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                DoctorClinic dc = findDcOrThrow(dto.getDcId());
                DoctorSchedule schedule = doctorScheduleRepository.findById(dto.getScheduleId())
                                .orElseThrow(() -> new RuntimeException("Schedule not found: " + dto.getScheduleId()));

                // Guard against duplicate slot
                if (dto.getSlotDate() != null && dto.getStartTime() != null
                                && appointmentSlotRepository.existsByDoctorClinic_DcIdAndSlotDateAndStartTime(
                                                dc.getDcId(), dto.getSlotDate(), dto.getStartTime())) {
                        throw new RuntimeException("A slot already exists for this dc/date/time combination");
                }

                AppointmentSlot slot = new AppointmentSlot();
                slot.setDoctorClinic(dc);
                slot.setSchedule(schedule);
                slot.setSlotDate(dto.getSlotDate());
                slot.setStartTime(dto.getStartTime());
                slot.setEndTime(dto.getEndTime());
                slot.setSessionType(dto.getSessionType() != null ? dto.getSessionType() : schedule.getSessionType());
                slot.setStatus(dto.getStatus() != null ? dto.getStatus() : SlotStatus.AVAILABLE);

                AppointmentSlot saved = appointmentSlotRepository.save(slot);

                accessLogService.log(authUser.getUser(), null, AuditAction.CREATE, ResourceType.DOCTOR,
                                saved.getSlotId(), AuditOutcome.SUCCESS);

                return AppointmentSlotResponseDto.fromEntity(saved);
        }

        @Transactional
        @Override
        public AppointmentSlotResponseDto updateSlot(UUID slotId, AppointmentSlotRequestDto dto,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                                .orElseThrow(() -> new RuntimeException("Appointment slot not found: " + slotId));

                // Cannot modify a booked slot
                if (slot.getStatus() == SlotStatus.BOOKED) {
                        throw new RuntimeException("Cannot modify a booked slot. Cancel the appointment first.");
                }

                if (dto.getSlotDate() != null) {
                        slot.setSlotDate(dto.getSlotDate());
                }
                if (dto.getStartTime() != null) {
                        slot.setStartTime(dto.getStartTime());
                }
                if (dto.getEndTime() != null) {
                        slot.setEndTime(dto.getEndTime());
                }
                if (dto.getSessionType() != null) {
                        slot.setSessionType(dto.getSessionType());
                }
                if (dto.getStatus() != null) {
                        slot.setStatus(dto.getStatus());
                }

                AppointmentSlot updated = appointmentSlotRepository.save(slot);

                accessLogService.log(authUser.getUser(), null, AuditAction.UPDATE, ResourceType.DOCTOR,
                                updated.getSlotId(), AuditOutcome.SUCCESS);

                return AppointmentSlotResponseDto.fromEntity(updated);
        }

        @Transactional
        @Override
        public String removeSlot(UUID slotId, CustomUserPrincipal authUser, HttpServletRequest request) {
                AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                                .orElseThrow(() -> new RuntimeException("Appointment slot not found: " + slotId));

                if (slot.getStatus() == SlotStatus.BOOKED) {
                        throw new RuntimeException("Cannot delete a booked slot. Cancel the appointment first.");
                }

                appointmentSlotRepository.delete(slot);

                accessLogService.log(authUser.getUser(), null, AuditAction.DELETE, ResourceType.DOCTOR,
                                slotId, AuditOutcome.SUCCESS);

                return "Appointment slot removed successfully";
        }

        @Override
        public List<AppointmentSlotResponseDto> getAvailableSlots(UUID dcId, LocalDate date,
                        CustomUserPrincipal authUser, HttpServletRequest request) {
                findDcOrThrow(dcId);
                LocalDate targetDate = (date != null) ? date : LocalDate.now();

                return appointmentSlotRepository
                                .findByDoctorClinic_DcIdAndSlotDateAndStatus(dcId, targetDate, SlotStatus.AVAILABLE)
                                .stream().map(AppointmentSlotResponseDto::fromEntity).toList();
        }

        // ══════════════════════════════════════════════════════════════════
        // ─── Private Helpers ──────────────────────────────────────────────
        // ══════════════════════════════════════════════════════════════════

        private Doctor findDoctorOrThrow(UUID id) {
                return doctorRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Doctor not found: " + id));
        }

        private DoctorClinic findDcOrThrow(UUID dcId) {
                return doctorClinicRepository.findById(dcId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Doctor-Clinic association not found: " + dcId));
        }
}
