package com.backend.medconsult.config;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class PresenceEventListener {
    private final Set<String> onlineUsers =
            ConcurrentHashMap.newKeySet();

    @EventListener
    public void handleConnect(
            SessionConnectedEvent event) {

        Principal user =
                SimpMessageHeaderAccessor
                        .wrap(event.getMessage())
                        .getUser();

        onlineUsers.add(user.getName());
    }

    @EventListener
    public void handleDisconnect(
            SessionDisconnectEvent event) {

        Principal user =
                SimpMessageHeaderAccessor
                        .wrap(event.getMessage())
                        .getUser();

        onlineUsers.remove(user.getName());
    }

}
