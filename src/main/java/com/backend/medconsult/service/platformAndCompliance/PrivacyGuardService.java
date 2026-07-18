package com.backend.medconsult.service.platformAndCompliance;

import java.util.UUID;

import com.backend.medconsult.enums.platformAndCompliance.DataScope;

public interface PrivacyGuardService {

    void checkDoctorAccess(UUID doctorId, UUID patientId, DataScope scope);

}
