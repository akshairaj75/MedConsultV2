package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    Optional<Doctor> findByUser(User user);

    boolean existsByMohRegistrationNumber(String moh);

    boolean existsByUser(User user);
}
