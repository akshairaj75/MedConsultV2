package com.backend.medconsult.serviceImpl.platformAndCompliance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.backend.medconsult.entity.platformAndCompliance.AccessLog;
import com.backend.medconsult.entity.usersAndPatients.Patient;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.repository.platformAndCompliance.AccessLogRepository;
import com.backend.medconsult.repository.usersAndPatients.PatientRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    AccessLogRepository accessLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Override
    @Async
    public void log(User user, Patient patient, AuditAction action, ResourceType resourceType, UUID resourceId,
            AuditOutcome outcome) {

        AccessLog entry = new AccessLog();

        entry.setUser(user);
        entry.setPatient(patient);

        entry.setAction(action);
        entry.setResourceType(resourceType);

        if (resourceId != null) {
            entry.setResourceId(resourceId.toString());
        }

        entry.setIpAddress(getIpAddress());
        entry.setUserAgent(getUserAgent());
        entry.setOutcome(outcome);
        accessLogRepository.save(entry);
    }

    private HttpServletRequest getCurrentRequest() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        return attributes.getRequest();
    }

    private String getIpAddress() {

        HttpServletRequest request = getCurrentRequest();

        if (request == null) {
            return null;
        }

        String forwardedFor = request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0];
        }

        return request.getRemoteAddr();
    }

    private String getUserAgent() {

        HttpServletRequest request = getCurrentRequest();

        if (request == null) {
            return null;
        }

        return request.getHeader("User-Agent");
    }

}
