package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DoctorLeaveRepository extends JpaRepository<DoctorLeave, UUID> {
}
