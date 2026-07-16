package com.backend.medconsult.service;

import com.backend.medconsult.dto.auth.AuthResponseDto;
import com.backend.medconsult.dto.auth.RegisterRequestDto;
import com.backend.medconsult.dto.auth.UserLoginDto;
import com.backend.medconsult.dto.auth.UserResponseDto;

public interface UserService {

    AuthResponseDto register(RegisterRequestDto dto);

    AuthResponseDto login(UserLoginDto request);

}
