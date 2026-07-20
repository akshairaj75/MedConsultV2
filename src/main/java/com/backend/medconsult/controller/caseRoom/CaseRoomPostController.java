package com.backend.medconsult.controller.caseRoom;

import com.backend.medconsult.dto.caseRoom.CaseRoomPostRequestDto;
import com.backend.medconsult.dto.caseRoom.CaseRoomPostResponseDto;
import com.backend.medconsult.dto.caseRoom.UpdateCaseRoomPostActionRequest;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.caseRoom.CaseRoomPostService;
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
@RequestMapping("/api/medconsult/caserooms/posts")
public class CaseRoomPostController {

    @Autowired
    private CaseRoomPostService caseRoomPostService;

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<CaseRoomPostResponseDto> createPost(
            @Valid @RequestBody CaseRoomPostRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        CaseRoomPostResponseDto response = caseRoomPostService.createPost(dto, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/room/{caseRoomId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<CaseRoomPostResponseDto>> getPostsForRoom(
            @PathVariable("caseRoomId") UUID caseRoomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<CaseRoomPostResponseDto> result = caseRoomPostService.getPostsForRoom(caseRoomId, page, size, authUser, request);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{postId}/action-status")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SYSTEM_ADMIN')")
    public ResponseEntity<CaseRoomPostResponseDto> updateActionStatus(
            @PathVariable("postId") UUID postId,
            @Valid @RequestBody UpdateCaseRoomPostActionRequest statusRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        CaseRoomPostResponseDto response = caseRoomPostService.updateActionStatus(postId, statusRequest, authUser, request);
        return ResponseEntity.ok(response);
    }
}
