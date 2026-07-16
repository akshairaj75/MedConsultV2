package com.backend.medconsult.entity.caseRoom;

import jakarta.persistence.*;

import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.enums.caseRoom.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "case_room_members",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_crm_room_doc", columnNames = {"case_room_id", "doctor_id"})
    },
    indexes = {
        @Index(name = "idx_crm_doctor", columnList = "doctor_id, is_active")
    }
)
public class CaseRoomMember {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "member_id", nullable = false, updatable = false, length = 36)
    private UUID memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "case_room_id", nullable = false, foreignKey = @ForeignKey(name = "fk_case_room_members_room"))
    private CaseRoom caseRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_case_room_members_doctor"))
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role = MemberRole.CONTRIBUTOR;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by", foreignKey = @ForeignKey(name = "fk_case_room_members_inviter"))
    private Doctor invitedBy;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public CaseRoomMember() {
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public CaseRoom getCaseRoom() {
        return caseRoom;
    }

    public void setCaseRoom(CaseRoom caseRoom) {
        this.caseRoom = caseRoom;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    public Doctor getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Doctor invitedBy) {
        this.invitedBy = invitedBy;
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
