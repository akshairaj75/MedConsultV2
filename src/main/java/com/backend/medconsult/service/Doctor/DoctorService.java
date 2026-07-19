package com.backend.medconsult.service.Doctor;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.backend.medconsult.dto.doctor.AppointmentSlotRequestDto;
import com.backend.medconsult.dto.doctor.AppointmentSlotResponseDto;
import com.backend.medconsult.dto.doctor.DoctorClinicRequestDto;
import com.backend.medconsult.dto.doctor.DoctorClinicResponseDto;
import com.backend.medconsult.dto.doctor.DoctorDetailResponse;
import com.backend.medconsult.dto.doctor.DoctorLanguageRequestDto;
import com.backend.medconsult.dto.doctor.DoctorLanguageResponseDto;
import com.backend.medconsult.dto.doctor.DoctorLeaveRequestDto;
import com.backend.medconsult.dto.doctor.DoctorLeaveResponseDto;
import com.backend.medconsult.dto.doctor.DoctorQualificationRequestDto;
import com.backend.medconsult.dto.doctor.DoctorQualificationResponseDto;
import com.backend.medconsult.dto.doctor.DoctorRequestDto;
import com.backend.medconsult.dto.doctor.DoctorResponseDto;
import com.backend.medconsult.dto.doctor.DoctorScheduleRequestDto;
import com.backend.medconsult.dto.doctor.DoctorScheduleResponseDto;
import com.backend.medconsult.dto.doctor.DoctorSpecialtyRequestDto;
import com.backend.medconsult.dto.doctor.DoctorSpecialtyResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface DoctorService {

    // ─── Core Doctor CRUD ──────────────────────────────────────────────

    // Page<DoctorResponseDto> searchDoctors(String name, UUID specialtyId, Boolean isActive, int page, int size, CustomUserPrincipal authUser, HttpServletRequest request);

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto addDoctor(DoctorRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    DoctorResponseDto updateDoctor(UUID id, DoctorRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String deleteDoctor(UUID id, CustomUserPrincipal authUser, HttpServletRequest request);

    DoctorDetailResponse getDoctorProfile(UUID id, CustomUserPrincipal authUser, HttpServletRequest request);

    // ─── Doctor-Clinic (dc) ────────────────────────────────────────────

    DoctorClinicResponseDto addDoctorClinic(DoctorClinicRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    DoctorClinicResponseDto updateDoctorClinic(UUID dcId, DoctorClinicRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String removeDoctorClinic(UUID dcId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<DoctorClinicResponseDto> getDoctorClinics(UUID doctorId, CustomUserPrincipal authUser,
            HttpServletRequest request);

    // ─── Specialties ───────────────────────────────────────────────────

    DoctorSpecialtyResponseDto addSpecialty(DoctorSpecialtyRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    DoctorSpecialtyResponseDto updateSpecialty(UUID specialtyId, DoctorSpecialtyRequestDto dto,
            CustomUserPrincipal authUser, HttpServletRequest request);

    String removeSpecialty(UUID specialtyId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<DoctorSpecialtyResponseDto> getDoctorSpecialties(UUID doctorId, CustomUserPrincipal authUser,
            HttpServletRequest request);

    // ─── Languages ─────────────────────────────────────────────────────

    DoctorLanguageResponseDto addLanguage(DoctorLanguageRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String removeLanguage(UUID languageId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<DoctorLanguageResponseDto> getDoctorLanguages(UUID doctorId, CustomUserPrincipal authUser,
            HttpServletRequest request);

    // ─── Qualifications ────────────────────────────────────────────────

    DoctorQualificationResponseDto addQualification(DoctorQualificationRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    DoctorQualificationResponseDto updateQualification(UUID qualId, DoctorQualificationRequestDto dto,
            CustomUserPrincipal authUser, HttpServletRequest request);

    String removeQualification(UUID qualId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<DoctorQualificationResponseDto> getDoctorQualifications(UUID doctorId, CustomUserPrincipal authUser,
            HttpServletRequest request);

    // ─── Schedules ─────────────────────────────────────────────────────

    DoctorScheduleResponseDto addSchedule(DoctorScheduleRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    DoctorScheduleResponseDto updateSchedule(UUID scheduleId, DoctorScheduleRequestDto dto,
            CustomUserPrincipal authUser, HttpServletRequest request);

    String removeSchedule(UUID scheduleId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<DoctorScheduleResponseDto> getDcSchedules(UUID dcId, CustomUserPrincipal authUser,
            HttpServletRequest request);

    // ─── Leave ─────────────────────────────────────────────────────────

    DoctorLeaveResponseDto addLeave(DoctorLeaveRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    DoctorLeaveResponseDto updateLeave(UUID leaveId, DoctorLeaveRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String removeLeave(UUID leaveId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<DoctorLeaveResponseDto> getDcLeave(UUID dcId, CustomUserPrincipal authUser, HttpServletRequest request);

    // ─── Appointment Slots ─────────────────────────────────────────────

    AppointmentSlotResponseDto addSlot(AppointmentSlotRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    AppointmentSlotResponseDto updateSlot(UUID slotId, AppointmentSlotRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String removeSlot(UUID slotId, CustomUserPrincipal authUser, HttpServletRequest request);

    List<AppointmentSlotResponseDto> getAvailableSlots(UUID dcId, java.time.LocalDate date,
            CustomUserPrincipal authUser, HttpServletRequest request);
}
