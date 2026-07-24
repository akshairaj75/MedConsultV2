package com.backend.medconsult.service.clinic;

import com.backend.medconsult.dto.clinic.ClinicAdminRequestDto;
import com.backend.medconsult.dto.clinic.ClinicAdminResponseDto;

import java.util.List;
import java.util.UUID;

public interface ClinicAdminService {

    ClinicAdminResponseDto assignAdmin(ClinicAdminRequestDto dto);

    void removeAdmin(UUID id);

    List<ClinicAdminResponseDto> getClinicAdmins(UUID clinicId);

    List<ClinicAdminResponseDto> getManagedClinics(UUID userId);

    void changePrimaryAdmin(UUID clinicId, UUID userId);
}
