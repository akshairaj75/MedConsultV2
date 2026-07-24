package com.backend.medconsult.controller.appointment;

import com.backend.medconsult.dto.appointment.AppointmentRequestDto;
import com.backend.medconsult.dto.appointment.AppointmentResponseDto;
import com.backend.medconsult.dto.appointment.AppointmentSearchRequest;
import com.backend.medconsult.dto.appointment.CancelAppointmentRequest;
import com.backend.medconsult.dto.appointment.UpdateAppointmentStatusRequest;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.appointment.AppointmentService;
import com.backend.medconsult.service.clinic.ClinicAuthorizationService;
import com.backend.medconsult.enums.usersAndPatients.UserRole;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for the Appointment module.
 *
 * Base URL: /api/medconsult/appointments
 */
@RestController
@RequestMapping("/api/medconsult/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ClinicAuthorizationService clinicAuthorizationService;

    // ─── Book ──────────────────────────────────────────────────────────

    /**
     * POST /api/medconsult/appointments/book
     * Book a new appointment.
     * Accessible by PATIENT and CLINIC_ADMIN roles.
     */
    @PostMapping("/book")
    @PreAuthorize("hasAnyRole('PATIENT', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(
            @Valid @RequestBody AppointmentRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        if (authUser != null && authUser.getUser().getRole() == UserRole.CLINIC_ADMIN) {
            clinicAuthorizationService.verifyDoctorClinicAdmin(dto.getDcId(), authUser.getUserId());
        }

        AppointmentResponseDto response = appointmentService.bookAppointment(dto, authUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─── Get By ID ─────────────────────────────────────────────────────

    /**
     * GET /api/medconsult/appointments/{id}
     * Retrieve appointment details by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(
            @PathVariable("id") UUID appointmentId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        AppointmentResponseDto response = appointmentService.getAppointmentById(appointmentId, authUser, request);
        return ResponseEntity.ok(response);
    }

    // ─── Search ────────────────────────────────────────────────────────

    /**
     * POST /api/medconsult/appointments/search
     * Flexible paginated search with optional filters.
     */
    @PostMapping("/search")
    public ResponseEntity<Page<AppointmentResponseDto>> searchAppointments(
            @RequestBody AppointmentSearchRequest searchRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<AppointmentResponseDto> page = appointmentService.searchAppointments(searchRequest, authUser, request);
        return ResponseEntity.ok(page);
    }

    // ─── Update Status ─────────────────────────────────────────────────

    /**
     * PATCH /api/medconsult/appointments/{id}/status
     * Update status to CONFIRMED, COMPLETED, or NO_SHOW.
     * Restricted to DOCTOR and CLINIC_ADMIN.
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<AppointmentResponseDto> updateStatus(
            @PathVariable("id") UUID appointmentId,
            @Valid @RequestBody UpdateAppointmentStatusRequest statusRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        if (authUser != null && authUser.getUser().getRole() == UserRole.CLINIC_ADMIN) {
            clinicAuthorizationService.verifyAppointmentAdmin(appointmentId, authUser.getUserId());
        }

        AppointmentResponseDto response = appointmentService.updateStatus(appointmentId, statusRequest, authUser,
                request);
        return ResponseEntity.ok(response);
    }

    // ─── Cancel ────────────────────────────────────────────────────────

    /**
     * PATCH /api/medconsult/appointments/{id}/cancel
     * Cancel an appointment and release the slot.
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponseDto> cancelAppointment(
            @PathVariable("id") UUID appointmentId,
            @Valid @RequestBody CancelAppointmentRequest cancelRequest,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        if (authUser != null && authUser.getUser().getRole() == UserRole.CLINIC_ADMIN) {
            clinicAuthorizationService.verifyAppointmentAdmin(appointmentId, authUser.getUserId());
        }

        AppointmentResponseDto response = appointmentService.cancelAppointment(appointmentId, cancelRequest, authUser,
                request);
        return ResponseEntity.ok(response);
    }

    // ─── My Upcoming (Patient) ─────────────────────────────────────────

    /**
     * GET /api/medconsult/appointments/my/upcoming
     * Get upcoming appointments for the authenticated patient.
     */
    @GetMapping("/my/upcoming")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<AppointmentResponseDto>> getMyUpcomingAppointments(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        List<AppointmentResponseDto> upcoming = appointmentService.getMyUpcomingAppointments(authUser, request);
        return ResponseEntity.ok(upcoming);
    }

    // ─── Doctor Upcoming ───────────────────────────────────────────────

    /**
     * GET /api/medconsult/appointments/doctor/upcoming
     * Get upcoming appointments for the authenticated doctor.
     */
    @GetMapping("/doctor/upcoming")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN')")
    public ResponseEntity<List<AppointmentResponseDto>> getDoctorUpcomingAppointments(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        List<AppointmentResponseDto> upcoming = appointmentService.getDoctorUpcomingAppointments(authUser, request);
        return ResponseEntity.ok(upcoming);
    }

    // ─── By Patient ────────────────────────────────────────────────────

    /**
     * GET /api/medconsult/appointments/patient/{patientId}
     * Get paginated appointments for a specific patient.
     */
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<AppointmentResponseDto>> getAppointmentsByPatient(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<AppointmentResponseDto> result = appointmentService.getAppointmentsByPatient(patientId, page, size,
                authUser, request);
        return ResponseEntity.ok(result);
    }

    // ─── By Doctor ─────────────────────────────────────────────────────

    /**
     * GET /api/medconsult/appointments/doctor/{doctorId}
     * Get paginated appointments for a specific doctor.
     */
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('CLINIC_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<Page<AppointmentResponseDto>> getAppointmentsByDoctor(
            @PathVariable UUID doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {

        Page<AppointmentResponseDto> result = appointmentService.getAppointmentsByDoctor(doctorId, page, size, authUser,
                request);
        return ResponseEntity.ok(result);
    }
}
