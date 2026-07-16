package com.backend.medconsult.repository.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CaseRoomRepository extends JpaRepository<CaseRoom, UUID> {
}
