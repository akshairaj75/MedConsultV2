package com.backend.medconsult.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.medconsult.dto.auth.AuthResponseDto;
import com.backend.medconsult.dto.auth.RegisterRequestDto;
import com.backend.medconsult.dto.auth.UserLoginDto;
import com.backend.medconsult.entity.usersAndPatients.User;
import com.backend.medconsult.enums.usersAndPatients.AuthProvider;
import com.backend.medconsult.repository.usersAndPatients.UserRepository;
import com.backend.medconsult.security.JwtService;
import com.backend.medconsult.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

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

        return AuthResponseDto.fromEntity(user, token);

    }

}
