package com.backend.medconsult.dto.consultation;

import com.backend.medconsult.entity.consultation.ConsultationMessage;
import com.backend.medconsult.enums.consultation.MessageType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConsultationMessageResponseDto {

    private UUID messageId;
    private UUID consultationId;
    private UUID senderId;
    private String senderName;
    private MessageType messageType;
    private String body;
    private UUID fileId;
    private UUID prescriptionId;
    private UUID labResultId;
    private Boolean isRead;
    private LocalDateTime readAt;
    private Boolean isUrgent;
    private LocalDateTime sentAt;
    private LocalDateTime deletedAt;

    public ConsultationMessageResponseDto() {
    }

    public static ConsultationMessageResponseDto fromEntity(ConsultationMessage entity) {
        if (entity == null) {
            return null;
        }
        ConsultationMessageResponseDto dto = new ConsultationMessageResponseDto();
        dto.setMessageId(entity.getMessageId());
        
        if (entity.getConsultation() != null) {
            dto.setConsultationId(entity.getConsultation().getConsultationId());
        }
        
        if (entity.getSender() != null) {
            dto.setSenderId(entity.getSender().getUserId());
            dto.setSenderName(entity.getSender().getFullName());
        }
        
        dto.setMessageType(entity.getMessageType());
        dto.setBody(entity.getBody());
        
        if (entity.getFile() != null) {
            dto.setFileId(entity.getFile().getFileId());
        }
        
        if (entity.getPrescription() != null) {
            dto.setPrescriptionId(entity.getPrescription().getPrescriptionId());
        }
        
        if (entity.getLabResult() != null) {
            dto.setLabResultId(entity.getLabResult().getLabResultId());
        }
        
        dto.setIsRead(entity.getIsRead());
        dto.setReadAt(entity.getReadAt());
        dto.setIsUrgent(entity.getIsUrgent());
        dto.setSentAt(entity.getSentAt());
        dto.setDeletedAt(entity.getDeletedAt());
        
        return dto;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public UUID getLabResultId() {
        return labResultId;
    }

    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
