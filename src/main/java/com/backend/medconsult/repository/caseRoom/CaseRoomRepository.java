package com.backend.medconsult.repository.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoom;
import com.backend.medconsult.enums.caseRoom.CasePriority;
import com.backend.medconsult.enums.caseRoom.CaseRoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CaseRoomRepository extends JpaRepository<CaseRoom, UUID> {

    @Query("SELECT cr FROM CaseRoom cr WHERE " +
           "(:patientId IS NULL OR cr.patient.patientId = :patientId) AND " +
           "(:doctorId IS NULL OR cr.openedBy.doctorId = :doctorId) AND " +
           "(:status IS NULL OR cr.status = :status) AND " +
           "(:priority IS NULL OR cr.priority = :priority)")
    Page<CaseRoom> search(
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("status") CaseRoomStatus status,
            @Param("priority") CasePriority priority,
            Pageable pageable);
}
