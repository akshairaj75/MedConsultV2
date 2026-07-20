package com.backend.medconsult.serviceImpl.consultation;

import com.backend.medconsult.dto.consultation.ConsultationMessageRequestDto;
import com.backend.medconsult.dto.consultation.ConsultationMessageResponseDto;
import com.backend.medconsult.entity.clinicalRecords.LabResult;
import com.backend.medconsult.entity.clinicalRecords.Prescription;
import com.backend.medconsult.entity.consultation.Consultation;
import com.backend.medconsult.entity.consultation.ConsultationMessage;
import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.NotificationType;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.clinicalRecords.LabResultRepository;
import com.backend.medconsult.repository.clinicalRecords.PrescriptionRepository;
import com.backend.medconsult.repository.consultation.ConsultationMessageRepository;
import com.backend.medconsult.repository.consultation.ConsultationRepository;
import com.backend.medconsult.repository.platformAndCompliance.FileMetadataRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.consultation.ConsultationMessageService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import com.backend.medconsult.service.platformAndCompliance.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsultationMessageServiceImpl implements ConsultationMessageService {

    @Autowired
    private ConsultationMessageRepository consultationMessageRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    @Override
    public ConsultationMessageResponseDto sendMessage(ConsultationMessageRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {
        User sender = authUser.getUser();

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new RuntimeException("Consultation not found with ID: " + dto.getConsultationId()));

        ConsultationMessage message = new ConsultationMessage();
        message.setConsultation(consultation);
        message.setSender(sender);
        message.setMessageType(dto.getMessageType());
        message.setBody(dto.getBody());
        message.setIsUrgent(dto.getIsUrgent() != null ? dto.getIsUrgent() : false);
        message.setIsRead(false);
        message.setSentAt(LocalDateTime.now());

        if (dto.getFileId() != null) {
            FileMetadata file = fileMetadataRepository.findById(dto.getFileId())
                    .orElseThrow(() -> new RuntimeException("File not found with ID: " + dto.getFileId()));
            message.setFile(file);
        }

        if (dto.getPrescriptionId() != null) {
            Prescription prescription = prescriptionRepository.findById(dto.getPrescriptionId())
                    .orElseThrow(() -> new RuntimeException("Prescription not found with ID: " + dto.getPrescriptionId()));
            message.setPrescription(prescription);
        }

        if (dto.getLabResultId() != null) {
            LabResult labResult = labResultRepository.findById(dto.getLabResultId())
                    .orElseThrow(() -> new RuntimeException("LabResult not found with ID: " + dto.getLabResultId()));
            message.setLabResult(labResult);
        }

        ConsultationMessage saved = consultationMessageRepository.save(message);

        consultation.setLastMessageAt(saved.getSentAt());
        consultationRepository.save(consultation);

        UUID recipientId = sender.getUserId().equals(consultation.getPatient().getUser().getUserId())
                ? consultation.getDoctor().getUser().getUserId()
                : consultation.getPatient().getUser().getUserId();

        notificationService.notify(
                recipientId,
                NotificationType.NEW_MESSAGE,
                "New Message",
                "You have a new message in your consultation",
                "consultation_message",
                saved.getMessageId().toString()
        );

        accessLogService.log(
                sender,
                consultation.getPatient(),
                AuditAction.CREATE,
                ResourceType.CONSULTATION_MESSAGE,
                null,
                AuditOutcome.SUCCESS);

        return ConsultationMessageResponseDto.fromEntity(saved);
    }

    @Override
    public List<ConsultationMessageResponseDto> getMessagesForConsultation(UUID consultationId, CustomUserPrincipal authUser, HttpServletRequest request) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation not found with ID: " + consultationId));

        List<ConsultationMessage> messages = consultationMessageRepository.findByConsultation_ConsultationIdOrderBySentAtAsc(consultationId);

        accessLogService.log(
                authUser.getUser(),
                consultation.getPatient(),
                AuditAction.VIEW,
                ResourceType.CONSULTATION_MESSAGE,
                null,
                AuditOutcome.SUCCESS);

        return messages.stream().map(ConsultationMessageResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ConsultationMessageResponseDto markAsRead(UUID messageId, CustomUserPrincipal authUser, HttpServletRequest request) {
        ConsultationMessage message = consultationMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with ID: " + messageId));

        if (!message.getIsRead()) {
            message.setIsRead(true);
            message.setReadAt(LocalDateTime.now());
            message = consultationMessageRepository.save(message);
        }

        accessLogService.log(
                authUser.getUser(),
                message.getConsultation().getPatient(),
                AuditAction.UPDATE,
                ResourceType.CONSULTATION_MESSAGE,
                null,
                AuditOutcome.SUCCESS);

        return ConsultationMessageResponseDto.fromEntity(message);
    }
}
