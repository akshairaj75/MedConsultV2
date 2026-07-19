package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, UUID> {

    /** All schedules for a doctor-clinic link. */
    List<DoctorSchedule> findByDoctorClinic_DcIdOrderByDayOfWeekAscStartTimeAsc(UUID dcId);

    /** Active schedules for a specific day of week. */
    List<DoctorSchedule> findByDoctorClinic_DcIdAndDayOfWeekAndIsActiveTrue(UUID dcId, Byte dayOfWeek);

    /** Check for overlapping schedule on the same day (to prevent conflicts). */
    boolean existsByDoctorClinic_DcIdAndDayOfWeekAndIsActiveTrue(UUID dcId, Byte dayOfWeek);
}
