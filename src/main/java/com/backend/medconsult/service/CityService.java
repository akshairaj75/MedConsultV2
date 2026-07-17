package com.backend.medconsult.service;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;

import com.backend.medconsult.dto.references.CityRequestDto;
import com.backend.medconsult.dto.references.CityResponseDto;
import com.backend.medconsult.dto.references.LocalityRequestDto;
import com.backend.medconsult.dto.references.LocalityResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface CityService {

    List<CityResponseDto> getAllCities();

    CityResponseDto addCity(CityRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    CityResponseDto updateCity(UUID cityId, CityRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String deleteCity(UUID cityId, CustomUserPrincipal authUser, HttpServletRequest request);

    CityResponseDto getCity(UUID cityId);

    List<LocalityResponseDto> getLocalities(UUID cityId);

    LocalityResponseDto getLocality(UUID localityId);

    LocalityResponseDto addLocality(LocalityRequestDto dto, CustomUserPrincipal authUser, HttpServletRequest request);

    LocalityResponseDto updateLocality(UUID localityId, LocalityRequestDto dto, CustomUserPrincipal authUser,
            HttpServletRequest request);

    String deleteLocality(UUID localityId, CustomUserPrincipal authUser, HttpServletRequest request);

}
