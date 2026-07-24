package com.backend.medconsult.serviceImpl.clinic;

import com.backend.medconsult.dto.clinic.ClinicAdminRequestDto;
import com.backend.medconsult.dto.clinic.ClinicAdminResponseDto;
import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.clinic.ClinicAdmin;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.usersAndPatients.UserRole;
import com.backend.medconsult.repository.clinic.ClinicAdminRepository;
import com.backend.medconsult.repository.clinic.ClinicRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.service.clinic.ClinicAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClinicAdminServiceImpl implements ClinicAdminService {

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public ClinicAdminResponseDto assignAdmin(ClinicAdminRequestDto dto) {
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found: " + dto.getClinicId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));

        // Validation
        if (user.getRole() != UserRole.CLINIC_ADMIN) {
            throw new IllegalArgumentException("Only users with role CLINIC_ADMIN can be assigned as clinic admins.");
        }

        if (clinicAdminRepository.existsByClinicAndUser(clinic, user)) {
            throw new IllegalArgumentException("User is already assigned to this clinic.");
        }

        ClinicAdmin clinicAdmin = new ClinicAdmin();
        clinicAdmin.setClinic(clinic);
        clinicAdmin.setUser(user);
        if (dto.getIsPrimary() != null) {
            clinicAdmin.setIsPrimary(dto.getIsPrimary());
        } else {
            clinicAdmin.setIsPrimary(true); // default to true
        }

        ClinicAdmin saved = clinicAdminRepository.save(clinicAdmin);
        return ClinicAdminResponseDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public void removeAdmin(UUID id) {
        ClinicAdmin clinicAdmin = clinicAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic admin assignment not found: " + id));
        clinicAdminRepository.delete(clinicAdmin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicAdminResponseDto> getClinicAdmins(UUID clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found: " + clinicId));
        return clinicAdminRepository.findByClinic(clinic)
                .stream()
                .map(ClinicAdminResponseDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicAdminResponseDto> getManagedClinics(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return clinicAdminRepository.findByUser(user)
                .stream()
                .map(ClinicAdminResponseDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public void changePrimaryAdmin(UUID clinicId, UUID userId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found: " + clinicId));

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        List<ClinicAdmin> admins = clinicAdminRepository.findByClinic(clinic);
        boolean targetAdminExists = false;

        for (ClinicAdmin admin : admins) {
            if (admin.getUser().getUserId().equals(userId)) {
                admin.setIsPrimary(true);
                targetAdminExists = true;
            } else {
                admin.setIsPrimary(false);
            }
        }

        if (!targetAdminExists) {
            throw new IllegalArgumentException("User is not assigned to this clinic.");
        }

        clinicAdminRepository.saveAll(admins);
    }
}
