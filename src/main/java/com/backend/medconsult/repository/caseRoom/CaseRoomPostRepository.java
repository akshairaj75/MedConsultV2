package com.backend.medconsult.repository.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoomPost;
import com.backend.medconsult.enums.caseRoom.ActionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CaseRoomPostRepository extends JpaRepository<CaseRoomPost, UUID> {

    Page<CaseRoomPost> findByCaseRoom_CaseRoomIdOrderByPostedAtAsc(UUID caseRoomId, Pageable pageable);

    List<CaseRoomPost> findByActionAssignedTo_DoctorIdAndActionStatus(UUID doctorId, ActionStatus status);
}
