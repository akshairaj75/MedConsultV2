package com.backend.medconsult.repository.platformAndCompliance;

import com.backend.medconsult.entity.platformAndCompliance.PrivacySetting;
import com.backend.medconsult.enums.platformAndCompliance.DataScope;
import com.backend.medconsult.enums.platformAndCompliance.PrivacyPermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface PrivacySettingRepository extends JpaRepository<PrivacySetting, UUID> {

        // boolean
        // existsByPatientIdAndDoctorIdAndDataScopeAndPermissionAndNotExpired(String
        // patientId, String doctorId,
        // DataScope scope, PrivacyPermission deny, LocalDateTime now);

        // boolean
        // existsByPatientIdAndDoctorIdIsNullAndDataScopeAndPermissionAndNotExpired(String
        // patientId, DataScope scope,
        // PrivacyPermission deny, LocalDateTime now);

        @Query("""
                            SELECT COUNT(ps) > 0
                            FROM PrivacySetting ps
                            WHERE ps.patient.patientId = :patientId
                              AND ps.doctor.doctorId = :doctorId
                              AND ps.dataScope = :scope
                              AND ps.permission = :permission
                              AND (ps.expiresAt IS NULL OR ps.expiresAt > :now)
                        """)
        boolean existsDoctorPermission(
                        @Param("patientId") UUID patientId,
                        @Param("doctorId") UUID doctorId,
                        @Param("scope") DataScope scope,
                        @Param("permission") PrivacyPermission permission,
                        @Param("now") LocalDateTime now);

        @Query("""
                            SELECT COUNT(ps) > 0
                            FROM PrivacySetting ps
                            WHERE ps.patient.patientId = :patientId
                              AND ps.doctor IS NULL
                              AND ps.dataScope = :scope
                              AND ps.permission = :permission
                              AND (ps.expiresAt IS NULL OR ps.expiresAt > :now)
                        """)
        boolean existsGlobalPermission(
                        @Param("patientId") UUID patientId,
                        @Param("scope") DataScope scope,
                        @Param("permission") PrivacyPermission permission,
                        @Param("now") LocalDateTime now);
}
