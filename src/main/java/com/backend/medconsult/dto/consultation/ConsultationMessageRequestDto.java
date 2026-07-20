package com.backend.medconsult.dto.consultation;

import com.backend.medconsult.enums.consultation.MessageType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ConsultationMessageRequestDto {

    @NotNull(message = "Consultation ID is required")
    private UUID consultationId;

    @NotNull(message = "Message type is required")
    private MessageType messageType = MessageType.TEXT;

    private String body;

    private UUID fileId;

    private UUID prescriptionId;

    private UUID labResultId;

    private Boolean isUrgent = false;

    public ConsultationMessageRequestDto() {
    }

    public UUID getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(UUID consultationId) {
        this.consultationId = consultationId;
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

    public Boolean getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }
}
