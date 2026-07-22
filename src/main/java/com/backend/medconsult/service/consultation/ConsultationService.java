package com.backend.medconsult.service.consultation;

import com.backend.medconsult.dto.consultation.ConsultationRequestDto;
import com.backend.medconsult.dto.consultation.ConsultationResponseDto;
import com.backend.medconsult.dto.consultation.ConsultationSearchRequest;
import com.backend.medconsult.dto.consultation.UpdateConsultationStatusRequest;
import com.backend.medconsult.security.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ConsultationService {

    ConsultationResponseDto openConsultation(ConsultationRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    ConsultationResponseDto getConsultationById(UUID consultationId, CustomUserPrincipal authUser, HttpServletRequest request);

    Page<ConsultationResponseDto> searchConsultations(ConsultationSearchRequest searchRequest, CustomUserPrincipal authUser, HttpServletRequest request);

    ConsultationResponseDto updateStatus(UUID consultationId, UpdateConsultationStatusRequest statusRequest, CustomUserPrincipal authUser, HttpServletRequest request);

    Page<ConsultationResponseDto> getConsultationsByPatient(UUID patientId, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request);

    Page<ConsultationResponseDto> getConsultationsByDoctor(UUID doctorId, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request);

    Page<ConsultationResponseDto> getMyDoctorConsultations(CustomUserPrincipal authUser, int page, int size,
            HttpServletRequest request);
}
