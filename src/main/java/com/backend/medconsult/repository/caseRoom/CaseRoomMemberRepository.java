package com.backend.medconsult.repository.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CaseRoomMemberRepository extends JpaRepository<CaseRoomMember, UUID> {

    List<CaseRoomMember> findByCaseRoom_CaseRoomIdAndIsActiveTrue(UUID caseRoomId);

    List<CaseRoomMember> findByDoctor_DoctorIdAndIsActiveTrue(UUID doctorId);

    Optional<CaseRoomMember> findByCaseRoom_CaseRoomIdAndDoctor_DoctorId(UUID caseRoomId, UUID doctorId);

    boolean existsByCaseRoom_CaseRoomIdAndDoctor_DoctorIdAndIsActiveTrue(UUID caseRoomId, UUID doctorId);
}
