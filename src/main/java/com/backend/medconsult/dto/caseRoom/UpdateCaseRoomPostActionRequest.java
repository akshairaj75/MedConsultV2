package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.enums.caseRoom.ActionStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateCaseRoomPostActionRequest {

    @NotNull(message = "Action Status is required")
    private ActionStatus actionStatus;

    public UpdateCaseRoomPostActionRequest() {
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }
}
