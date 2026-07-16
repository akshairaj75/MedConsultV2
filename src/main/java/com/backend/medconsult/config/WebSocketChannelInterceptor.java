package com.backend.medconsult.config;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.backend.medconsult.security.CustomUserDetailsService;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.security.JwtService;


@Component
// @RequiredArgsConstructor
public class WebSocketChannelInterceptor
                implements ChannelInterceptor {

        @Autowired
        private JwtService jwtUtil;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Override
        public Message<?> preSend(
                        Message<?> message,
                        MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
                                message,
                                StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        String authHeader = accessor.getFirstNativeHeader("Authorization");
                        if (authHeader == null ||
                                        !authHeader.startsWith("Bearer ")) {

                                throw new IllegalArgumentException(
                                                "Missing JWT token");
                        }

                        String token = authHeader.substring(7);
                        String userEmail = jwtUtil.extractEmail(token);
                        CustomUserPrincipal principal = (CustomUserPrincipal) userDetailsService
                                        .loadUserByUsername(userEmail);

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        principal,
                                        null,
                                        principal.getAuthorities());

                        accessor.setUser(authentication);
                }

                return message;
        }
}