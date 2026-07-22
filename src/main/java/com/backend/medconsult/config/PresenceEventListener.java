package com.backend.medconsult.config;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class PresenceEventListener {

    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Principal user = accessor.getUser();
        if (user != null && user.getName() != null) {
            onlineUsers.add(user.getName());
        }
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Principal user = accessor.getUser();
        if (user != null && user.getName() != null) {
            onlineUsers.remove(user.getName());
        }
    }

    public boolean isUserOnline(String usernameOrEmail) {
        return usernameOrEmail != null && onlineUsers.contains(usernameOrEmail);
    }

    public Set<String> getOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers);
    }

    public int getOnlineCount() {
        return onlineUsers.size();
    }
}

