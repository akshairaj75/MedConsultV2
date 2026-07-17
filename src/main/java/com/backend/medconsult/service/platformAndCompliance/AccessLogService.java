package com.backend.medconsult.service.platformAndCompliance;

import java.util.UUID;

import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;

public interface AccessLogService {

    void log(User user, Patient patient, AuditAction action,
              ResourceType resourceType, UUID resourceId, AuditOutcome outcome);

}
