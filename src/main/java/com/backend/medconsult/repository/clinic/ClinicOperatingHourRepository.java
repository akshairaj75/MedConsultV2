package com.backend.medconsult.repository.clinic;

import com.backend.medconsult.entity.clinic.ClinicBranch;
import com.backend.medconsult.entity.clinic.ClinicOperatingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClinicOperatingHourRepository extends JpaRepository<ClinicOperatingHour, UUID> {

    List<ClinicOperatingHour> findAllByBranch(ClinicBranch clinicBranch);
}
