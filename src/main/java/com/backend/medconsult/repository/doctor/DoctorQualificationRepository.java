package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DoctorQualificationRepository extends JpaRepository<DoctorQualification, UUID> {
}
