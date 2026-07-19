package com.backend.medconsult.serviceImpl.patient;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.medconsult.dto.patient.PatientAllergyRequestDto;
import com.backend.medconsult.dto.patient.PatientAllergyResponseDto;
import com.backend.medconsult.dto.patient.PatientChronicConditionRequestDto;
import com.backend.medconsult.dto.patient.PatientChronicConditionResponseDto;
import com.backend.medconsult.dto.patient.PatientHealthProfileRequestDto;
import com.backend.medconsult.dto.patient.PatientHealthProfileResponseDto;
import com.backend.medconsult.dto.patient.PatientRequestDto;
import com.backend.medconsult.dto.patient.PatientResponseDto;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.PatientAllergy;
import com.backend.medconsult.entity.usersAndPatients.PatientChronicCondition;
import com.backend.medconsult.entity.usersAndPatients.PatientHealthProfile;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.DataScope;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientAllergyRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientChronicConditionRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientHealthProfileRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.patient.PatientService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import com.backend.medconsult.service.platformAndCompliance.PrivacyGuardService;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccessLogService accessLogService;

    @Autowired
    PatientChronicConditionRepository patientChronicConditionRepository;

    @Autowired
    PatientHealthProfileRepository patientHealthProfileRepository;

    @Autowired
    PatientAllergyRepository patientAllergyRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PrivacyGuardService privacyGuardService;

    @Transactional
    @Override
    public PatientResponseDto createProfile(CustomUserPrincipal principal, PatientRequestDto request) {

        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (patientRepository.existsByUser_UserId(user.getUserId())) {
            throw new RuntimeException("Patient profile already exists for this user");
        }
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setBloodType(request.getBloodType());
        patient.setNationalId(request.getNationalId());
        patient.setNationality(request.getNationality());
        patient.setMaritalStatus(request.getMaritalStatus());
        patient.setEmergencyContactName(request.getEmergencyContactName());
        patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        patient.setNotes(request.getNotes());
        Patient saved = patientRepository.save(patient);

        accessLogService.log(
                user,
                saved,
                AuditAction.CREATE,
                ResourceType.PATIENT,
                saved.getPatientId(),
                AuditOutcome.SUCCESS);

        return PatientResponseDto.fromEntity(saved);
    }

    @Override
    public PatientResponseDto getMyProfile(CustomUserPrincipal principal) {

        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        return PatientResponseDto.fromEntity(patient);
    }

    @Transactional
    @Override
    public PatientResponseDto updateProfile(CustomUserPrincipal principal, PatientRequestDto request) {
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Patient patient = patientRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        if (request.getDateOfBirth() != null) {
            patient.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getBloodType() != null) {
            patient.setBloodType(request.getBloodType());
        }
        if (request.getNationalId() != null) {
            patient.setNationalId(request.getNationalId());
        }
        if (request.getNationality() != null) {
            patient.setNationality(request.getNationality());
        }
        if (request.getMaritalStatus() != null) {
            patient.setMaritalStatus(request.getMaritalStatus());
        }
        if (request.getEmergencyContactName() != null) {
            patient.setEmergencyContactName(request.getEmergencyContactName());
        }
        if (request.getEmergencyContactPhone() != null) {
            patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        }
        if (request.getNotes() != null) {
            patient.setNotes(request.getNotes());
        }
        Patient saved = patientRepository.save(patient);

        accessLogService.log(
                user,
                saved,
                AuditAction.UPDATE,
                ResourceType.PATIENT,
                saved.getPatientId(),
                AuditOutcome.SUCCESS);

        return PatientResponseDto.fromEntity(saved);
    }

    @Override
    public PatientHealthProfileResponseDto getHealthProfile(CustomUserPrincipal principal) {
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        PatientHealthProfile res = patientHealthProfileRepository.findByPatient(patient)
                .orElseThrow(() -> new RuntimeException("Patient health profile not found for this user"));

        accessLogService.log(
                user,
                null,
                AuditAction.VIEW,
                ResourceType.PATIENT,
                res.getProfileId(),
                AuditOutcome.SUCCESS);

        return PatientHealthProfileResponseDto.fromEntity(res);
    }

    @Transactional
    @Override
    public PatientHealthProfileResponseDto addHealthProfile(CustomUserPrincipal principal,
            PatientHealthProfileRequestDto request) {
        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        if (patientHealthProfileRepository.existsByPatient(patient)) {
            throw new RuntimeException("Patient health profile already exists for this user");
        }

        PatientHealthProfile profile = new PatientHealthProfile();
        profile.setPatient(patient);
        profile.setWeightKg(request.getWeightKg());
        profile.setHeightCm(request.getHeightCm());
        profile.setSmokingStatus(request.getSmokingStatus());
        profile.setAlcoholStatus(request.getAlcoholStatus());
        profile.setSurgicalHistory(request.getSurgicalHistory());
        profile.setFamilyHistory(request.getFamilyHistory());
        profile.setAdditionalNotes(request.getAdditionalNotes());
        PatientHealthProfile saved = patientHealthProfileRepository.save(profile);

        // accessLogService.log(
        // user,
        // patient,
        // AuditAction.CREATE,
        // ResourceType.PATIENT,
        // saved.getProfileId(),
        // AuditOutcome.SUCCESS);

        return PatientHealthProfileResponseDto.fromEntity(saved);
    }

    @Transactional
    @Override
    public PatientHealthProfileResponseDto updateHealthProfile(CustomUserPrincipal principal,
            PatientHealthProfileRequestDto request) {

        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        PatientHealthProfile profile = patientHealthProfileRepository.findByPatient(patient)
                .orElseThrow(() -> new RuntimeException("Patient health profile not found for this user"));

        if (request.getWeightKg() != null) {
            profile.setWeightKg(request.getWeightKg());
        }
        if (request.getHeightCm() != null) {
            profile.setHeightCm(request.getHeightCm());
        }
        if (request.getSmokingStatus() != null) {
            profile.setSmokingStatus(request.getSmokingStatus());
        }
        if (request.getAlcoholStatus() != null) {
            profile.setAlcoholStatus(request.getAlcoholStatus());
        }
        if (request.getSurgicalHistory() != null) {
            profile.setSurgicalHistory(request.getSurgicalHistory());
        }
        if (request.getFamilyHistory() != null) {
            profile.setFamilyHistory(request.getFamilyHistory());
        }
        if (request.getAdditionalNotes() != null) {
            profile.setAdditionalNotes(request.getAdditionalNotes());
        }
        PatientHealthProfile saved = patientHealthProfileRepository.save(profile);

        return PatientHealthProfileResponseDto.fromEntity(saved);
    }

    @Override
    public List<PatientAllergyResponseDto> getAllergies(CustomUserPrincipal principal) {
        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        List<PatientAllergy> allergies = patientAllergyRepository.findByPatient(patient);

        return allergies.stream().map(PatientAllergyResponseDto::fromEntity).toList();
    }

    @Transactional
    @Override
    public PatientAllergyResponseDto addAllergy(CustomUserPrincipal principal, PatientAllergyRequestDto request) {
        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        PatientAllergy allergy = new PatientAllergy();
        allergy.setPatient(patient);
        allergy.setAllergen(request.getAllergen());
        allergy.setAllergyType(request.getAllergyType());
        allergy.setReaction(request.getReaction());
        allergy.setSeverity(request.getSeverity());
        allergy.setConfirmed(request.getConfirmed());
        allergy.setRecordedBy(principal.getUser());
        PatientAllergy saved = patientAllergyRepository.save(allergy);

        return PatientAllergyResponseDto.fromEntity(saved);
    }

    @Transactional
    @Override
    public PatientAllergyResponseDto updateAllergy(CustomUserPrincipal principal, UUID allergyId,
            PatientAllergyRequestDto request) {

        PatientAllergy alr = patientAllergyRepository.findById(allergyId)
                .orElseThrow(() -> new RuntimeException("Patient allergy not found for this user"));

        if (request.getAllergen() != null) {
            alr.setAllergen(request.getAllergen());
        }
        if (request.getAllergyType() != null) {
            alr.setAllergyType(request.getAllergyType());
        }
        if (request.getReaction() != null) {
            alr.setReaction(request.getReaction());
        }
        if (request.getSeverity() != null) {
            alr.setSeverity(request.getSeverity());
        }
        if (request.getConfirmed() != null) {
            alr.setConfirmed(request.getConfirmed());
        }
        PatientAllergy saved = patientAllergyRepository.save(alr);
        return PatientAllergyResponseDto.fromEntity(saved);
    }

    @Transactional
    @Override
    public void deleteAllergy(CustomUserPrincipal principal, UUID allergyId) {

        PatientAllergy alr = patientAllergyRepository.findById(allergyId)
                .orElseThrow(() -> new RuntimeException("Patient allergy not found for this user"));

        patientAllergyRepository.delete(alr);
    }

    @Override
    public List<PatientChronicConditionResponseDto> getChronicConditions(CustomUserPrincipal principal) {
        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        List<PatientChronicCondition> chronicConditions = patientChronicConditionRepository.findByPatient(patient);

        return chronicConditions.stream().map(PatientChronicConditionResponseDto::fromEntity).toList();
    }

    @Transactional
    @Override
    public PatientChronicConditionResponseDto addChronicCondition(CustomUserPrincipal principal,
            PatientChronicConditionRequestDto request) {
        Patient patient = patientRepository.findByUser_UserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Patient profile not found for this user"));

        PatientChronicCondition chronicCondition = new PatientChronicCondition();
        chronicCondition.setPatient(patient);
        chronicCondition.setIcd10Code(request.getIcd10Code());
        chronicCondition.setConditionName(request.getConditionName());
        chronicCondition.setDiagnosisDate(request.getDiagnosisDate());
        chronicCondition.setStatus(request.getStatus());

        if (request.getManagingDoctorId() != null) {
            Doctor managingDoctor = doctorRepository.findById(request.getManagingDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found for this user"));

            chronicCondition.setManagingDoctor(managingDoctor);
        }

        chronicCondition.setNotes(request.getNotes());
        PatientChronicCondition saved = patientChronicConditionRepository.save(chronicCondition);

        return PatientChronicConditionResponseDto.fromEntity(saved);
    }

    @Transactional
    @Override
    public PatientChronicConditionResponseDto updateChronicCondition(CustomUserPrincipal principal,
            UUID chronicConditionId, PatientChronicConditionRequestDto request) {

        PatientChronicCondition chronicCondition = patientChronicConditionRepository.findById(chronicConditionId)
                .orElseThrow(() -> new RuntimeException("Patient chronic condition not found for this user"));

        if (request.getIcd10Code() != null) {
            chronicCondition.setIcd10Code(request.getIcd10Code());
        }
        if (request.getConditionName() != null) {
            chronicCondition.setConditionName(request.getConditionName());
        }
        if (request.getDiagnosisDate() != null) {
            chronicCondition.setDiagnosisDate(request.getDiagnosisDate());
        }
        if (request.getStatus() != null) {
            chronicCondition.setStatus(request.getStatus());
        }
        if (request.getManagingDoctorId() != null) {
            Doctor managingDoctor = doctorRepository.findById(request.getManagingDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found for this user"));

            chronicCondition.setManagingDoctor(managingDoctor);
        }
        if (request.getNotes() != null) {
            chronicCondition.setNotes(request.getNotes());
        }
        PatientChronicCondition saved = patientChronicConditionRepository.save(chronicCondition);

        return PatientChronicConditionResponseDto.fromEntity(saved);
    }

    @Transactional
    @Override
    public void deleteChronicCondition(CustomUserPrincipal principal, UUID chronicConditionId) {

        PatientChronicCondition chronicCondition = patientChronicConditionRepository.findById(chronicConditionId)
                .orElseThrow(() -> new RuntimeException("Patient chronic condition not found for this user"));

        patientChronicConditionRepository.delete(chronicCondition);
    }

    @Override
    public PatientHealthProfileResponseDto getPatientHealthProfile(CustomUserPrincipal principal, UUID patientId) {

        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found for this user"));

        Doctor requestingDoctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found for this user"));

        UUID requestingDoctorId = requestingDoctor.getDoctorId();

        if (requestingDoctorId != null) {
            privacyGuardService.checkDoctorAccess(requestingDoctorId, patientId, DataScope.ALLERGIES);
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found for this user"));

        PatientHealthProfile patientHealthProfiles = patientHealthProfileRepository.findByPatient(patient)
                .orElseThrow(() -> new RuntimeException("Patient health profile not found for this user"));

        return PatientHealthProfileResponseDto.fromEntity(patientHealthProfiles);
    }

    @Override
    public List<PatientAllergyResponseDto> getPatientAllergies(CustomUserPrincipal principal, UUID patientId) {
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found for this user"));

        Doctor requestingDoctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found for this user"));

        UUID requestingDoctorId = requestingDoctor.getDoctorId();

        if (requestingDoctorId != null) {
            privacyGuardService.checkDoctorAccess(requestingDoctorId, patientId, DataScope.ALLERGIES);
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found for this user"));

        List<PatientAllergy> allergies = patientAllergyRepository.findByPatient(patient);

        accessLogService.log(user, patient, AuditAction.VIEW, ResourceType.ALLERGIES, patientId,
                AuditOutcome.SUCCESS);

        return allergies.stream()
                .map(PatientAllergyResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<PatientChronicConditionResponseDto> getPatientChronicConditions(CustomUserPrincipal principal,
            UUID patientId) {
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found for this user"));

        Doctor requestingDoctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found for this user"));

        UUID requestingDoctorId = requestingDoctor.getDoctorId();

        if (requestingDoctorId != null) {
            privacyGuardService.checkDoctorAccess(requestingDoctorId, patientId, DataScope.ALLERGIES);
        }
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found for this user"));

        List<PatientChronicCondition> chronicConditions = patientChronicConditionRepository.findByPatient(patient);

        return chronicConditions.stream()
                .map(PatientChronicConditionResponseDto::fromEntity)
                .toList();
    }
}
