package com.backend.medconsult.repository.consultation;

import com.backend.medconsult.entity.consultation.ConsultationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ConsultationMessageRepository extends JpaRepository<ConsultationMessage, UUID> {
}
