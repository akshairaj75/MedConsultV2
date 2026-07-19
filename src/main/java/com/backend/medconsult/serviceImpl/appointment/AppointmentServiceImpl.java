package com.backend.medconsult.serviceImpl.appointment;

import com.backend.medconsult.dto.appointment.AppointmentRequestDto;
import com.backend.medconsult.dto.appointment.AppointmentResponseDto;
import com.backend.medconsult.dto.appointment.AppointmentSearchRequest;
import com.backend.medconsult.dto.appointment.CancelAppointmentRequest;
import com.backend.medconsult.dto.appointment.UpdateAppointmentStatusRequest;
import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.entity.doctor.AppointmentSlot;
import com.backend.medconsult.entity.doctor.DoctorClinic;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.appointments.AppointmentStatus;
import com.backend.medconsult.enums.doctor.SlotStatus;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.NotificationType;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.appointments.AppointmentRepository;
import com.backend.medconsult.repository.doctor.AppointmentSlotRepository;
import com.backend.medconsult.repository.doctor.DoctorClinicRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.appointment.AppointmentService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;
import com.backend.medconsult.service.platformAndCompliance.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

        @Autowired
        private AppointmentRepository appointmentRepository;

        @Autowired
        private PatientRepository patientRepository;

        @Autowired
        private DoctorClinicRepository doctorClinicRepository;

        @Autowired
        private AppointmentSlotRepository appointmentSlotRepository;

        @Autowired
        private DoctorRepository doctorRepository;

        @Autowired
        private AccessLogService accessLogService;

        @Autowired
        private NotificationService notificationService;

        // ─── Book Appointment ──────────────────────────────────────────────

        @Transactional
        @Override
        public AppointmentResponseDto bookAppointment(AppointmentRequestDto dto,
                        CustomUserPrincipal authUser,
                        HttpServletRequest request) {

                User user = authUser.getUser();

                // 1. Resolve patient
                Patient patient = patientRepository.findById(dto.getPatientId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Patient not found with ID: " + dto.getPatientId()));

                // 2. Resolve doctor-clinic link
                DoctorClinic doctorClinic = doctorClinicRepository.findById(dto.getDcId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Doctor-Clinic association not found with ID: " + dto.getDcId()));

                if (Boolean.FALSE.equals(doctorClinic.getIsActive())) {
                        throw new RuntimeException("The selected Doctor-Clinic association is not active");
                }

                // 3. Resolve slot
                AppointmentSlot slot = appointmentSlotRepository.findById(dto.getSlotId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Appointment slot not found with ID: " + dto.getSlotId()));

                // 4. Validate slot availability
                if (slot.getStatus() != SlotStatus.AVAILABLE) {
                        throw new RuntimeException(
                                        "The selected slot is no longer available (status: " + slot.getStatus() + ")");
                }

                // 5. Validate slot belongs to the given dc
                if (!slot.getDoctorClinic().getDcId().equals(doctorClinic.getDcId())) {
                        throw new RuntimeException("The selected slot does not belong to the specified Doctor-Clinic");
                }

                // 6. Validate scheduled date matches slot date
                if (!slot.getSlotDate().equals(dto.getScheduledDate())) {
                        throw new RuntimeException(
                                        "Scheduled date does not match the slot date (" + slot.getSlotDate() + ")");
                }

                // 7. Prevent duplicate active appointments for same patient-doctor pair
                boolean hasActiveAppointment = appointmentRepository
                                .existsByPatient_PatientIdAndDoctorClinic_Doctor_DoctorIdAndStatusNot(
                                                patient.getPatientId(),
                                                doctorClinic.getDoctor().getDoctorId(),
                                                AppointmentStatus.CANCELLED);

                if (hasActiveAppointment) {
                        throw new RuntimeException(
                                        "Patient already has an active appointment with this doctor. " +
                                                        "Please cancel the existing one before booking a new one.");
                }

                // 8. Build appointment
                Appointment appointment = new Appointment();
                appointment.setPatient(patient);
                appointment.setDoctorClinic(doctorClinic);
                appointment.setSlot(slot);
                appointment.setAppointmentType(dto.getAppointmentType());
                appointment.setScheduledDate(dto.getScheduledDate());
                appointment.setStartTime(slot.getStartTime());
                appointment.setDurationMinutes(slot.getDoctorClinic() != null
                                ? calculateDuration(slot)
                                : (short) 30);
                appointment.setSessionType(dto.getSessionType());
                appointment.setReason(dto.getReason());
                appointment.setStatus(AppointmentStatus.SCHEDULED);

                // 9. Mark slot as BOOKED
                slot.setStatus(SlotStatus.BOOKED);
                appointmentSlotRepository.save(slot);

                // 10. Persist appointment
                Appointment saved = appointmentRepository.save(appointment);

                // 11. Link slot back to appointment
                slot.setAppointment(saved);
                appointmentSlotRepository.save(slot);

                notificationService.notify(
                                saved.getPatient().getUser().getUserId(), 
                                NotificationType.APPOINTMENT_CONFIRMED,
                                "Appointment booked", 
                                "Your appointment is scheduled for " + saved.getScheduledDate(),
                                "appointment", 
                                saved.getAppointmentId().toString());
                notificationService.notify(
                                saved.getDoctorClinic().getDoctor().getUser().getUserId(),
                                NotificationType.APPOINTMENT_CONFIRMED,
                                "New booking", "A patient booked a slot on " + saved.getScheduledDate(),
                                "appointment", 
                                saved.getAppointmentId().toString());

                // 12. Audit log
                accessLogService.log(
                                user,
                                patient,
                                AuditAction.CREATE,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return AppointmentResponseDto.fromEntity(saved);
        }

        // ─── Get By ID ─────────────────────────────────────────────────────

        @Override
        public AppointmentResponseDto getAppointmentById(UUID appointmentId,
                        CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Appointment appointment = findAppointmentOrThrow(appointmentId);

                accessLogService.log(
                                authUser.getUser(),
                                null,
                                AuditAction.VIEW,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return AppointmentResponseDto.fromEntity(appointment);
        }

        // ─── Search ────────────────────────────────────────────────────────

        @Override
        public Page<AppointmentResponseDto> searchAppointments(AppointmentSearchRequest searchRequest,
                        CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Sort sort = Sort.by(
                                "ASC".equalsIgnoreCase(searchRequest.getSortDir())
                                                ? Sort.Direction.ASC
                                                : Sort.Direction.DESC,
                                resolveValidSortField(searchRequest.getSortBy()));

                Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sort);

                Page<Appointment> page = appointmentRepository.search(
                                searchRequest.getPatientId(),
                                searchRequest.getDoctorId(),
                                searchRequest.getDcId(),
                                searchRequest.getStatus(),
                                searchRequest.getAppointmentType(),
                                searchRequest.getSessionType(),
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

                return page.map(AppointmentResponseDto::fromEntity);
        }

        // ─── Update Status ─────────────────────────────────────────────────

        @Transactional
        @Override
        public AppointmentResponseDto updateStatus(UUID appointmentId,
                        UpdateAppointmentStatusRequest statusRequest,
                        CustomUserPrincipal authUser,
                        HttpServletRequest httpRequest) {
                Appointment appointment = findAppointmentOrThrow(appointmentId);

                AppointmentStatus newStatus = statusRequest.getStatus();

                // Prevent re-cancelling or going back from terminal states
                if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
                        throw new RuntimeException("Cannot update status of a cancelled appointment");
                }
                if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                        throw new RuntimeException("Cannot update status of a completed appointment");
                }
                if (newStatus == AppointmentStatus.CANCELLED) {
                        throw new RuntimeException(
                                        "Use the dedicated cancel endpoint to cancel an appointment");
                }

                appointment.setStatus(newStatus);
                Appointment updated = appointmentRepository.save(appointment);

                accessLogService.log(
                                authUser.getUser(),
                                null,
                                AuditAction.UPDATE,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return AppointmentResponseDto.fromEntity(updated);
        }

        // ─── Cancel ────────────────────────────────────────────────────────

        @Transactional
        @Override
        public AppointmentResponseDto cancelAppointment(UUID appointmentId,
                        CancelAppointmentRequest cancelRequest,
                        CustomUserPrincipal authUser,
                        HttpServletRequest httpRequest) {
                Appointment appointment = findAppointmentOrThrow(appointmentId);
                User user = authUser.getUser();

                if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
                        throw new RuntimeException("Appointment is already cancelled");
                }
                if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                        throw new RuntimeException("Cannot cancel a completed appointment");
                }

                // Release the slot back to AVAILABLE
                AppointmentSlot slot = appointment.getSlot();
                if (slot != null) {
                        slot.setStatus(SlotStatus.AVAILABLE);
                        slot.setAppointment(null);
                        appointmentSlotRepository.save(slot);
                }

                appointment.setStatus(AppointmentStatus.CANCELLED);
                appointment.setCancelledBy(user);
                appointment.setCancelReason(cancelRequest.getCancelReason());

                Appointment cancelled = appointmentRepository.save(appointment);

                accessLogService.log(
                                user,
                                null,
                                AuditAction.UPDATE,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return AppointmentResponseDto.fromEntity(cancelled);
        }

        // ─── Upcoming – Patient ────────────────────────────────────────────

        @Override
        public List<AppointmentResponseDto> getMyUpcomingAppointments(CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                User user = authUser.getUser();

                Patient patient = patientRepository.findByUser_UserId(user.getUserId())
                                .orElseThrow(() -> new RuntimeException(
                                                "No patient profile found for the current user"));

                List<Appointment> upcoming = appointmentRepository.findUpcomingByPatient(
                                patient.getPatientId(), LocalDate.now());

                accessLogService.log(
                                user,
                                patient,
                                AuditAction.VIEW,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return upcoming.stream().map(AppointmentResponseDto::fromEntity).toList();
        }

        // ─── Upcoming – Doctor ─────────────────────────────────────────────

        @Override
        public List<AppointmentResponseDto> getDoctorUpcomingAppointments(CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                // Doctor's userId -> find doctor -> use doctorId
                User user = authUser.getUser();
                UUID doctorId = resolveDoctorIdFromUser(user);

                List<Appointment> upcoming = appointmentRepository.findUpcomingByDoctor(
                                doctorId, LocalDate.now());

                accessLogService.log(
                                user,
                                null,
                                AuditAction.VIEW,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return upcoming.stream().map(AppointmentResponseDto::fromEntity).toList();
        }

        // ─── By Patient (paginated) ────────────────────────────────────────

        @Override
        public Page<AppointmentResponseDto> getAppointmentsByPatient(UUID patientId,
                        int page,
                        int size,
                        CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                patientRepository.findById(patientId)
                                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

                Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "scheduledDate"));
                Page<Appointment> results = appointmentRepository
                                .findByPatient_PatientIdOrderByScheduledDateDesc(patientId, pageable);

                accessLogService.log(
                                authUser.getUser(),
                                null,
                                AuditAction.VIEW,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return results.map(AppointmentResponseDto::fromEntity);
        }

        // ─── By Doctor (paginated) ─────────────────────────────────────────

        @Override
        public Page<AppointmentResponseDto> getAppointmentsByDoctor(UUID doctorId,
                        int page,
                        int size,
                        CustomUserPrincipal authUser,
                        HttpServletRequest request) {
                Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "scheduledDate"));
                Page<Appointment> results = appointmentRepository
                                .findByDoctorClinic_Doctor_DoctorIdOrderByScheduledDateDesc(doctorId, pageable);

                accessLogService.log(
                                authUser.getUser(),
                                null,
                                AuditAction.VIEW,
                                ResourceType.APPOINTMENT,
                                null,
                                AuditOutcome.SUCCESS);

                return results.map(AppointmentResponseDto::fromEntity);
        }

        // ─── Private Helpers ───────────────────────────────────────────────

        private Appointment findAppointmentOrThrow(UUID appointmentId) {
                return appointmentRepository.findById(appointmentId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Appointment not found with ID: " + appointmentId));
        }

        /**
         * Compute duration in minutes from slot start/end times. Falls back to 30 min.
         */
        private short calculateDuration(AppointmentSlot slot) {
                if (slot.getStartTime() != null && slot.getEndTime() != null) {
                        long minutes = java.time.Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();
                        if (minutes > 0 && minutes <= Short.MAX_VALUE) {
                                return (short) minutes;
                        }
                }
                return 30;
        }

        /**
         * Guard against injection via sortBy field — allow only safe field names.
         */
        private String resolveValidSortField(String sortBy) {
                return switch (sortBy) {
                        case "startTime" -> "startTime";
                        case "status" -> "status";
                        case "createdAt" -> "createdAt";
                        case "updatedAt" -> "updatedAt";
                        default -> "scheduledDate";
                };
        }

        /**
         * Resolve doctorId from the authenticated user's doctor profile.
         */
        private UUID resolveDoctorIdFromUser(User user) {
                return doctorRepository.findByUser(user)
                                .map(doc -> doc.getDoctorId())
                                .orElseThrow(() -> new RuntimeException(
                                                "No doctor profile found for the current user"));
        }
}
