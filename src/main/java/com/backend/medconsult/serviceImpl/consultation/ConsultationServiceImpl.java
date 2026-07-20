package com.backend.medconsult.serviceImpl.consultation;

import com.backend.medconsult.dto.consultation.ConsultationRequestDto;
import com.backend.medconsult.dto.consultation.ConsultationResponseDto;
import com.backend.medconsult.dto.consultation.ConsultationSearchRequest;
import com.backend.medconsult.dto.consultation.UpdateConsultationStatusRequest;
import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.entity.consultation.Consultation;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.consultation.ConsultationStatus;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.appointments.AppointmentRepository;
import com.backend.medconsult.repository.consultation.ConsultationRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.consultation.ConsultationService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AccessLogService accessLogService;

    @Transactional
    @Override
    public ConsultationResponseDto openConsultation(ConsultationRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {
        User user = authUser.getUser();

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        Appointment appointment = null;
        if (dto.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + dto.getAppointmentId()));
        }

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setAppointment(appointment);
        consultation.setSubject(dto.getSubject());
        consultation.setIsUrgent(dto.getIsUrgent() != null ? dto.getIsUrgent() : false);
        consultation.setStatus(ConsultationStatus.OPEN);
        consultation.setOpenedAt(LocalDateTime.now());
        consultation.setCreatedAt(LocalDateTime.now());

        Consultation saved = consultationRepository.save(consultation);

        accessLogService.log(
                user,
                patient,
                AuditAction.CREATE,
                ResourceType.CONSULTATION,
                null,
                AuditOutcome.SUCCESS);

        return ConsultationResponseDto.fromEntity(saved);
    }

    @Override
    public ConsultationResponseDto getConsultationById(UUID consultationId, CustomUserPrincipal authUser, HttpServletRequest request) {
        Consultation consultation = findConsultationOrThrow(consultationId);

        accessLogService.log(
                authUser.getUser(),
                consultation.getPatient(),
                AuditAction.VIEW,
                ResourceType.CONSULTATION,
                null,
                AuditOutcome.SUCCESS);

        return ConsultationResponseDto.fromEntity(consultation);
    }

    @Override
    public Page<ConsultationResponseDto> searchConsultations(ConsultationSearchRequest searchRequest, CustomUserPrincipal authUser, HttpServletRequest request) {
        Sort sort = Sort.by(
                "ASC".equalsIgnoreCase(searchRequest.getSortDir())
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC,
                resolveValidSortField(searchRequest.getSortBy()));

        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sort);

        Page<Consultation> page = consultationRepository.search(
                searchRequest.getPatientId(),
                searchRequest.getDoctorId(),
                searchRequest.getStatus(),
                searchRequest.getIsUrgent(),
                searchRequest.getFromDate(),
                searchRequest.getToDate(),
                pageable);

        accessLogService.log(
                authUser.getUser(),
                null,
                AuditAction.VIEW,
                ResourceType.SEARCH,
                null,
                AuditOutcome.SUCCESS);

        return page.map(ConsultationResponseDto::fromEntity);
    }

    @Transactional
    @Override
    public ConsultationResponseDto updateStatus(UUID consultationId, UpdateConsultationStatusRequest statusRequest, CustomUserPrincipal authUser, HttpServletRequest request) {
        Consultation consultation = findConsultationOrThrow(consultationId);
        
        ConsultationStatus newStatus = statusRequest.getStatus();

        if (consultation.getStatus() == ConsultationStatus.ARCHIVED) {
            throw new RuntimeException("Cannot update status of an archived consultation");
        }

        consultation.setStatus(newStatus);
        
        if (newStatus == ConsultationStatus.CLOSED || newStatus == ConsultationStatus.ARCHIVED) {
            consultation.setClosedAt(LocalDateTime.now());
        }

        Consultation updated = consultationRepository.save(consultation);

        accessLogService.log(
                authUser.getUser(),
                consultation.getPatient(),
                AuditAction.UPDATE,
                ResourceType.CONSULTATION,
                null,
                AuditOutcome.SUCCESS);

        return ConsultationResponseDto.fromEntity(updated);
    }

    @Override
    public Page<ConsultationResponseDto> getConsultationsByPatient(UUID patientId, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

        Pageable pageable = PageRequest.of(page, size);
        Page<Consultation> results = consultationRepository.findByPatient_PatientIdOrderByLastMessageAtDesc(patientId, pageable);

        accessLogService.log(
                authUser.getUser(),
                results.hasContent() ? results.getContent().get(0).getPatient() : null,
                AuditAction.VIEW,
                ResourceType.CONSULTATION,
                null,
                AuditOutcome.SUCCESS);

        return results.map(ConsultationResponseDto::fromEntity);
    }

    @Override
    public Page<ConsultationResponseDto> getConsultationsByDoctor(UUID doctorId, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request) {
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));

        Pageable pageable = PageRequest.of(page, size);
        Page<Consultation> results = consultationRepository.findByDoctor_DoctorIdOrderByLastMessageAtDesc(doctorId, pageable);

        accessLogService.log(
                authUser.getUser(),
                null,
                AuditAction.VIEW,
                ResourceType.CONSULTATION,
                null,
                AuditOutcome.SUCCESS);

        return results.map(ConsultationResponseDto::fromEntity);
    }

    private Consultation findConsultationOrThrow(UUID consultationId) {
        return consultationRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation not found with ID: " + consultationId));
    }

    private String resolveValidSortField(String sortBy) {
        return switch (sortBy) {
            case "openedAt" -> "openedAt";
            case "lastMessageAt" -> "lastMessageAt";
            case "status" -> "status";
            default -> "createdAt";
        };
    }
}
