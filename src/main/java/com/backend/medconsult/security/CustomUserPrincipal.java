package com.backend.medconsult.security;

import java.util.Collection;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backend.medconsult.entity.usersAndPatients.User;

import java.util.Collections;

public class CustomUserPrincipal implements UserDetails {

    private User user;

    public CustomUserPrincipal(User user) {
        this.user = user;

    }

    public User getUser() {
        return user;
    }

    public UUID getUserId() {
        return user.getUserId();
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole().toString();

        if (role.equals("PATIENT")) {
            role = "ROLE_PATIENT";
        } else if (role.equals("DOCTOR")) {
            role = "ROLE_DOCTOR";
        } else {
            role = "ROLE_ADMIN";
        }
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

}