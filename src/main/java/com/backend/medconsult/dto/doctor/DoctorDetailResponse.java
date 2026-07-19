package com.backend.medconsult.dto.doctor;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.doctor.DoctorLanguage;
import com.backend.medconsult.entity.doctor.DoctorClinic;
import com.backend.medconsult.entity.doctor.AppointmentSlot;
import com.backend.medconsult.enums.doctor.SlotStatus;

public class DoctorDetailResponse {
    DoctorResponseDto doctor;
    List<String> languages;
    List<String> specialties;
    List<DoctorClinicResponseDto> clinics;
    List<String> qualifications;
    LocalDateTime nextAvailableSlot;

    public DoctorResponseDto getDoctor() {
        return doctor;
    }
    public void setDoctor(DoctorResponseDto doctor) {
        this.doctor = doctor;
    }
    public List<String> getLanguages() {
        return languages;
    }
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
    public List<String> getSpecialties() {
        return specialties;
    }
    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }
    public List<DoctorClinicResponseDto> getClinics() {
        return clinics;
    }
    public void setClinics(List<DoctorClinicResponseDto> clinics) {
        this.clinics = clinics;
    }
    public List<String> getQualifications() {
        return qualifications;
    }
    public void setQualifications(List<String> qualifications) {
        this.qualifications = qualifications;
    }
    public LocalDateTime getNextAvailableSlot() {
        return nextAvailableSlot;
    }
    public void setNextAvailableSlot(LocalDateTime nextAvailableSlot) {
        this.nextAvailableSlot = nextAvailableSlot;
    }


    public static DoctorDetailResponse fromEntity(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        DoctorDetailResponse response = new DoctorDetailResponse();
        response.setDoctor(DoctorResponseDto.fromEntity(doctor));

        List<String> languages = new java.util.ArrayList<>();
        if (doctor.getLanguages() != null) {
            for (DoctorLanguage dl : doctor.getLanguages()) {
                if (dl.getLanguage() != null && dl.getLanguage().getNameEn() != null) {
                    languages.add(dl.getLanguage().getNameEn());
                }
            }
        }
        response.setLanguages(languages);

        List<String> specialties = new java.util.ArrayList<>();
        if (doctor.getSpecialties() != null) {
            doctor.getSpecialties().stream()
                .sorted((s1, s2) -> {
                    boolean p1 = s1.getIsPrimary() != null ? s1.getIsPrimary() : false;
                    boolean p2 = s2.getIsPrimary() != null ? s2.getIsPrimary() : false;
                    return Boolean.compare(p2, p1); // true (primary) first
                })
                .forEach(ds -> {
                    if (ds.getSpecialty() != null && ds.getSpecialty().getNameEn() != null) {
                        specialties.add(ds.getSpecialty().getNameEn());
                    }
                });
        }
        response.setSpecialties(specialties);

        List<DoctorClinicResponseDto> clinics = new java.util.ArrayList<>();
        LocalDateTime nextAvailableSlot = null;
        if (doctor.getClinics() != null) {
            for (DoctorClinic dc : doctor.getClinics()) {
                clinics.add(DoctorClinicResponseDto.fromEntity(dc));

                if (dc.getIsActive() != null && dc.getIsActive() && dc.getAppointmentSlots() != null) {
                    for (AppointmentSlot slot : dc.getAppointmentSlots()) {
                        if (slot.getStatus() == SlotStatus.AVAILABLE) {
                            LocalDateTime slotStart = LocalDateTime.of(slot.getSlotDate(), slot.getStartTime());
                            if (slotStart.isAfter(LocalDateTime.now())) {
                                if (nextAvailableSlot == null || slotStart.isBefore(nextAvailableSlot)) {
                                    nextAvailableSlot = slotStart;
                                }
                            }
                        }
                    }
                }
            }
        }
        response.setClinics(clinics);
        response.setNextAvailableSlot(nextAvailableSlot);

        List<String> qualifications = new java.util.ArrayList<>();
        if (doctor.getQualifications() != null) {
            doctor.getQualifications().stream()
                .sorted((q1, q2) -> {
                    int s1 = q1.getSortOrder() != null ? q1.getSortOrder() : 0;
                    int s2 = q2.getSortOrder() != null ? q2.getSortOrder() : 0;
                    return Integer.compare(s1, s2);
                })
                .forEach(dq -> {
                    if (dq.getDegree() != null) {
                        qualifications.add(dq.getDegree());
                    }
                });
        }
        response.setQualifications(qualifications);

        return response;
    }

    
}
