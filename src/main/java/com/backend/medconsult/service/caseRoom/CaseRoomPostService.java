package com.backend.medconsult.service.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomPostRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomPostResponseDto;
import com.backend.medconsult.dto.caseRoom.UpdateCaseRoomPostActionRequest;
import com.backend.medconsult.security.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CaseRoomPostService {

    CaseRoomPostResponseDto createPost(CaseRoomPostRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    Page<CaseRoomPostResponseDto> getPostsForRoom(UUID caseRoomId, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request);

    CaseRoomPostResponseDto updateActionStatus(UUID postId, UpdateCaseRoomPostActionRequest statusRequest, CustomUserPrincipal authUser, HttpServletRequest request);
}
