package com.backend.medconsult.serviceImpl.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomResponseDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomSearchRequest;
import com.backend.medconsult.dto.caseRoom.UpdateCaseRoomStatusRequest;
import com.backend.medconsult.entity.caseRoom.CaseRoom;
import com.backend.medconsult.entity.caseRoom.CaseRoomMember;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.caseRoom.CaseRoomStatus;
import com.backend.medconsult.enums.caseRoom.MemberRole;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.caseRoom.CaseRoomMemberRepository;
import com.backend.medconsult.repository.caseRoom.CaseRoomRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.caseRoom.CaseRoomService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CaseRoomServiceImpl implements CaseRoomService {

    @Autowired
    private CaseRoomRepository caseRoomRepository;

    @Autowired
    private CaseRoomMemberRepository caseRoomMemberRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccessLogService accessLogService;

    @Transactional
    @Override
    public CaseRoomResponseDto openCaseRoom(CaseRoomRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {
        User user = authUser.getUser();

        Doctor openedBy = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Current user is not registered as a doctor"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

        CaseRoom caseRoom = new CaseRoom();
        caseRoom.setPatient(patient);
        caseRoom.setOpenedBy(openedBy);
        caseRoom.setTitle(dto.getTitle());
        caseRoom.setDescription(dto.getDescription());
        caseRoom.setPriority(dto.getPriority());
        caseRoom.setStatus(CaseRoomStatus.ACTIVE);

        CaseRoom savedRoom = caseRoomRepository.save(caseRoom);

        // Automatically add the creator as OWNER
        CaseRoomMember ownerMember = new CaseRoomMember();
        ownerMember.setCaseRoom(savedRoom);
        ownerMember.setDoctor(openedBy);
        ownerMember.setRole(MemberRole.OWNER);
        ownerMember.setJoinedAt(LocalDateTime.now());
        ownerMember.setIsActive(true);

        caseRoomMemberRepository.save(ownerMember);

        accessLogService.log(
                user,
                patient,
                AuditAction.CREATE,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return CaseRoomResponseDto.fromEntity(savedRoom);
    }

    @Override
    public CaseRoomResponseDto getCaseRoomById(UUID caseRoomId, CustomUserPrincipal authUser, HttpServletRequest request) {
        CaseRoom caseRoom = findCaseRoomOrThrow(caseRoomId);

        accessLogService.log(
                authUser.getUser(),
                caseRoom.getPatient(),
                AuditAction.VIEW,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return CaseRoomResponseDto.fromEntity(caseRoom);
    }

    @Override
    public Page<CaseRoomResponseDto> searchCaseRooms(CaseRoomSearchRequest searchRequest, CustomUserPrincipal authUser, HttpServletRequest request) {
        Sort sort = Sort.by(
                "ASC".equalsIgnoreCase(searchRequest.getSortDir())
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                resolveValidSortField(searchRequest.getSortBy()));

        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sort);

        Page<CaseRoom> page = caseRoomRepository.search(
                searchRequest.getPatientId(),
                searchRequest.getDoctorId(),
                searchRequest.getStatus(),
                searchRequest.getPriority(),
                pageable);

        accessLogService.log(
                authUser.getUser(),
                null,
                AuditAction.VIEW,
                ResourceType.SEARCH,
                null,
                AuditOutcome.SUCCESS);

        return page.map(CaseRoomResponseDto::fromEntity);
    }

    @Transactional
    @Override
    public CaseRoomResponseDto updateStatus(UUID caseRoomId, UpdateCaseRoomStatusRequest statusRequest, CustomUserPrincipal authUser, HttpServletRequest request) {
        CaseRoom caseRoom = findCaseRoomOrThrow(caseRoomId);
        
        CaseRoomStatus newStatus = statusRequest.getStatus();

        if (caseRoom.getStatus() == CaseRoomStatus.ARCHIVED) {
            throw new RuntimeException("Cannot update status of an archived case room");
        }

        caseRoom.setStatus(newStatus);
        
        if (newStatus == CaseRoomStatus.RESOLVED || newStatus == CaseRoomStatus.ARCHIVED) {
            caseRoom.setClosedAt(LocalDateTime.now());
        }

        CaseRoom updated = caseRoomRepository.save(caseRoom);

        accessLogService.log(
                authUser.getUser(),
                caseRoom.getPatient(),
                AuditAction.UPDATE,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return CaseRoomResponseDto.fromEntity(updated);
    }

    private CaseRoom findCaseRoomOrThrow(UUID caseRoomId) {
        return caseRoomRepository.findById(caseRoomId)
                .orElseThrow(() -> new RuntimeException("Case room not found with ID: " + caseRoomId));
    }

    private String resolveValidSortField(String sortBy) {
        return switch (sortBy) {
            case "updatedAt" -> "updatedAt";
            case "status" -> "status";
            case "priority" -> "priority";
            default -> "createdAt";
        };
    }
}
