package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorLeaveRepository extends JpaRepository<DoctorLeave, UUID> {

    /** All leave records for a doctor-clinic link. */
    List<DoctorLeave> findByDoctorClinic_DcIdOrderByStartDateDesc(UUID dcId);

    /** Leave records for a dc within a date range (for overlap detection). */
    List<DoctorLeave> findByDoctorClinic_DcIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            UUID dcId, LocalDate end, LocalDate start);
}
