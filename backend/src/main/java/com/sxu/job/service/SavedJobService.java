package com.sxu.job.service;

import com.sxu.job.dto.SavedJobDto;
import com.sxu.job.model.Job;
import com.sxu.job.model.SavedJob;
import com.sxu.job.model.User;
import com.sxu.job.model.enums.JobStatus;
import com.sxu.job.repository.JobRepository;
import com.sxu.job.repository.SavedJobRepository;
import com.sxu.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<SavedJobDto> getSavedJobsByUsername(String username) {
        log.info("Looking up user by username: {}", username);
        // Get user by username
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                log.error("User not found with username: {}", username);
                return new RuntimeException("User not found");
            });
            
        log.info("Found user with ID: {}", user.getId());
        
        // Get all saved jobs for the user with job details
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "savedAt"));
        List<SavedJob> savedJobs = savedJobRepository.findByUserId(user.getId(), pageable).getContent();
        log.info("Found {} saved jobs for user ID: {}", savedJobs.size(), user.getId());
        
        return savedJobs.stream()
            .peek(job -> log.debug("Processing saved job ID: {} for job ID: {}", job.getId(), job.getJob().getId()))
            .map(this::convertToDto)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Transactional
    public void saveJob(String username, Long jobId) {
        // Get user by username
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        if (savedJobRepository.existsByUserIdAndJobId(user.getId(), jobId)) {
            return; // Already saved
        }

        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        // Only save if job is published
        if (!JobStatus.PUBLISHED.name().equals(job.getStatus())) {
            throw new IllegalStateException("Cannot save a job that is not published");
        }

        SavedJob savedJob = new SavedJob();
        savedJob.setUser(user);
        savedJob.setJob(job);
        savedJob.setSavedAt(LocalDateTime.now());
        
        savedJobRepository.save(savedJob);
    }

    @Transactional
    public void unsaveJob(String username, Long jobId) {
        // Get user by username
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        savedJobRepository.deleteByUserIdAndJobId(user.getId(), jobId);
    }

    private SavedJobDto convertToDto(SavedJob savedJob) {
        if (savedJob == null || savedJob.getJob() == null) {
            return null;
        }

        Job job = savedJob.getJob();
        
        // Get employer name or use a default
        String company = job.getEmployer() != null ? 
            job.getEmployer().getCompanyName() : "未知公司";
            
        // Format salary
        String salary = "面议";
        if (job.getSalaryMin() != null && job.getSalaryMax() != null) {
            salary = formatSalary(job.getSalaryMin()) + " - " + formatSalary(job.getSalaryMax()) + " 元/小时";
        } else if (job.getSalaryMin() != null) {
            salary = "从 " + formatSalary(job.getSalaryMin()) + " 元/小时";
        } else if (job.getSalaryMax() != null) {
            salary = "最高 " + formatSalary(job.getSalaryMax()) + " 元/小时";
        }
        
        return SavedJobDto.builder()
            .id(savedJob.getId())
            .jobId(job.getId())
            .title(job.getTitle())
            .company(company)
            .location(job.getLocation())
            .type(job.getJobType())
            .description(job.getDescription())
            .salary(salary)
            .savedAt(savedJob.getSavedAt() != null ? 
                savedJob.getSavedAt().toString() : 
                LocalDateTime.now().toString())
            .build();
    }
    
    private String formatSalary(BigDecimal amount) {
        if (amount == null) return "";
        // Remove decimal part if it's .00
        return amount.stripTrailingZeros().toPlainString();
    }
}
