package com.sxu.job.controller;

import com.sxu.job.dto.ResumeDto;
import com.sxu.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/me/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping
    public ResponseEntity<ResumeDto> getResume() {
        log.debug("GET /api/me/resume endpoint called");
        String username = getCurrentUsername();
        log.info("Fetching resume for user: {}", username);
        
        try {
            Optional<ResumeDto> resume = resumeService.getResumeByUsername(username);
            log.debug("Resume found: {}", resume.isPresent());
            return resume
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        log.warn("No resume found for user: {}", username);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            log.error("Error fetching resume for user: " + username, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<ResumeDto> saveResume(@RequestBody ResumeDto resumeDto) {
        String username = getCurrentUsername();
        log.info("Saving resume for user: {}", username);
        
        try {
            ResumeDto savedResume = resumeService.saveOrUpdateResume(username, resumeDto);
            return ResponseEntity.ok(savedResume);
        } catch (Exception e) {
            log.error("Error saving resume for user: " + username, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/completion")
    public ResponseEntity<Integer> getResumeCompletion() {
        String username = getCurrentUsername();
        log.info("Fetching resume completion for user: {}", username);
        
        return resumeService.getResumeByUsername(username)
                .map(resume -> ResponseEntity.ok(resume.getCompletionPercentage()))
                .orElseGet(() -> ResponseEntity.ok(0));
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
