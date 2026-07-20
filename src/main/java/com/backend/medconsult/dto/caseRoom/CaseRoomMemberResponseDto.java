package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoomMember;
import com.backend.medconsult.enums.caseRoom.MemberRole;

import java.time.LocalDateTime;
import java.util.UUID;

public class CaseRoomMemberResponseDto {

    private UUID memberId;
    private UUID caseRoomId;
    private UUID doctorId;
    private String doctorName;
    private MemberRole role;
    private UUID invitedById;
    private String invitedByName;
    private LocalDateTime joinedAt;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public CaseRoomMemberResponseDto() {
    }

    public static CaseRoomMemberResponseDto fromEntity(CaseRoomMember entity) {
        if (entity == null) {
            return null;
        }
        CaseRoomMemberResponseDto dto = new CaseRoomMemberResponseDto();
        dto.setMemberId(entity.getMemberId());

        if (entity.getCaseRoom() != null) {
            dto.setCaseRoomId(entity.getCaseRoom().getCaseRoomId());
        }

        if (entity.getDoctor() != null) {
            dto.setDoctorId(entity.getDoctor().getDoctorId());
            if (entity.getDoctor().getUser() != null) {
                dto.setDoctorName(entity.getDoctor().getUser().getFullName());
            }
        }

        dto.setRole(entity.getRole());

        if (entity.getInvitedBy() != null) {
            dto.setInvitedById(entity.getInvitedBy().getDoctorId());
            if (entity.getInvitedBy().getUser() != null) {
                dto.setInvitedByName(entity.getInvitedBy().getUser().getFullName());
            }
        }

        dto.setJoinedAt(entity.getJoinedAt());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    public UUID getInvitedById() {
        return invitedById;
    }

    public void setInvitedById(UUID invitedById) {
        this.invitedById = invitedById;
    }

    public String getInvitedByName() {
        return invitedByName;
    }

    public void setInvitedByName(String invitedByName) {
        this.invitedByName = invitedByName;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
