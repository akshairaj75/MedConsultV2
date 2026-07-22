package com.backend.medconsult.controller.consultation;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.backend.medconsult.dto.consultation.ConsultationMessageRequestDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.consultation.ConsultationMessageService;

import jakarta.validation.Valid;

@Controller
public class WebSocketChatController {

    @Autowired
    private ConsultationMessageService consultationMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void handleSendMessage(@Valid @Payload ConsultationMessageRequestDto dto, Principal principal) {
        CustomUserPrincipal authUser = getAuthUser(principal);
        if (authUser == null) {
            throw new AccessDeniedException("User is not authenticated for WebSocket messaging");
        }

        consultationMessageService.sendMessage(dto, authUser, null);
    }

    @MessageMapping("/chat.typing")
    public void handleTypingIndicator(@Payload Map<String, Object> payload, Principal principal) {
        CustomUserPrincipal authUser = getAuthUser(principal);
        if (authUser == null || payload == null) {
            return;
        }

        Object consultationIdObj = payload.get("consultationId");
        if (consultationIdObj == null) {
            return;
        }

        String consultationIdStr = consultationIdObj.toString();
        Boolean isTyping = Boolean.TRUE.equals(payload.get("isTyping"));

        Map<String, Object> typingEvent = new HashMap<>();
        typingEvent.put("consultationId", consultationIdStr);
        typingEvent.put("userId", authUser.getUserId());
        typingEvent.put("userName", authUser.getUser().getFullName());
        typingEvent.put("isTyping", isTyping);

        messagingTemplate.convertAndSend("/topic/consultation/" + consultationIdStr + "/typing", (Object) typingEvent);
    }

    private CustomUserPrincipal getAuthUser(Principal principal) {
        if (principal == null) {
            return null;
        }
        if (principal instanceof CustomUserPrincipal customPrincipal) {
            return customPrincipal;
        }
        if (principal instanceof Authentication auth) {
            if (auth.getPrincipal() instanceof CustomUserPrincipal customPrincipal) {
                return customPrincipal;
            }
        }
        return null;
    }
}

