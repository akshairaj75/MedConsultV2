package com.backend.medconsult.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.backend.medconsult.security.CustomUserDetailsService;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.security.JwtService;

@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = extractToken(accessor);

            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException("Missing JWT token for WebSocket connection");
            }

            String userEmail = jwtUtil.extractEmail(token);
            if (userEmail == null || userEmail.isBlank()) {
                throw new IllegalArgumentException("Invalid JWT token: subject email missing");
            }

            CustomUserPrincipal principal = (CustomUserPrincipal) userDetailsService.loadUserByUsername(userEmail);

            if (!jwtUtil.validateToken(token, principal)) {
                throw new IllegalArgumentException("Invalid or expired JWT token");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    principal.getAuthorities()
            );

            accessor.setUser(authentication);
        }

        return message;
    }

    private String extractToken(StompHeaderAccessor accessor) {
        // 1. Try "Authorization" STOMP native header
        String authHeader = accessor.getFirstNativeHeader("Authorization");
        if (authHeader != null && !authHeader.isBlank()) {
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7).trim();
            }
            return authHeader.trim();
        }

        // 2. Try "token" or "access_token" or "passcode" native headers
        String tokenHeader = accessor.getFirstNativeHeader("token");
        if (tokenHeader != null && !tokenHeader.isBlank()) {
            return tokenHeader.trim();
        }

        String accessTokenHeader = accessor.getFirstNativeHeader("access_token");
        if (accessTokenHeader != null && !accessTokenHeader.isBlank()) {
            return accessTokenHeader.trim();
        }

        String passcode = accessor.getFirstNativeHeader("passcode");
        if (passcode != null && !passcode.isBlank()) {
            return passcode.trim();
        }

        return null;
    }
}