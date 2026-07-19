package com.backend.medconsult.dto.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CancelAppointmentRequest {

    @NotBlank(message = "Cancel reason is required")
    @Size(max = 255, message = "Cancel reason must not exceed 255 characters")
    private String cancelReason;

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
