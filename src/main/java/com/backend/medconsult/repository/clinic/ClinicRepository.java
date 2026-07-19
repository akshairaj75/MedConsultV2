package com.backend.medconsult.repository.clinic;

import com.backend.medconsult.entity.clinic.Clinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, UUID> {

    boolean existsByMohLicenseNumber(String mohLicenseNumber);

    Optional<Clinic> findByMohLicenseNumber(String mohLicenseNumber);

    /**
     * Flexible paginated search across clinics.
     * Joins through the ClinicSpecialty join-entity to filter by specialty.
     * All parameters are optional — null values are treated as "match all".
     *
     * <p>Note: When specialtyId is not null, only clinics that have at least
     * one matching ClinicSpecialty record will appear (implicit INNER JOIN via the filter).
     * When specialtyId is null the LEFT JOIN keeps all clinics.
     */
    @Query(value = """
        SELECT DISTINCT c FROM Clinic c
        LEFT JOIN ClinicSpecialty cs ON cs.clinic = c
        WHERE (:name IS NULL
               OR LOWER(c.nameEn) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(c.nameAr) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:specialtyId IS NULL OR cs.specialty.specialtyId = :specialtyId)
          AND (:isActive IS NULL OR c.isActive = :isActive)
    """,
    countQuery = """
        SELECT COUNT(DISTINCT c) FROM Clinic c
        LEFT JOIN ClinicSpecialty cs ON cs.clinic = c
        WHERE (:name IS NULL
               OR LOWER(c.nameEn) LIKE LOWER(CONCAT('%', :name, '%'))
               OR LOWER(c.nameAr) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:specialtyId IS NULL OR cs.specialty.specialtyId = :specialtyId)
          AND (:isActive IS NULL OR c.isActive = :isActive)
    """)
    Page<Clinic> searchClinics(
            @Param("name") String name,
            @Param("specialtyId") UUID specialtyId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}
