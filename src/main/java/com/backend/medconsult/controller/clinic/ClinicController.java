package com.backend.medconsult.controller.clinic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.clinic.ClinicResponseDto;
import com.backend.medconsult.service.clinic.ClinicService;

import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import com.backend.medconsult.dto.clinic.ClinicBranchResponseDto;
import com.backend.medconsult.dto.clinic.ClinicOperatingHourResponseDto;

@RestController
@RequestMapping
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @GetMapping("/api/medconsult/clinic/all")
    public ResponseEntity<List<ClinicResponseDto>> getAllClinics() {
        return ResponseEntity.ok(clinicService.getAllClinics());
    }

    @GetMapping("/api/clinics/{id}")
    public ResponseEntity<ClinicResponseDto> getClinicById(@PathVariable UUID id) {
        return ResponseEntity.ok(clinicService.getClinicById(id));
    }

    @GetMapping("/api/clinics/{id}/branches")
    public ResponseEntity<List<ClinicBranchResponseDto>> getClinicBranches(@PathVariable UUID id) {
        return ResponseEntity.ok(clinicService.getClinicBranches(id));
    }

    @GetMapping("/api/branches/{branchId}/hours")
    public ResponseEntity<List<ClinicOperatingHourResponseDto>> getBranchHours(@PathVariable UUID branchId) {
        return ResponseEntity.ok(clinicService.getBranchHours(branchId));
    }

}
