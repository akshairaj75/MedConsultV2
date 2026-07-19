package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.AppointmentSlot;
import com.backend.medconsult.enums.doctor.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, UUID> {

    /** All available slots for a dc on a specific date. */
    List<AppointmentSlot> findByDoctorClinic_DcIdAndSlotDateAndStatus(
            UUID dcId, LocalDate slotDate, SlotStatus status);

    /** All slots for a dc within a date range. */
    List<AppointmentSlot> findByDoctorClinic_DcIdAndSlotDateBetweenOrderBySlotDateAscStartTimeAsc(
            UUID dcId, LocalDate fromDate, LocalDate toDate);

    /** Check for duplicate slot (unique constraint guard). */
    boolean existsByDoctorClinic_DcIdAndSlotDateAndStartTime(
            UUID dcId, LocalDate slotDate, java.time.LocalTime startTime);
}
