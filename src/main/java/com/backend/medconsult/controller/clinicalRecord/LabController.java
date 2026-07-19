package com.backend.medconsult.controller.clinicalRecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.medconsult.dto.clinicRecord.LabResultResponseDto;
import com.backend.medconsult.service.clinicRecord.LabResultService;

@RestController
@RequestMapping("/api/medconsult/lab-results")
public class LabController {

    @Autowired
    private LabResultService labResultService;

}
