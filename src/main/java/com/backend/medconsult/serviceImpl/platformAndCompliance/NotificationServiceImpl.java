package com.backend.medconsult.serviceImpl.platformAndCompliance;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.medconsult.entity.platformAndCompliance.Notification;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.NotificationChannel;
import com.backend.medconsult.enums.platformAndCompliance.NotificationType;
import com.backend.medconsult.repository.platformAndCompliance.NotificationRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.service.platformAndCompliance.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void notify(UUID userId, NotificationType type, String title, String body, String refType, String refId) {
        
        User user = userRepository.findById(userId).get();
        Notification n = new Notification();
        n.setUser(user);
        n.setType(type);
        n.setTitle(title);
        n.setBody(body);
        n.setRefType(refType);
        n.setRefId(refId);
        n.setChannel(NotificationChannel.IN_APP); // push/SMS/email dispatch adapters come in Phase 10
        n.setIsRead(false);
        n.setSentAt(LocalDateTime.now());
        notificationRepository.save(n);
    }

}
