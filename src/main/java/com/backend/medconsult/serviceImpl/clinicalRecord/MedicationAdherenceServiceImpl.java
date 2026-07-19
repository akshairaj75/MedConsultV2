package com.backend.medconsult.serviceImpl.clinicalRecord;

import com.backend.medconsult.dto.clinicRecord.MedicationAdherenceRequestDto;
import com.backend.medconsult.dto.clinicRecord.MedicationAdherenceResponseDto;
import com.backend.medconsult.entity.clinicalRecords.MedicationAdherence;
import com.backend.medconsult.entity.clinicalRecords.PrescriptionItem;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.repository.clinicalRecords.MedicationAdherenceRepository;
import com.backend.medconsult.repository.clinicalRecords.PrescriptionItemRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.clinicRecord.MedicationAdherenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class MedicationAdherenceServiceImpl implements MedicationAdherenceService {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("logDate", "takenAt", "createdAt");

    @Autowired
    private MedicationAdherenceRepository medicationAdherenceRepository;

    @Autowired
    private PrescriptionItemRepository prescriptionItemRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Page<MedicationAdherenceResponseDto> searchAdherence(UUID patientId, UUID rxItemId, int page, int size,
            String sortBy, String sortDir) {
        String sortField = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "logDate";
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return medicationAdherenceRepository.searchAdherence(patientId, rxItemId, pageable)
                .map(MedicationAdherenceResponseDto::fromEntity);
    }

    @Override
    public MedicationAdherenceResponseDto getAdherenceById(UUID id) {
        MedicationAdherence adherence = findAdherenceOrThrow(id);
        return MedicationAdherenceResponseDto.fromEntity(adherence);
    }

    @Transactional
    @Override
    public MedicationAdherenceResponseDto createAdherence(MedicationAdherenceRequestDto dto,
            CustomUserPrincipal principal) {
        MedicationAdherence adherence = new MedicationAdherence();

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
        adherence.setPatient(patient);

        PrescriptionItem rxItem = prescriptionItemRepository.findById(dto.getRxItemId())
                .orElseThrow(() -> new RuntimeException("Prescription Item not found: " + dto.getRxItemId()));
        adherence.setRxItem(rxItem);

        applyDtoToAdherence(adherence, dto);

        return MedicationAdherenceResponseDto.fromEntity(medicationAdherenceRepository.save(adherence));
    }

    @Transactional
    @Override
    public MedicationAdherenceResponseDto updateAdherence(UUID id, MedicationAdherenceRequestDto dto,
            CustomUserPrincipal principal) {
        MedicationAdherence adherence = findAdherenceOrThrow(id);

        if (dto.getPatientId() != null && !dto.getPatientId().equals(adherence.getPatient().getPatientId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
            adherence.setPatient(patient);
        }

        if (dto.getRxItemId() != null && !dto.getRxItemId().equals(adherence.getRxItem().getItemId())) {
            PrescriptionItem rxItem = prescriptionItemRepository.findById(dto.getRxItemId())
                    .orElseThrow(() -> new RuntimeException("Prescription Item not found: " + dto.getRxItemId()));
            adherence.setRxItem(rxItem);
        }

        applyDtoToAdherence(adherence, dto);

        return MedicationAdherenceResponseDto.fromEntity(medicationAdherenceRepository.save(adherence));
    }

    @Transactional
    @Override
    public void deleteAdherence(UUID id, CustomUserPrincipal principal) {
        MedicationAdherence adherence = findAdherenceOrThrow(id);
        medicationAdherenceRepository.delete(adherence);
    }

    private MedicationAdherence findAdherenceOrThrow(UUID id) {
        return medicationAdherenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medication Adherence not found: " + id));
    }

    private void applyDtoToAdherence(MedicationAdherence adherence, MedicationAdherenceRequestDto dto) {
        if (dto.getLogDate() != null)
            adherence.setLogDate(dto.getLogDate());
        if (dto.getTaken() != null)
            adherence.setTaken(dto.getTaken());
        if (dto.getTakenAt() != null)
            adherence.setTakenAt(dto.getTakenAt());
        if (dto.getSkippedReason() != null)
            adherence.setSkippedReason(dto.getSkippedReason());
    }
}
