package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.enums.caseRoom.CaseRoomStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateCaseRoomStatusRequest {

    @NotNull(message = "Status is required")
    private CaseRoomStatus status;

    public UpdateCaseRoomStatusRequest() {
    }

    public CaseRoomStatus getStatus() {
        return status;
    }

    public void setStatus(CaseRoomStatus status) {
        this.status = status;
    }
}
