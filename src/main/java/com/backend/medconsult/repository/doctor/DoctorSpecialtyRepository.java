package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty, UUID> {
}
