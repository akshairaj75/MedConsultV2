package com.backend.medconsult.serviceImpl.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomPostRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomPostResponseDto;
import com.backend.medconsult.dto.caseRoom.UpdateCaseRoomPostActionRequest;
import com.backend.medconsult.entity.caseRoom.CaseRoom;
import com.backend.medconsult.entity.caseRoom.CaseRoomPost;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.caseRoom.ActionStatus;
import com.backend.medconsult.enums.caseRoom.PostType;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.NotificationType;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.caseRoom.CaseRoomPostRepository;
import com.backend.medconsult.repository.caseRoom.CaseRoomRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.platformAndCompliance.FileMetadataRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.caseRoom.CaseRoomPostService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import com.backend.medconsult.service.platformAndCompliance.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CaseRoomPostServiceImpl implements CaseRoomPostService {

    @Autowired
    private CaseRoomPostRepository caseRoomPostRepository;

    @Autowired
    private CaseRoomRepository caseRoomRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    @Override
    public CaseRoomPostResponseDto createPost(CaseRoomPostRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {
        User user = authUser.getUser();

        Doctor author = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Current user is not registered as a doctor"));

        CaseRoom caseRoom = caseRoomRepository.findById(dto.getCaseRoomId())
                .orElseThrow(() -> new RuntimeException("Case room not found with ID: " + dto.getCaseRoomId()));

        CaseRoomPost post = new CaseRoomPost();
        post.setCaseRoom(caseRoom);
        post.setAuthor(author);
        post.setPostType(dto.getPostType());
        post.setBody(dto.getBody());
        post.setTags(dto.getTags());

        if (dto.getFileId() != null) {
            FileMetadata file = fileMetadataRepository.findById(dto.getFileId())
                    .orElseThrow(() -> new RuntimeException("File not found with ID: " + dto.getFileId()));
            post.setFile(file);
        }

        if (dto.getPostType() == PostType.ACTION_ITEM && dto.getActionAssignedTo() != null) {
            Doctor assignee = doctorRepository.findById(dto.getActionAssignedTo())
                    .orElseThrow(() -> new RuntimeException("Assignee Doctor not found with ID: " + dto.getActionAssignedTo()));
            post.setActionAssignedTo(assignee);
            post.setActionDueDate(dto.getActionDueDate());
            post.setActionStatus(ActionStatus.PENDING);

            // Notify assignee
            notificationService.notify(
                    assignee.getUser().getUserId(),
                    NotificationType.NEW_MESSAGE,
                    "Action Item Assigned",
                    "You have a new action item in Case Room: " + caseRoom.getTitle(),
                    "case_room",
                    caseRoom.getCaseRoomId().toString()
            );
        }

        CaseRoomPost saved = caseRoomPostRepository.save(post);

        accessLogService.log(
                user,
                caseRoom.getPatient(),
                AuditAction.CREATE,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return CaseRoomPostResponseDto.fromEntity(saved);
    }

    @Override
    public Page<CaseRoomPostResponseDto> getPostsForRoom(UUID caseRoomId, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request) {
        CaseRoom caseRoom = caseRoomRepository.findById(caseRoomId)
                .orElseThrow(() -> new RuntimeException("Case room not found with ID: " + caseRoomId));

        Pageable pageable = PageRequest.of(page, size);
        Page<CaseRoomPost> posts = caseRoomPostRepository.findByCaseRoom_CaseRoomIdOrderByPostedAtAsc(caseRoomId, pageable);

        accessLogService.log(
                authUser.getUser(),
                caseRoom.getPatient(),
                AuditAction.VIEW,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return posts.map(CaseRoomPostResponseDto::fromEntity);
    }

    @Transactional
    @Override
    public CaseRoomPostResponseDto updateActionStatus(UUID postId, UpdateCaseRoomPostActionRequest statusRequest, CustomUserPrincipal authUser, HttpServletRequest request) {
        CaseRoomPost post = caseRoomPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));

        if (post.getPostType() != PostType.ACTION_ITEM) {
            throw new RuntimeException("Cannot update action status on a non-action item post");
        }

        post.setActionStatus(statusRequest.getActionStatus());

        CaseRoomPost updated = caseRoomPostRepository.save(post);

        accessLogService.log(
                authUser.getUser(),
                post.getCaseRoom().getPatient(),
                AuditAction.UPDATE,
                ResourceType.CASE_ROOM,
                null,
                AuditOutcome.SUCCESS);

        return CaseRoomPostResponseDto.fromEntity(updated);
    }
}
