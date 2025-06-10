package com.sxu.job.controller;

import com.sxu.job.dto.ApplicationDto;
import com.sxu.job.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/me/applications")
    public ResponseEntity<List<ApplicationDto>> getMyApplications(@AuthenticationPrincipal UserDetails userDetails) {
        // For now, we'll just return mock data without checking the user
        // In a real app, you would get the user ID from the authentication
        List<ApplicationDto> applications = applicationService.getStudentApplications(1L); // Mock user ID
        return ResponseEntity.ok(applications);
    }
}
