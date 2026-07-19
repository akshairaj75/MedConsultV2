package com.backend.medconsult.dto.appointment;

import com.backend.medconsult.enums.appointments.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateAppointmentStatusRequest {

    @NotNull(message = "Status is required")
    private AppointmentStatus status;

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
