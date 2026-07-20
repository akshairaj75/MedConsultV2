package com.backend.medconsult.dto.consultation;

import com.backend.medconsult.enums.consultation.ConsultationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateConsultationStatusRequest {

    @NotNull(message = "Status is required")
    private ConsultationStatus status;

    public UpdateConsultationStatusRequest() {
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultationStatus status) {
        this.status = status;
    }
}
