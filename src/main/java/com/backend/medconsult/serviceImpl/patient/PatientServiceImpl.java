package com.backend.medconsult.serviceImpl.patient;

import java.nio.file.attribute.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.patient.PatientRequestDto;
import com.backend.medconsult.dto.patient.PatientResponseDto;
import com.backend.medconsult.entity.platformAndCompliance.AccessLog;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.patient.PatientService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccessLogService accessLogService;

    @Override
    public PatientResponseDto createProfile(CustomUserPrincipal principal, PatientRequestDto request) {

        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

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
}
