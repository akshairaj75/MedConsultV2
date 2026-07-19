package com.backend.medconsult.controller.references;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.backend.medconsult.dto.references.CityRequestDto;
import com.backend.medconsult.dto.references.CityResponseDto;
import com.backend.medconsult.dto.references.LocalityRequestDto;
import com.backend.medconsult.dto.references.LocalityResponseDto;
import com.backend.medconsult.security.CustomUserPrincipal;
import com.backend.medconsult.service.references.CityService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/medconsult/cities")
public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping("/all")
    public ResponseEntity<List<CityResponseDto>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<CityResponseDto> getCity(@PathVariable UUID cityId) {
        CityResponseDto city = cityService.getCity(cityId);
        if (city == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(city);
    }

    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<CityResponseDto> addCity(
            @RequestBody CityRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        CityResponseDto res = cityService.addCity(dto, authUser, request);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{cityId}/edit")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<CityResponseDto> updateCity(
            @PathVariable UUID cityId,
            @RequestBody CityRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        CityResponseDto res = cityService.updateCity(cityId, dto, authUser, request);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{cityId}/delete")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<String> deleteCity(
            @PathVariable UUID cityId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(cityService.deleteCity(cityId, authUser, request));
    }

    @GetMapping("/{cityId}/localities")
    public ResponseEntity<List<LocalityResponseDto>> getLocalities(@PathVariable UUID cityId) {
        List<LocalityResponseDto> localities = cityService.getLocalities(cityId);
        if (localities == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(localities);
    }

    @GetMapping("/locality/{localityId}")
    public ResponseEntity<LocalityResponseDto> getLocality(@PathVariable UUID localityId) {
        LocalityResponseDto locality = cityService.getLocality(localityId);
        if (locality == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(locality);
    }

    @PostMapping("/locality/add")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<LocalityResponseDto> addLocality(
            @RequestBody LocalityRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        LocalityResponseDto res = cityService.addLocality(dto, authUser, request);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/locality/{localityId}/edit")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<LocalityResponseDto> updateLocality(
            @PathVariable UUID localityId,
            @RequestBody LocalityRequestDto dto,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        LocalityResponseDto res = cityService.updateLocality(localityId, dto, authUser, request);
        if (res == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/locality/{localityId}/delete")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<String> deleteLocality(
            @PathVariable UUID localityId,
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            HttpServletRequest request) {
        return ResponseEntity.ok(cityService.deleteLocality(localityId, authUser, request));
    }

}
