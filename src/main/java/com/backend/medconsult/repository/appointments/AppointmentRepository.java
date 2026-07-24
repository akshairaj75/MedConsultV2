package com.backend.medconsult.repository.appointments;

import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.enums.appointments.AppointmentStatus;
import com.backend.medconsult.enums.appointments.AppointmentType;
import com.backend.medconsult.enums.doctor.SessionType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    /**
     * Checks if an active (non-cancelled) appointment already exists for the given
     * patient with the given doctor.
     */
    boolean existsByPatient_PatientIdAndDoctorClinic_Doctor_DoctorIdAndStatusNot(
            UUID patientId,
            UUID doctorId,
            AppointmentStatus status);

    /**
     * Find all appointments for a patient ordered by date descending.
     */
    Page<Appointment> findByPatient_PatientIdOrderByScheduledDateDesc(UUID patientId, Pageable pageable);

    /**
     * Find all appointments for a specific doctor-clinic link.
     */
    Page<Appointment> findByDoctorClinic_DcIdOrderByScheduledDateDesc(UUID dcId, Pageable pageable);

    /**
     * Find all appointments for a specific doctor (across all clinics).
     */
    Page<Appointment> findByDoctorClinic_Doctor_DoctorIdOrderByScheduledDateDesc(UUID doctorId, Pageable pageable);

    /**
     * Find all appointments by status.
     */
    Page<Appointment> findByStatusOrderByScheduledDateDesc(AppointmentStatus status, Pageable pageable);

    /**
     * Find appointments by patient and status.
     */
    List<Appointment> findByPatient_PatientIdAndStatus(UUID patientId, AppointmentStatus status);

    /**
     * Find appointments for a patient within a date range.
     */
    @Query("""
            SELECT a FROM Appointment a
            WHERE a.patient.patientId = :patientId
              AND a.scheduledDate BETWEEN :fromDate AND :toDate
            ORDER BY a.scheduledDate DESC, a.startTime ASC
            """)
    List<Appointment> findByPatientAndDateRange(
            @Param("patientId") UUID patientId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    /**
     * Flexible search with all optional filters + pagination.
     */
    @Query("""
            SELECT a FROM Appointment a
            WHERE (:patientId IS NULL   OR a.patient.patientId = :patientId)
              AND (:doctorId IS NULL    OR a.doctorClinic.doctor.doctorId = :doctorId)
              AND (:dcId IS NULL        OR a.doctorClinic.dcId = :dcId)
              AND (:status IS NULL      OR a.status = :status)
              AND (:apptType IS NULL    OR a.appointmentType = :apptType)
              AND (:sessionType IS NULL OR a.sessionType = :sessionType)
              AND (:fromDate IS NULL    OR a.scheduledDate >= :fromDate)
              AND (:toDate IS NULL      OR a.scheduledDate <= :toDate)
              AND (:clinicIds IS NULL   OR a.doctorClinic.clinic.clinicId IN :clinicIds)
            """)
    Page<Appointment> search(
            @Param("patientId")   UUID patientId,
            @Param("doctorId")    UUID doctorId,
            @Param("dcId")        UUID dcId,
            @Param("status")      AppointmentStatus status,
            @Param("apptType")    AppointmentType apptType,
            @Param("sessionType") SessionType sessionType,
            @Param("fromDate")    LocalDate fromDate,
            @Param("toDate")      LocalDate toDate,
            @Param("clinicIds")   List<UUID> clinicIds,
            Pageable pageable);

    /**
     * Count appointments for a patient by status.
     */
    long countByPatient_PatientIdAndStatus(UUID patientId, AppointmentStatus status);

    /**
     * Count appointments for a doctor by status.
     */
    long countByDoctorClinic_Doctor_DoctorIdAndStatus(UUID doctorId, AppointmentStatus status);

    /**
     * Find upcoming appointments for a patient (status SCHEDULED or CONFIRMED, date >= today).
     */
    @Query("""
            SELECT a FROM Appointment a
            WHERE a.patient.patientId = :patientId
              AND a.status IN (
                  com.backend.medconsult.enums.appointments.AppointmentStatus.SCHEDULED,
                  com.backend.medconsult.enums.appointments.AppointmentStatus.CONFIRMED)
              AND a.scheduledDate >= :today
            ORDER BY a.scheduledDate ASC, a.startTime ASC
            """)
    List<Appointment> findUpcomingByPatient(
            @Param("patientId") UUID patientId,
            @Param("today") LocalDate today);

    /**
     * Find upcoming appointments for a doctor (status SCHEDULED or CONFIRMED, date >= today).
     */
    @Query("""
            SELECT a FROM Appointment a
            WHERE a.doctorClinic.doctor.doctorId = :doctorId
              AND a.status IN (
                  com.backend.medconsult.enums.appointments.AppointmentStatus.SCHEDULED,
                  com.backend.medconsult.enums.appointments.AppointmentStatus.CONFIRMED)
              AND a.scheduledDate >= :today
            ORDER BY a.scheduledDate ASC, a.startTime ASC
            """)
    List<Appointment> findUpcomingByDoctor(
            @Param("doctorId") UUID doctorId,
            @Param("today") LocalDate today);
}
