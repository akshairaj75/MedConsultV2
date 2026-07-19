package com.backend.medconsult.repository.doctor;

import com.backend.medconsult.entity.doctor.DoctorClinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorClinicRepository extends JpaRepository<DoctorClinic, UUID> {

    /** All active clinics for a doctor. */
    List<DoctorClinic> findByDoctor_DoctorIdAndIsActiveTrue(UUID doctorId);

    /** All dc records for a doctor regardless of status. */
    List<DoctorClinic> findByDoctor_DoctorId(UUID doctorId);

    /** All doctors for a clinic branch. */
    List<DoctorClinic> findByBranch_BranchIdAndIsActiveTrue(UUID branchId);

    /** Prevent duplicate doctor+branch assignment. */
    boolean existsByDoctor_DoctorIdAndBranch_BranchId(UUID doctorId, UUID branchId);

    /**
     * Paginated search for doctor-clinic links with optional filters.
     */
    @Query("""
            SELECT dc FROM DoctorClinic dc
            WHERE (:doctorId IS NULL  OR dc.doctor.doctorId = :doctorId)
              AND (:clinicId IS NULL  OR dc.clinic.clinicId = :clinicId)
              AND (:branchId IS NULL  OR dc.branch.branchId = :branchId)
              AND (:isActive IS NULL  OR dc.isActive = :isActive)
            """)
    Page<DoctorClinic> search(
            @Param("doctorId") UUID doctorId,
            @Param("clinicId") UUID clinicId,
            @Param("branchId") UUID branchId,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}
