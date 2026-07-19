package com.backend.medconsult.serviceImpl.Doctor;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.medconsult.dto.doctor.DoctorDetailResponse;
import com.backend.medconsult.dto.doctor.DoctorRequestDto;
import com.backend.medconsult.dto.doctor.DoctorResponseDto;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.Doctor.DoctorService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AccessLogService accessLogService;

    @Override
    public List<DoctorResponseDto> getAllDoctors() {
        List<Doctor> docList = doctorRepository.findAll();
        if (docList.isEmpty()) {
            throw new RuntimeException("No doctors found");
        }
        return docList.stream().map(DoctorResponseDto::fromEntity).toList();
    }

    @Transactional
    @Override
    public DoctorResponseDto addDoctor(DoctorRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request) {

        User user = authUser.getUser();
        String moh = dto.getMohRegistrationNumber();

        if (doctorRepository.existsByMohRegistrationNumber(moh)) {
            throw new RuntimeException("Doctor with this MOH registration number already exists");
        }
        if (doctorRepository.existsByUser(user)) {
            throw new RuntimeException("Doctor already exists on this user");
        }

        Doctor doc = new Doctor();
        doc.setUser(user);
        doc.setMohRegistrationNumber(dto.getMohRegistrationNumber());
        doc.setMohVerified(dto.getMohVerified());
        doc.setTitle(dto.getTitle());
        doc.setBioAr(dto.getBioAr());
        doc.setBioEn(dto.getBioEn());
        doc.setExperienceYears(dto.getExperienceYears());
        doc.setConsultationFeeSar(dto.getConsultationFeeSar());

        Doctor savedDoc = doctorRepository.save(doc);

        accessLogService.log(
                user,
                null,
                AuditAction.CREATE,
                ResourceType.DOCTOR,
                savedDoc.getDoctorId(),
                AuditOutcome.SUCCESS);

        return DoctorResponseDto.fromEntity(savedDoc);
    }

    @Transactional
    @Override
    public DoctorResponseDto updateDoctor(UUID id, DoctorRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request) {
        Doctor doc = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User user = authUser.getUser();
        String moh = dto.getMohRegistrationNumber();

        if (doctorRepository.existsByMohRegistrationNumber(moh) && !doc.getMohRegistrationNumber().equals(moh)) {
            throw new RuntimeException("Doctor with this MOH registration number already exists");
        }
        if (doctorRepository.existsByUser(user) && !doc.getUser().equals(user)) {
            throw new RuntimeException("Doctor already exists on this user");
        }

        doc.setMohRegistrationNumber(dto.getMohRegistrationNumber());
        doc.setMohVerified(dto.getMohVerified());
        doc.setTitle(dto.getTitle());
        doc.setBioAr(dto.getBioAr());
        doc.setBioEn(dto.getBioEn());
        doc.setExperienceYears(dto.getExperienceYears());
        doc.setConsultationFeeSar(dto.getConsultationFeeSar());

        Doctor updatedDoc = doctorRepository.save(doc);

        accessLogService.log(
                user,
                null,
                AuditAction.UPDATE,
                ResourceType.DOCTOR,
                updatedDoc.getDoctorId(),
                AuditOutcome.SUCCESS);

        return DoctorResponseDto.fromEntity(updatedDoc);
    }

    @Override
    public String deleteDoctor(UUID id, CustomUserPrincipal authUser, HttpServletRequest request) {
        Doctor doc = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User user = authUser.getUser();

        doctorRepository.delete(doc);

        accessLogService.log(
                user,
                null,
                AuditAction.DELETE,
                ResourceType.DOCTOR,
                doc.getDoctorId(),
                AuditOutcome.SUCCESS);

        return "Doctor deleted successfully";
    }

    @Override
    public DoctorDetailResponse getDoctorProfile(UUID id, CustomUserPrincipal authUser, HttpServletRequest request) {
        Doctor doc = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User user = authUser.getUser();

        accessLogService.log(
                user,
                null,
                AuditAction.VIEW,
                ResourceType.DOCTOR,
                doc.getDoctorId(),
                AuditOutcome.SUCCESS);

        return DoctorDetailResponse.fromEntity(doc);
    }



}
