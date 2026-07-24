package com.backend.medconsult.serviceImpl.clinic;

import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.clinic.ClinicBranch;
import com.backend.medconsult.entity.doctor.AppointmentSlot;
import com.backend.medconsult.entity.doctor.DoctorClinic;
import com.backend.medconsult.entity.doctor.DoctorLeave;
import com.backend.medconsult.entity.doctor.DoctorSchedule;
import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.usersAndPatients.UserRole;
import com.backend.medconsult.repository.clinic.ClinicAdminRepository;
import com.backend.medconsult.repository.clinic.ClinicBranchRepository;
import com.backend.medconsult.repository.clinic.ClinicRepository;
import com.backend.medconsult.repository.doctor.AppointmentSlotRepository;
import com.backend.medconsult.repository.doctor.DoctorClinicRepository;
import com.backend.medconsult.repository.doctor.DoctorLeaveRepository;
import com.backend.medconsult.repository.doctor.DoctorScheduleRepository;
import com.backend.medconsult.repository.appointments.AppointmentRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.service.clinic.ClinicAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClinicAuthorizationServiceImpl implements ClinicAuthorizationService {

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicBranchRepository clinicBranchRepository;

    @Autowired
    private DoctorClinicRepository doctorClinicRepository;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private DoctorLeaveRepository doctorLeaveRepository;

    @Autowired
    private AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    @Transactional(readOnly = true)
    public void verifyClinicAdmin(UUID clinicId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        if (user.getRole() == UserRole.CLINIC_ADMIN) {
            boolean manages = clinicAdminRepository.existsByClinic_ClinicIdAndUser_UserId(clinicId, userId);
            if (!manages) {
                throw new AccessDeniedException("Access denied to requested clinic.");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyPrimaryClinicAdmin(UUID clinicId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        if (user.getRole() == UserRole.CLINIC_ADMIN) {
            boolean managesPrimary = clinicAdminRepository.existsByClinic_ClinicIdAndUser_UserIdAndIsPrimary(clinicId, userId, true);
            if (!managesPrimary) {
                throw new AccessDeniedException("Access denied. Primary clinic admin privilege required.");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getManagedClinicIds(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        return clinicAdminRepository.findByUser(user)
                .stream()
                .map(admin -> admin.getClinic().getClinicId())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicResponseDto> getManagedClinics(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        return clinicAdminRepository.findByUser(user)
                .stream()
                .map(admin -> ClinicResponseDto.fromEntity(admin.getClinic()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyBranchAdmin(UUID branchId, UUID userId) {
        ClinicBranch branch = clinicBranchRepository.findById(branchId)
                .orElseThrow(() -> new AccessDeniedException("Clinic branch not found"));
        verifyClinicAdmin(branch.getClinic().getClinicId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyDoctorClinicAdmin(UUID doctorClinicId, UUID userId) {
        DoctorClinic doctorClinic = doctorClinicRepository.findById(doctorClinicId)
                .orElseThrow(() -> new AccessDeniedException("Doctor-Clinic association not found"));
        verifyClinicAdmin(doctorClinic.getClinic().getClinicId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyScheduleAdmin(UUID scheduleId, UUID userId) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new AccessDeniedException("Doctor schedule not found"));
        verifyClinicAdmin(schedule.getDoctorClinic().getClinic().getClinicId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyLeaveAdmin(UUID leaveId, UUID userId) {
        DoctorLeave leave = doctorLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new AccessDeniedException("Doctor leave record not found"));
        verifyClinicAdmin(leave.getDoctorClinic().getClinic().getClinicId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifySlotAdmin(UUID slotId, UUID userId) {
        AppointmentSlot slot = appointmentSlotRepository.findById(slotId)
                .orElseThrow(() -> new AccessDeniedException("Appointment slot not found"));
        verifyClinicAdmin(slot.getDoctorClinic().getClinic().getClinicId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyAppointmentAdmin(UUID appointmentId, UUID userId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AccessDeniedException("Appointment not found"));
        verifyClinicAdmin(appointment.getDoctorClinic().getClinic().getClinicId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyDoctorAdmin(UUID doctorId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        if (user.getRole() == UserRole.CLINIC_ADMIN) {
            List<UUID> managedClinicIds = getManagedClinicIds(userId);
            List<DoctorClinic> dcs = doctorClinicRepository.findByDoctor_DoctorId(doctorId);
            boolean isLinked = dcs.stream().anyMatch(dc -> managedClinicIds.contains(dc.getClinic().getClinicId()));
            if (!isLinked) {
                throw new AccessDeniedException("Access denied. Doctor is not associated with any of your managed clinics.");
            }
        }
    }
}
