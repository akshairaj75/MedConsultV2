package com.backend.medconsult.serviceImpl.clinicalRecord;

import com.backend.medconsult.dto.clinicRecord.VitalRequestDto;
import com.backend.medconsult.dto.clinicRecord.VitalResponseDto;
import com.backend.medconsult.entity.clinicalRecords.Vital;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.clinicalRecords.VitalSource;
import com.backend.medconsult.repository.clinicalRecords.VitalRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.clinicRecord.VitalService;
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
public class VitalServiceImpl implements VitalService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("recordedAt");

    @Autowired
    private VitalRepository vitalRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<VitalResponseDto> searchVitals(UUID patientId, VitalSource source, int page, int size, String sortBy, String sortDir) {
        String sortField = ALLOWED_SORT_FIELDS.contains(sortBy) ? sortBy : "recordedAt";
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return vitalRepository.searchVitals(patientId, source, pageable)
                .map(VitalResponseDto::fromEntity);
    }

    @Override
    public VitalResponseDto getVitalById(UUID id) {
        Vital vital = findVitalOrThrow(id);
        return VitalResponseDto.fromEntity(vital);
    }

    @Transactional
    @Override
    public VitalResponseDto createVital(VitalRequestDto dto, CustomUserPrincipal principal) {
        Vital vital = new Vital();

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
        vital.setPatient(patient);

        if (dto.getRecordedById() != null) {
            User recordedBy = userRepository.findById(dto.getRecordedById())
                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getRecordedById()));
            vital.setRecordedBy(recordedBy);
        }

        applyDtoToVital(vital, dto);

        return VitalResponseDto.fromEntity(vitalRepository.save(vital));
    }

    @Transactional
    @Override
    public VitalResponseDto updateVital(UUID id, VitalRequestDto dto, CustomUserPrincipal principal) {
        Vital vital = findVitalOrThrow(id);

        if (dto.getPatientId() != null && !dto.getPatientId().equals(vital.getPatient().getPatientId())) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
            vital.setPatient(patient);
        }

        if (dto.getRecordedById() != null) {
            if (vital.getRecordedBy() == null || !dto.getRecordedById().equals(vital.getRecordedBy().getUserId())) {
                User recordedBy = userRepository.findById(dto.getRecordedById())
                        .orElseThrow(() -> new RuntimeException("User not found: " + dto.getRecordedById()));
                vital.setRecordedBy(recordedBy);
            }
        }

        applyDtoToVital(vital, dto);

        return VitalResponseDto.fromEntity(vitalRepository.save(vital));
    }

    @Transactional
    @Override
    public void deleteVital(UUID id, CustomUserPrincipal principal) {
        Vital vital = findVitalOrThrow(id);
        vitalRepository.delete(vital);
    }

    private Vital findVitalOrThrow(UUID id) {
        return vitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vital not found: " + id));
    }

    private void applyDtoToVital(Vital vital, VitalRequestDto dto) {
        if (dto.getRecordedAt() != null) vital.setRecordedAt(dto.getRecordedAt());
        if (dto.getSource() != null) vital.setSource(dto.getSource());
        if (dto.getBloodPressureSystolic() != null) vital.setBloodPressureSystolic(dto.getBloodPressureSystolic());
        if (dto.getBloodPressureDiastolic() != null) vital.setBloodPressureDiastolic(dto.getBloodPressureDiastolic());
        if (dto.getHeartRateBpm() != null) vital.setHeartRateBpm(dto.getHeartRateBpm());
        if (dto.getBloodGlucoseMmol() != null) vital.setBloodGlucoseMmol(dto.getBloodGlucoseMmol());
        if (dto.getHba1cPercent() != null) vital.setHba1cPercent(dto.getHba1cPercent());
        if (dto.getWeightKg() != null) vital.setWeightKg(dto.getWeightKg());
        if (dto.getTemperatureC() != null) vital.setTemperatureC(dto.getTemperatureC());
        if (dto.getOxygenSaturation() != null) vital.setOxygenSaturation(dto.getOxygenSaturation());
        if (dto.getNotes() != null) vital.setNotes(dto.getNotes());
    }
}
