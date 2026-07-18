package com.backend.medconsult.serviceImpl.platformAndCompliance;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.backend.medconsult.enums.appointments.AppointmentStatus;
import com.backend.medconsult.enums.platformAndCompliance.DataScope;
import com.backend.medconsult.enums.platformAndCompliance.PrivacyPermission;
import com.backend.medconsult.repository.appointments.AppointmentRepository;
import com.backend.medconsult.repository.platformAndCompliance.PrivacySettingRepository;
import com.backend.medconsult.service.platformAndCompliance.PrivacyGuardService;

@Service
public class PrivacyGuardServiceImpl implements PrivacyGuardService {

    @Autowired
    private PrivacySettingRepository privacySettingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void checkDoctorAccess(UUID doctorId, UUID patientId, DataScope scope) {

        LocalDateTime now = LocalDateTime.now();

        // 1. Doctor must have a valid care relationship with patient
        boolean hasRelationship = appointmentRepository
                .existsByPatient_PatientIdAndDoctorClinic_Doctor_DoctorIdAndStatusNot(
                        patientId,
                        doctorId,
                        AppointmentStatus.CANCELLED);

        if (!hasRelationship) {
            throw new AccessDeniedException("No care relationship with this patient");
        }

        // 2. Explicit doctor-specific ALLOW rule
        boolean explicitlyAllowed = privacySettingRepository
                .existsDoctorPermission(
                        patientId,
                        doctorId,
                        scope,
                        PrivacyPermission.ALLOW,
                        now);

        if (explicitlyAllowed) {
            return;
        }

        // 3. Global ALLOW rule (doctor = null)
        boolean globallyAllowed = privacySettingRepository
                .existsGlobalPermission(
                        patientId,
                        scope,
                        PrivacyPermission.ALLOW,
                        now);

        if (globallyAllowed) {
            return;
        }

        // 4. Explicit doctor-specific DENY rule
        boolean explicitlyDenied = privacySettingRepository
                .existsDoctorPermission(
                        patientId,
                        doctorId,
                        scope,
                        PrivacyPermission.DENY,
                        now);

        if (explicitlyDenied) {
            throw new AccessDeniedException(
                    "Patient has restricted access to this data");
        }

        // 5. Global DENY rule (doctor = null)
        boolean globallyDenied = privacySettingRepository
                .existsGlobalPermission(
                        patientId,
                        scope,
                        PrivacyPermission.DENY,
                        now);

        if (globallyDenied) {
            throw new AccessDeniedException(
                    "Patient has restricted access to this data");
        }

        // Default: allow because doctor has relationship and no deny rule exists.
    }
}