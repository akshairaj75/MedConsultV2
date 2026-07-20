package com.backend.medconsult.controller.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomResponseDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomSearchRequest;
import com.backend.medconsult.dto.caseRoom.UpdateCaseRoomStatusRequest;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.caseRoom.CaseRoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/medconsult/caserooms")
public class CaseRoomController {

    @Autowired
    private CaseRoomService caseRoomService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<CaseRoomResponseDto> openCaseRoom(
            @Valid @RequestBody CaseRoomRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        CaseRoomResponseDto response = caseRoomService.openCaseRoom(dto, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<CaseRoomResponseDto> getCaseRoomById(
            @PathVariable("id") UUID caseRoomId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        CaseRoomResponseDto response = caseRoomService.getCaseRoomById(caseRoomId, authUser, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<CaseRoomResponseDto>> searchCaseRooms(
            @RequestBody CaseRoomSearchRequest searchRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<CaseRoomResponseDto> page = caseRoomService.searchCaseRooms(searchRequest, authUser, request);
        return ResponseEntity.ok(page);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<CaseRoomResponseDto> updateStatus(
            @PathVariable("id") UUID caseRoomId,
            @Valid @RequestBody UpdateCaseRoomStatusRequest statusRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        CaseRoomResponseDto response = caseRoomService.updateStatus(caseRoomId, statusRequest, authUser, request);
        return ResponseEntity.ok(response);
    }
}
