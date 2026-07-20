package com.backend.medconsult.repository.consultation;

import com.backend.medconsult.entity.consultation.ConsultationMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultationMessageRepository extends JpaRepository<ConsultationMessage, UUID> {

    List<ConsultationMessage> findByConsultation_ConsultationIdOrderBySentAtAsc(UUID consultationId);

    Page<ConsultationMessage> findByConsultation_ConsultationIdOrderBySentAtAsc(UUID consultationId, Pageable pageable);

    long countByConsultation_ConsultationIdAndIsReadFalseAndSender_UserIdNot(UUID consultationId, UUID currentUserId);
}
