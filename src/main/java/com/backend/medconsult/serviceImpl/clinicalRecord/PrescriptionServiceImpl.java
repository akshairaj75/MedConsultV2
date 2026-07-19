package com.backend.medconsult.serviceImpl.clinicalRecord;

import com.backend.medconsult.dto.clinicRecord.PrescriptionItemRequestDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionItemResponseDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionRequestDto;
import com.backend.medconsult.dto.clinicRecord.PrescriptionResponseDto;
import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.entity.clinicalRecords.Prescription;
import com.backend.medconsult.entity.clinicalRecords.PrescriptionItem;
import com.backend.medconsult.entity.consultation.Consultation;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.enums.clinicalRecords.PrescriptionStatus;
import com.backend.medconsult.repository.appointments.AppointmentRepository;
import com.backend.medconsult.repository.clinicalRecords.PrescriptionItemRepository;
import com.backend.medconsult.repository.clinicalRecords.PrescriptionRepository;
import com.backend.medconsult.repository.consultation.ConsultationRepository;
import com.backend.medconsult.repository.doctor.DoctorRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.clinicRecord.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("issuedDate", "createdAt", "status", "validUntil");

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionItemRepository prescriptionItemRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Page<PrescriptionResponseDto> searchPrescriptions(UUID patientId, UUID doctorId, PrescriptionStatus status, int page, int size, String sortBy, String sortDir) {
        String sortField = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "issuedDate";
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return prescriptionRepository.searchPrescriptions(patientId, doctorId, status, pageable)
                .map(PrescriptionResponseDto::fromEntity);
    }

    @Override
    public PrescriptionResponseDto getPrescriptionById(UUID id) {
        Prescription prescription = findPrescriptionOrThrow(id);
        return PrescriptionResponseDto.fromEntity(prescription);
    }

    @Transactional
    @Override
    public PrescriptionResponseDto createPrescription(PrescriptionRequestDto dto, CustomUserPrincipal principal) {
        Prescription prescription = new Prescription();

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
        prescription.setPatient(patient);

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getDoctorId()));
        prescription.setDoctor(doctor);

        if (dto.getConsultationId() != null) {
            Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                    .orElseThrow(() -> new RuntimeException("Consultation not found: " + dto.getConsultationId()));
            prescription.setConsultation(consultation);
        }

        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found: " + dto.getAppointmentId()));
            prescription.setAppointment(appointment);
        }

        applyDtoToPrescription(prescription, dto);

        return PrescriptionResponseDto.fromEntity(prescriptionRepository.save(prescription));
    }

    @Transactional
    @Override
    public PrescriptionResponseDto updatePrescription(UUID id, PrescriptionRequestDto dto, CustomUserPrincipal principal) {
        Prescription prescription = findPrescriptionOrThrow(id);

        if (dto.getPatientId() != null && !dto.getPatientId().equals(prescription.getPatient().getPatientId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
            prescription.setPatient(patient);
        }

        if (dto.getDoctorId() != null && !dto.getDoctorId().equals(prescription.getDoctor().getDoctorId())) {
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getDoctorId()));
            prescription.setDoctor(doctor);
        }

        if (dto.getConsultationId() != null) {
            if (prescription.getConsultation() == null || !dto.getConsultationId().equals(prescription.getConsultation().getConsultationId())) {
                Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                        .orElseThrow(() -> new RuntimeException("Consultation not found: " + dto.getConsultationId()));
                prescription.setConsultation(consultation);
            }
        }

        if (dto.getAppointmentId() != null) {
            if (prescription.getAppointment() == null || !dto.getAppointmentId().equals(prescription.getAppointment().getAppointmentId())) {
                Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                        .orElseThrow(() -> new RuntimeException("Appointment not found: " + dto.getAppointmentId()));
                prescription.setAppointment(appointment);
            }
        }

        applyDtoToPrescription(prescription, dto);

        return PrescriptionResponseDto.fromEntity(prescriptionRepository.save(prescription));
    }

    @Transactional
    @Override
    public void deletePrescription(UUID id, CustomUserPrincipal principal) {
        Prescription prescription = findPrescriptionOrThrow(id);
        prescriptionRepository.delete(prescription);
    }

    @Override
    public List<PrescriptionItemResponseDto> getPrescriptionItems(UUID prescriptionId) {
        return prescriptionItemRepository.findByPrescription_PrescriptionIdOrderBySortOrderAsc(prescriptionId)
                .stream()
                .map(PrescriptionItemResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PrescriptionItemResponseDto addPrescriptionItem(UUID prescriptionId, PrescriptionItemRequestDto dto, CustomUserPrincipal principal) {
        Prescription prescription = findPrescriptionOrThrow(prescriptionId);

        PrescriptionItem item = new PrescriptionItem();
        item.setPrescription(prescription);
        applyDtoToPrescriptionItem(item, dto);

        return PrescriptionItemResponseDto.fromEntity(prescriptionItemRepository.save(item));
    }

    @Transactional
    @Override
    public PrescriptionItemResponseDto updatePrescriptionItem(UUID itemId, PrescriptionItemRequestDto dto, CustomUserPrincipal principal) {
        PrescriptionItem item = prescriptionItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Prescription Item not found: " + itemId));

        applyDtoToPrescriptionItem(item, dto);

        return PrescriptionItemResponseDto.fromEntity(prescriptionItemRepository.save(item));
    }

    @Transactional
    @Override
    public void deletePrescriptionItem(UUID itemId, CustomUserPrincipal principal) {
        PrescriptionItem item = prescriptionItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Prescription Item not found: " + itemId));
        prescriptionItemRepository.delete(item);
    }

    private Prescription findPrescriptionOrThrow(UUID id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found: " + id));
    }

    private void applyDtoToPrescription(Prescription prescription, PrescriptionRequestDto dto) {
        if (dto.getIssuedDate() != null) prescription.setIssuedDate(dto.getIssuedDate());
        if (dto.getValidUntil() != null) prescription.setValidUntil(dto.getValidUntil());
        if (dto.getStatus() != null) prescription.setStatus(dto.getStatus());
        if (dto.getDiagnosisNotes() != null) prescription.setDiagnosisNotes(dto.getDiagnosisNotes());
        if (dto.getPharmacistNotes() != null) prescription.setPharmacistNotes(dto.getPharmacistNotes());
        if (dto.getNaphiesReference() != null) prescription.setNaphiesReference(dto.getNaphiesReference());
    }

    private void applyDtoToPrescriptionItem(PrescriptionItem item, PrescriptionItemRequestDto dto) {
        if (dto.getDrugName() != null) item.setDrugName(dto.getDrugName());
        if (dto.getDosage() != null) item.setDosage(dto.getDosage());
        if (dto.getRoute() != null) item.setRoute(dto.getRoute());
        if (dto.getFrequency() != null) item.setFrequency(dto.getFrequency());
        if (dto.getDurationDays() != null) item.setDurationDays(dto.getDurationDays());
        if (dto.getQuantity() != null) item.setQuantity(dto.getQuantity());
        if (dto.getRefillsAllowed() != null) item.setRefillsAllowed(dto.getRefillsAllowed());
        if (dto.getRefillsUsed() != null) item.setRefillsUsed(dto.getRefillsUsed());
        if (dto.getSpecialInstructions() != null) item.setSpecialInstructions(dto.getSpecialInstructions());
        if (dto.getSortOrder() != null) item.setSortOrder(dto.getSortOrder());
    }
}
