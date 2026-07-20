package com.backend.medconsult.service.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomResponseDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomSearchRequest;
import com.backend.medconsult.dto.caseRoom.UpdateCaseRoomStatusRequest;
import com.backend.medconsult.security.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CaseRoomService {

    CaseRoomResponseDto openCaseRoom(CaseRoomRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    CaseRoomResponseDto getCaseRoomById(UUID caseRoomId, CustomUserPrincipal authUser, HttpServletRequest request);

    Page<CaseRoomResponseDto> searchCaseRooms(CaseRoomSearchRequest searchRequest, CustomUserPrincipal authUser, HttpServletRequest request);

    CaseRoomResponseDto updateStatus(UUID caseRoomId, UpdateCaseRoomStatusRequest statusRequest, CustomUserPrincipal authUser, HttpServletRequest request);
}
