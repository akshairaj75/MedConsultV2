package com.backend.medconsult.controller.doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.backend.medconsult.service.Doctor.DoctorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * REST controller for the Doctor management module.
 */
@RestController
@RequestMapping("/api/medconsult/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // ══════════════════════════════════════════════════════════════════
    // ─── Core Doctor CRUD ─────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    // @GetMapping("/search")
    // public ResponseEntity<Page<DoctorResponseDto>> searchDoctors(
    //         @RequestParam(name = "name", required = false) String name,
    //         @RequestParam(name = "specialtyId", required = false) UUID specialtyId,
    //         @RequestParam(name = "isActive", required = false) Boolean isActive,
    //         @RequestParam(name = "page", defaultValue = "0") int page,
    //         @RequestParam(name = "size", defaultValue = "10") int size,
    //         @AuthenticationPrincipal CustomUserPrincipal authUser,
    //         HttpServletRequest request) {
    //     return ResponseEntity.ok(doctorService.searchDoctors(name, specialtyId, isActive, page, size, authUser, request));
    // }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<DoctorDetailResponse> getDoctorProfile(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getDoctorProfile(id, authUser, request));
    }

    @PostMapping("/add")
    // @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN')")
    public ResponseEntity<DoctorResponseDto> addDoctor(
            @Valid @RequestBody DoctorRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addDoctor(dto, authUser, request));
    }

    @PatchMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorResponseDto> updateDoctor(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DoctorRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, dto, authUser, request));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<String> deleteDoctor(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.deleteDoctor(id, authUser, request));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Doctor-Clinic (dc) ───────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @GetMapping("/{doctorId}/clinics")
    public ResponseEntity<List<DoctorClinicResponseDto>> getDoctorClinics(
            @PathVariable("doctorId") UUID doctorId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getDoctorClinics(doctorId, authUser, request));
    }

    @PostMapping("/clinics/add")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN')")
    public ResponseEntity<DoctorClinicResponseDto> addDoctorClinic(
            @Valid @RequestBody DoctorClinicRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addDoctorClinic(dto, authUser, request));
    }

    @PatchMapping("/clinics/{dcId}/update")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN')")
    public ResponseEntity<DoctorClinicResponseDto> updateDoctorClinic(
            @PathVariable("dcId") UUID dcId,
            @Valid @RequestBody DoctorClinicRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.updateDoctorClinic(dcId, dto, authUser, request));
    }

    @DeleteMapping("/clinics/{dcId}/remove")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN')")
    public ResponseEntity<String> removeDoctorClinic(
            @PathVariable("dcId") UUID dcId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.removeDoctorClinic(dcId, authUser, request));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Specialties ──────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @GetMapping("/{doctorId}/specialties")
    public ResponseEntity<List<DoctorSpecialtyResponseDto>> getDoctorSpecialties(
            @PathVariable("doctorId") UUID doctorId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getDoctorSpecialties(doctorId, authUser, request));
    }

    @PostMapping("/specialties/add")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorSpecialtyResponseDto> addSpecialty(
            @Valid @RequestBody DoctorSpecialtyRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addSpecialty(dto, authUser, request));
    }

    @PatchMapping("/specialties/{id}/update")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorSpecialtyResponseDto> updateSpecialty(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DoctorSpecialtyRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.updateSpecialty(id, dto, authUser, request));
    }

    @DeleteMapping("/specialties/{id}/remove")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<String> removeSpecialty(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.removeSpecialty(id, authUser, request));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Languages ────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @GetMapping("/{doctorId}/languages")
    public ResponseEntity<List<DoctorLanguageResponseDto>> getDoctorLanguages(
            @PathVariable("doctorId") UUID doctorId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getDoctorLanguages(doctorId, authUser, request));
    }

    @PostMapping("/languages/add")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorLanguageResponseDto> addLanguage(
            @Valid @RequestBody DoctorLanguageRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addLanguage(dto, authUser, request));
    }

    @DeleteMapping("/languages/{id}/remove")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<String> removeLanguage(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.removeLanguage(id, authUser, request));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Qualifications ───────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @GetMapping("/{doctorId}/qualifications")
    public ResponseEntity<List<DoctorQualificationResponseDto>> getDoctorQualifications(
            @PathVariable("doctorId") UUID doctorId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getDoctorQualifications(doctorId, authUser, request));
    }

    @PostMapping("/qualifications/add")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorQualificationResponseDto> addQualification(
            @Valid @RequestBody DoctorQualificationRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addQualification(dto, authUser, request));
    }

    @PatchMapping("/qualifications/{id}/update")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorQualificationResponseDto> updateQualification(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DoctorQualificationRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.updateQualification(id, dto, authUser, request));
    }

    @DeleteMapping("/qualifications/{id}/remove")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<String> removeQualification(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.removeQualification(id, authUser, request));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Schedules ────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @GetMapping("/clinics/{dcId}/schedules")
    public ResponseEntity<List<DoctorScheduleResponseDto>> getDcSchedules(
            @PathVariable("dcId") UUID dcId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getDcSchedules(dcId, authUser, request));
    }

    @PostMapping("/schedules/add")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorScheduleResponseDto> addSchedule(
            @Valid @RequestBody DoctorScheduleRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addSchedule(dto, authUser, request));
    }

    @PatchMapping("/schedules/{id}/update")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorScheduleResponseDto> updateSchedule(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DoctorScheduleRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.updateSchedule(id, dto, authUser, request));
    }

    @DeleteMapping("/schedules/{id}/remove")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<String> removeSchedule(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.removeSchedule(id, authUser, request));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Leave ────────────────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @GetMapping("/clinics/{dcId}/leave")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<List<DoctorLeaveResponseDto>> getDcLeave(
            @PathVariable("dcId") UUID dcId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getDcLeave(dcId, authUser, request));
    }

    @PostMapping("/leave/add")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorLeaveResponseDto> addLeave(
            @Valid @RequestBody DoctorLeaveRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addLeave(dto, authUser, request));
    }

    @PatchMapping("/leave/{id}/update")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<DoctorLeaveResponseDto> updateLeave(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DoctorLeaveRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.updateLeave(id, dto, authUser, request));
    }

    @DeleteMapping("/leave/{id}/remove")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<String> removeLeave(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.removeLeave(id, authUser, request));
    }

    // ══════════════════════════════════════════════════════════════════
    // ─── Appointment Slots ────────────────────────────────────────────
    // ══════════════════════════════════════════════════════════════════

    @GetMapping("/clinics/{dcId}/slots")
    public ResponseEntity<List<AppointmentSlotResponseDto>> getAvailableSlots(
            @PathVariable("dcId") UUID dcId,
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.getAvailableSlots(dcId, date, authUser, request));
    }

    @PostMapping("/slots/add")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<AppointmentSlotResponseDto> addSlot(
            @Valid @RequestBody AppointmentSlotRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addSlot(dto, authUser, request));
    }

    @PatchMapping("/slots/{id}/update")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<AppointmentSlotResponseDto> updateSlot(
            @PathVariable("id") UUID id,
            @Valid @RequestBody AppointmentSlotRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.updateSlot(id, dto, authUser, request));
    }

    @DeleteMapping("/slots/{id}/remove")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'CLINIC_ADMIN', 'DOCTOR')")
    public ResponseEntity<String> removeSlot(
            @PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(doctorService.removeSlot(id, authUser, request));
    }
}
