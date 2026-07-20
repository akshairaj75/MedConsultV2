package com.backend.medconsult.service.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomMemberRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomMemberResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface CaseRoomMemberService {

    CaseRoomMemberResponseDto addMember(CaseRoomMemberRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    List<CaseRoomMemberResponseDto> getMembersForRoom(UUID caseRoomId, CustomUserPrincipal authUser, HttpServletRequest request);

    CaseRoomMemberResponseDto removeMember(UUID memberId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<CaseRoomMemberResponseDto> getMyCaseRooms(CustomUserPrincipal authUser, HttpServletRequest request);
}
