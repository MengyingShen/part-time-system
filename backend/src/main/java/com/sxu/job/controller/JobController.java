package com.sxu.job.controller;

import com.sxu.job.model.Job;
import com.sxu.job.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/jobs/search")
    public ResponseEntity<?> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String status) {
        
        // Build query based on parameters
        List<Job> jobs = jobRepository.findAll();
        
        // Apply filters
        List<Job> filteredJobs = jobs.stream()
            .filter(job -> keyword == null || keyword.isEmpty() || 
                         (job.getTitle() != null && job.getTitle().toLowerCase().contains(keyword.toLowerCase())) ||
                         (job.getDescription() != null && job.getDescription().toLowerCase().contains(keyword.toLowerCase())))
            .filter(job -> location == null || location.isEmpty() || 
                         (job.getLocation() != null && job.getLocation().toLowerCase().contains(location.toLowerCase())))
            .filter(job -> jobType == null || jobType.isEmpty() || 
                         (job.getJobType() != null && job.getJobType().equalsIgnoreCase(jobType)))
            .filter(job -> status == null || status.isEmpty() || 
                         (job.getStatus() != null && job.getStatus().equalsIgnoreCase(status)))
            .collect(Collectors.toList());
        
        // Convert to DTO with relative time
        List<Map<String, Object>> result = filteredJobs.stream().map(job -> {
            long daysAgo = ChronoUnit.DAYS.between(
                job.getCreatedAt().toLocalDate(),
                LocalDateTime.now().toLocalDate()
            );
            
            String postedAt;
            if (daysAgo == 0) {
                postedAt = "今天";
            } else if (daysAgo == 1) {
                postedAt = "昨天";
            } else if (daysAgo < 7) {
                postedAt = daysAgo + "天前";
            } else {
                postedAt = (daysAgo / 7) + "周前";
            }
            
            // Format salary
            String salaryDisplay = "面议";
            if (job.getSalaryMin() != null && job.getSalaryMax() != null) {
                salaryDisplay = String.format("¥%d-%d/小时", 
                    job.getSalaryMin().intValue(), 
                    job.getSalaryMax().intValue());
            }
            
            return Map.<String, Object>of(
                "id", job.getId(),
                "title", job.getTitle(),
                "company", job.getEmployer() != null ? job.getEmployer().getCompanyName() : "",
                "location", job.getLocation() != null ? job.getLocation() : "",
                "type", job.getJobType() != null ? getJobTypeDisplayName(job.getJobType()) : "",
                "description", job.getDescription() != null ? job.getDescription() : "",
                "salary", salaryDisplay,
                "postedAt", postedAt,
                "status", job.getStatus() != null ? job.getStatus() : ""
            );
        }).collect(Collectors.toList());
        
        // Filter by status if provided
        if (status != null && !status.isEmpty()) {
            result = result.stream()
                .filter(job -> status.equalsIgnoreCase((String) job.get("status")))
                .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(result);
    }
    
    private String getJobTypeDisplayName(String jobType) {
        if (jobType == null) return "";
        switch (jobType.toUpperCase()) {
            case "PART_TIME": 
            case "兼职":
                return "兼职";
            case "STUDENT_WORK":
            case "学生工作":
                return "学生工作";
            case "INTERNSHIP":
            case "实习":
                return "实习";
            case "VOLUNTEER":
            case "志愿者":
                return "志愿者";
            case "FULL_TIME":
            case "全职":
                return "全职";
            case "TEMPORARY":
            case "临时工":
                return "临时工";
            default: 
                return jobType;
        }
    }

    @GetMapping("/me/saved-jobs")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<?> getSavedJobs() {
        // TODO: Implement actual logic to fetch saved jobs for the current user
        return ResponseEntity.ok(List.of());
    }
}
