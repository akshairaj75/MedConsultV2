package com.backend.medconsult.repository.clinic;

import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.clinic.ClinicAdmin;
import com.backend.medconsult.entity.usersAndPatients.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, UUID> {

    boolean existsByClinicAndUser(Clinic clinic, User user);

    boolean existsByClinic_ClinicIdAndUser_UserId(UUID clinicId, UUID userId);

    boolean existsByClinic_ClinicIdAndUser_UserIdAndIsPrimary(UUID clinicId, UUID userId, Boolean isPrimary);

    List<ClinicAdmin> findByClinic(Clinic clinic);

    List<ClinicAdmin> findByUser(User user);

    Optional<ClinicAdmin> findByClinicAndIsPrimaryTrue(Clinic clinic);
}
