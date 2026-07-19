package com.backend.medconsult.service.appointment;

import com.backend.medconsult.dto.appointment.AppointmentRequestDto;
import com.backend.medconsult.dto.appointment.AppointmentResponseDto;
import com.backend.medconsult.dto.appointment.AppointmentSearchRequest;
import com.backend.medconsult.dto.appointment.CancelAppointmentRequest;
import com.backend.medconsult.dto.appointment.UpdateAppointmentStatusRequest;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.domain.Page;

public interface AppointmentService {

    /**
     * Book a new appointment. Validates slot availability and prevents duplicate
     * active appointments for the same patient-doctor pair.
     */
    AppointmentResponseDto bookAppointment(AppointmentRequestDto dto,
                                           CustomUserPrincipal authUser,
                                           HttpServletRequest request);

    /**
     * Retrieve a single appointment by its ID.
     */
    AppointmentResponseDto getAppointmentById(String appointmentId,
                                              CustomUserPrincipal authUser,
                                              HttpServletRequest request);

    /**
     * Paginated search with optional filters.
     */
    Page<AppointmentResponseDto> searchAppointments(AppointmentSearchRequest searchRequest,
                                                     CustomUserPrincipal authUser,
                                                     HttpServletRequest request);

    /**
     * Update the status of an appointment (CONFIRMED, COMPLETED, NO_SHOW).
     * Cancellation is handled by a dedicated endpoint.
     */
    AppointmentResponseDto updateStatus(String appointmentId,
                                        UpdateAppointmentStatusRequest request,
                                        CustomUserPrincipal authUser,
                                        HttpServletRequest httpRequest);

    /**
     * Cancel an appointment. Marks the slot as AVAILABLE again.
     */
    AppointmentResponseDto cancelAppointment(String appointmentId,
                                             CancelAppointmentRequest cancelRequest,
                                             CustomUserPrincipal authUser,
                                             HttpServletRequest httpRequest);

    /**
     * Get all upcoming appointments for the currently authenticated patient.
     */
    List<AppointmentResponseDto> getMyUpcomingAppointments(CustomUserPrincipal authUser,
                                                            HttpServletRequest request);

    /**
     * Get all upcoming appointments for the currently authenticated doctor.
     */
    List<AppointmentResponseDto> getDoctorUpcomingAppointments(CustomUserPrincipal authUser,
                                                               HttpServletRequest request);

    /**
     * Get all appointments for a specific patient (paginated).
     */
    Page<AppointmentResponseDto> getAppointmentsByPatient(java.util.UUID patientId,
                                                           int page,
                                                           int size,
                                                           CustomUserPrincipal authUser,
                                                           HttpServletRequest request);

    /**
     * Get all appointments for a specific doctor (paginated).
     */
    Page<AppointmentResponseDto> getAppointmentsByDoctor(java.util.UUID doctorId,
                                                          int page,
                                                          int size,
                                                          CustomUserPrincipal authUser,
                                                          HttpServletRequest request);
}
