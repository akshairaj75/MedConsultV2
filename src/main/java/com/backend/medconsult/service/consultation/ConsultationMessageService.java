package com.backend.medconsult.service.consultation;

import com.backend.medconsult.dto.consultation.ConsultationMessageRequestDto;
import com.backend.medconsult.dto.consultation.ConsultationMessageResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface ConsultationMessageService {

    ConsultationMessageResponseDto sendMessage(ConsultationMessageRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    List<ConsultationMessageResponseDto> getMessagesForConsultation(UUID consultationId, CustomUserPrincipal authUser, HttpServletRequest request);

    ConsultationMessageResponseDto markAsRead(UUID messageId, CustomUserPrincipal authUser, HttpServletRequest request);
}
