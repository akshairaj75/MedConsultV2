package com.backend.medconsult.repository.appointments;

import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.enums.appointments.AppointmentStatus;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    // boolean existsByPatientIdAndDcId_DoctorIdAndStatusNot(String patientId,
    // String doctorId,
    // AppointmentStatus cancelled);

    boolean existsByPatient_PatientIdAndDoctorClinic_Doctor_DoctorIdAndStatusNot(
            UUID patientId,
            UUID doctorId,
            AppointmentStatus status);
}
