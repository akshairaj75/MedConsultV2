package com.backend.medconsult.serviceImpl.platformAndCompliance;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.backend.medconsult.entity.platformAndCompliance.Notification;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.NotificationChannel;
import com.backend.medconsult.enums.platformAndCompliance.NotificationType;
import com.backend.medconsult.repository.platformAndCompliance.NotificationRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.service.platformAndCompliance.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void notify(UUID userId, NotificationType type, String title, String body, String refType, String refId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        Notification n = new Notification();
        n.setUser(user);
        n.setType(type);
        n.setTitle(title);
        n.setBody(body);
        n.setRefType(refType);
        n.setRefId(refId);
        n.setChannel(NotificationChannel.IN_APP);
        n.setIsRead(false);
        n.setSentAt(LocalDateTime.now());
        Notification saved = notificationRepository.save(n);

        // Real-time STOMP notification dispatch
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("notificationId", saved.getNotificationId());
            payload.put("type", saved.getType());
            payload.put("title", saved.getTitle());
            payload.put("body", saved.getBody());
            payload.put("refType", saved.getRefType());
            payload.put("refId", saved.getRefId());
            payload.put("isRead", saved.getIsRead());
            payload.put("sentAt", saved.getSentAt());

            if (user.getEmail() != null) {
                messagingTemplate.convertAndSendToUser(user.getEmail(), "/queue/notifications", (Object) payload);
            }
            if (user.getUserId() != null) {
                messagingTemplate.convertAndSendToUser(user.getUserId().toString(), "/queue/notifications", (Object) payload);
            }
        } catch (Exception e) {
            // Do not fail DB notification if WebSocket send encounters issue
        }
    }
}

