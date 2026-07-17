package com.backend.medconsult.service;

import java.util.List;

import com.backend.medconsult.dto.auth.AuthResponseDto;
import com.backend.medconsult.dto.auth.RegisterRequestDto;
import com.backend.medconsult.dto.auth.UserLoginDto;
import com.backend.medconsult.dto.auth.UserResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

public interface UserService {

    AuthResponseDto register(RegisterRequestDto dto);

    AuthResponseDto login(UserLoginDto request);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getMe(CustomUserPrincipal userPrincipal);
}
