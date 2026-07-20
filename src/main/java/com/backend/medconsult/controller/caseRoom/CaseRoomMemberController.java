package com.backend.medconsult.controller.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomMemberRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomMemberResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.caseRoom.CaseRoomMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/caserooms/members")
public class CaseRoomMemberController {

    @Autowired
    private CaseRoomMemberService caseRoomMemberService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<CaseRoomMemberResponseDto> addMember(
            @Valid @RequestBody CaseRoomMemberRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        CaseRoomMemberResponseDto response = caseRoomMemberService.addMember(dto, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/room/{caseRoomId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<List<CaseRoomMemberResponseDto>> getMembersForRoom(
            @PathVariable("caseRoomId") UUID caseRoomId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        List<CaseRoomMemberResponseDto> response = caseRoomMemberService.getMembersForRoom(caseRoomId, authUser, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<CaseRoomMemberResponseDto> removeMember(
            @PathVariable("memberId") UUID memberId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        CaseRoomMemberResponseDto response = caseRoomMemberService.removeMember(memberId, authUser, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-rooms")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public ResponseEntity<List<CaseRoomMemberResponseDto>> getMyCaseRooms(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        List<CaseRoomMemberResponseDto> response = caseRoomMemberService.getMyCaseRooms(authUser, request);
        return ResponseEntity.ok(response);
    }
}
