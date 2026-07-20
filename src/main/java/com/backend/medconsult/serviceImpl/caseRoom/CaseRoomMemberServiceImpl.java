package com.backend.medconsult.serviceImpl.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomMemberRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomMemberResponseDto;
import com.backend.medconsult.entity.caseRoom.CaseRoom;
import com.backend.medconsult.entity.caseRoom.CaseRoomMember;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.caseRoom.MemberRole;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.NotificationType;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.caseRoom.CaseRoomMemberRepository;
import com.backend.medconsult.repository.caseRoom.CaseRoomRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.caseRoom.CaseRoomMemberService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import com.backend.medconsult.service.platformAndCompliance.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CaseRoomMemberServiceImpl implements CaseRoomMemberService {

    @Autowired
    private CaseRoomMemberRepository caseRoomMemberRepository;

    @Autowired
    private CaseRoomRepository caseRoomRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    @Override
    public CaseRoomMemberResponseDto addMember(CaseRoomMemberRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {
        User inviterUser = authUser.getUser();

        Doctor inviterDoctor = doctorRepository.findByUser(inviterUser)
                .orElseThrow(() -> new RuntimeException("Current user is not registered as a doctor"));

        CaseRoom caseRoom = caseRoomRepository.findById(dto.getCaseRoomId())
                .orElseThrow(() -> new RuntimeException("Case room not found with ID: " + dto.getCaseRoomId()));

        Doctor invitedDoctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        if (caseRoomMemberRepository.existsByCaseRoom_CaseRoomIdAndDoctor_DoctorIdAndIsActiveTrue(caseRoom.getCaseRoomId(), invitedDoctor.getDoctorId())) {
            throw new RuntimeException("Doctor is already an active member of this case room");
        }

        CaseRoomMember member = new CaseRoomMember();
        member.setCaseRoom(caseRoom);
        member.setDoctor(invitedDoctor);
        member.setRole(dto.getRole() != null ? dto.getRole() : MemberRole.CONTRIBUTOR);
        member.setInvitedBy(inviterDoctor);
        member.setJoinedAt(LocalDateTime.now());
        member.setIsActive(true);

        CaseRoomMember saved = caseRoomMemberRepository.save(member);

        notificationService.notify(
                invitedDoctor.getUser().getUserId(),
                NotificationType.NEW_MESSAGE, // Assuming NEW_MESSAGE maps roughly to invitation/alert or we can add a specific type.
                "Case Room Invitation",
                "You have been invited to collaborate in Case Room: " + caseRoom.getTitle(),
                "case_room",
                caseRoom.getCaseRoomId().toString()
        );

        accessLogService.log(
                inviterUser,
                caseRoom.getPatient(),
                AuditAction.CREATE,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return CaseRoomMemberResponseDto.fromEntity(saved);
    }

    @Override
    public List<CaseRoomMemberResponseDto> getMembersForRoom(UUID caseRoomId, CustomUserPrincipal authUser, HttpServletRequest request) {
        CaseRoom caseRoom = caseRoomRepository.findById(caseRoomId)
                .orElseThrow(() -> new RuntimeException("Case room not found with ID: " + caseRoomId));

        List<CaseRoomMember> members = caseRoomMemberRepository.findByCaseRoom_CaseRoomIdAndIsActiveTrue(caseRoomId);

        accessLogService.log(
                authUser.getUser(),
                caseRoom.getPatient(),
                AuditAction.VIEW,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return members.stream().map(CaseRoomMemberResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CaseRoomMemberResponseDto removeMember(UUID memberId, CustomUserPrincipal authUser, HttpServletRequest request) {
        CaseRoomMember member = caseRoomMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Case Room Member not found with ID: " + memberId));

        if (member.getRole() == MemberRole.OWNER) {
            throw new RuntimeException("Cannot remove the owner of the case room");
        }

        member.setIsActive(false);
        CaseRoomMember removed = caseRoomMemberRepository.save(member);

        accessLogService.log(
                authUser.getUser(),
                member.getCaseRoom().getPatient(),
                AuditAction.DELETE,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return CaseRoomMemberResponseDto.fromEntity(removed);
    }

    @Override
    public List<CaseRoomMemberResponseDto> getMyCaseRooms(CustomUserPrincipal authUser, HttpServletRequest request) {
        Doctor doctor = doctorRepository.findByUser(authUser.getUser())
                .orElseThrow(() -> new RuntimeException("Current user is not registered as a doctor"));

        List<CaseRoomMember> members = caseRoomMemberRepository.findByDoctor_DoctorIdAndIsActiveTrue(doctor.getDoctorId());

        accessLogService.log(
                authUser.getUser(),
                null,
                AuditAction.VIEW,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return members.stream().map(CaseRoomMemberResponseDto::fromEntity).collect(Collectors.toList());
    }
}
