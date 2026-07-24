package com.backend.medconsult.service.clinic;

import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import java.util.List;
import java.util.UUID;

public interface ClinicAuthorizationService {

    void verifyClinicAdmin(UUID clinicId, UUID userId);

    void verifyPrimaryClinicAdmin(UUID clinicId, UUID userId);

    List<UUID> getManagedClinicIds(UUID userId);

    List<ClinicResponseDto> getManagedClinics(UUID userId);

    void verifyBranchAdmin(UUID branchId, UUID userId);

    void verifyDoctorClinicAdmin(UUID doctorClinicId, UUID userId);

    void verifyScheduleAdmin(UUID scheduleId, UUID userId);

    void verifyLeaveAdmin(UUID leaveId, UUID userId);

    void verifySlotAdmin(UUID slotId, UUID userId);

    void verifyAppointmentAdmin(UUID appointmentId, UUID userId);

    void verifyDoctorAdmin(UUID doctorId, UUID userId);
}
