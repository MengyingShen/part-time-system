package com.sxu.job.controller;

import com.sxu.job.dto.SavedJobDto;
import com.sxu.job.service.SavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/me/saved-jobs")
@RequiredArgsConstructor
public class SavedJobController {

    private final SavedJobService savedJobService;

    @GetMapping
    public ResponseEntity<List<SavedJobDto>> getSavedJobs() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Current authentication: {}", auth);
        log.info("Principal: {}", auth.getPrincipal());
        log.info("Authorities: {}", auth.getAuthorities());
        
        String username = getCurrentUsername();
        log.info("Getting saved jobs for username: {}", username);
        
        List<SavedJobDto> savedJobs = savedJobService.getSavedJobsByUsername(username);
        log.info("Found {} saved jobs for user: {}", savedJobs.size(), username);
        
        return ResponseEntity.ok(savedJobs);
    }

    @PostMapping("/{jobId}")
    public ResponseEntity<Void> saveJob(@PathVariable Long jobId) {
        String username = getCurrentUsername();
        savedJobService.saveJob(username, jobId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> unsaveJob(@PathVariable Long jobId) {
        String username = getCurrentUsername();
        savedJobService.unsaveJob(username, jobId);
        return ResponseEntity.noContent().build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
