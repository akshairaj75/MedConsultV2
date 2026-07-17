package com.backend.medconsult.serviceImpl.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.medconsult.dto.auth.AuthResponseDto;
import com.backend.medconsult.dto.auth.RegisterRequestDto;
import com.backend.medconsult.dto.auth.UserLoginDto;
import com.backend.medconsult.dto.auth.UserResponseDto;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.platformAndCompliance.AuditAction;
import com.backend.medconsult.enums.platformAndCompliance.AuditOutcome;
import com.backend.medconsult.enums.platformAndCompliance.ResourceType;
import com.backend.medconsult.enums.usersAndPatients.AuthProvider;
import com.backend.medconsult.repository.references.SubSpecialtyRepository;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.security.JwtService;
import com.backend.medconsult.service.UserService;
import com.backend.medconsult.service.platformAndCompliance.AccessLogService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubSpecialtyRepository subSpecialtyRepository;

    @Autowired
    AccessLogService accessLogService;

    @Autowired
    JwtService jwtService;

    @Transactional
    public AuthResponseDto register(RegisterRequestDto dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(
                        dto.getPassword()));

        user.setPhone(dto.getPhone());
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
        if (dto.getGender() != null) {
            user.setGender(dto.getGender());
        }
        if (dto.getPreferredLang() != null) {
            user.setPreferredLang(dto.getPreferredLang());
        }

        user.setAuthProvider(AuthProvider.LOCAL);
        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return AuthResponseDto.fromEntity(savedUser, token);
    }

    @Override
    public AuthResponseDto login(UserLoginDto dto) {
        User user = userRepository.findByEmail(
                dto.getEmail()).orElseThrow();

        if (user.getAuthProvider() != AuthProvider.LOCAL) {
            throw new RuntimeException(
                    "Use Google login");
        }

        boolean matches = passwordEncoder.matches(
                dto.getPassword(),
                user.getPasswordHash());

        if (!matches) {
            throw new RuntimeException(
                    "Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        accessLogService.log(
                user,
                null,
                AuditAction.LOGIN,
                ResourceType.AUTH,
                null,
                AuditOutcome.SUCCESS);

        return AuthResponseDto.fromEntity(user, token);

    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getMe(CustomUserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return UserResponseDto.fromEntity(user);
    }


}
