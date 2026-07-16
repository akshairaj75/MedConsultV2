package com.backend.medconsult.repository.appointments;

import com.backend.medconsult.entity.appointments.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
}
