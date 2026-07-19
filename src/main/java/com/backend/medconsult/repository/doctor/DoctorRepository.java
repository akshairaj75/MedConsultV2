package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    Optional<Doctor> findByUser(User user);

    boolean existsByMohRegistrationNumber(String moh);

    boolean existsByUser(User user);

    // @Query("""
    //     SELECT DISTINCT d FROM Doctor d
    //     LEFT JOIN d.user u
    //     LEFT JOIN d.specialties s
    //     WHERE (:name IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%')))
    //     AND (:specialtyId IS NULL OR s.specialty.specialtyId = :specialtyId)
    //     AND (:isActive IS NULL OR d.isActive = :isActive)
    // """)
    // Page<Doctor> searchDoctors(@Param("name") String name, 
    //                            @Param("specialtyId") UUID specialtyId, 
    //                            @Param("isActive") Boolean isActive, 
    //                            Pageable pageable);
}
