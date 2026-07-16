package com.backend.medconsult.repository.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoomPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CaseRoomPostRepository extends JpaRepository<CaseRoomPost, UUID> {
}
