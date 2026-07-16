package com.backend.medconsult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.auth.AuthResponseDto;
import com.backend.medconsult.dto.auth.RegisterRequestDto;
import com.backend.medconsult.dto.auth.UserLoginDto;
import com.backend.medconsult.service.UserService;

@RestController
@RequestMapping("/api/medconsult/auth")
public class AuthController {

    @Autowired
    UserService userService;

     @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register( @RequestBody RegisterRequestDto dto) {
        AuthResponseDto registered = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

        @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

}
