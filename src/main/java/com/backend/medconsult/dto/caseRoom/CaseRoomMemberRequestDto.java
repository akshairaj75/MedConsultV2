package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.enums.caseRoom.MemberRole;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CaseRoomMemberRequestDto {

    @NotNull(message = "Case Room ID is required")
    private UUID caseRoomId;

    @NotNull(message = "Doctor ID is required")
    private UUID doctorId;

    @NotNull(message = "Role is required")
    private MemberRole role = MemberRole.CONTRIBUTOR;

    public CaseRoomMemberRequestDto() {
    }

    public UUID getCaseRoomId() {
        return caseRoomId;
    }

    public void setCaseRoomId(UUID caseRoomId) {
        this.caseRoomId = caseRoomId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }
}
