package com.backend.medconsult.service.platformAndCompliance;

import java.util.UUID;

import com.backend.medconsult.enums.platformAndCompliance.NotificationType;

public interface NotificationService {

    void notify(UUID userId, NotificationType type, String title, String body,
            String refType, String refId);

}
