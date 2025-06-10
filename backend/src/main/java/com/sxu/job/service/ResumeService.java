package com.sxu.job.service;

import com.sxu.job.dto.ResumeDto;
import com.sxu.job.model.Resume;
import com.sxu.job.model.User;
import com.sxu.job.repository.ResumeRepository;
import com.sxu.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<ResumeDto> getResumeByUsername(String username) {
        log.info("Fetching resume for username: {}", username);
        log.debug("Looking up user with username: {}", username);
        Optional<User> userOpt = userRepository.findByUsername(username);
        log.debug("User found: {}", userOpt.isPresent());
        
        if (userOpt.isEmpty()) {
            log.warn("No user found with username: {}", username);
            return Optional.empty();
        }
        
        return getResumeByUser(userOpt.get());
    }

    @Transactional(readOnly = true)
    public Optional<ResumeDto> getResumeByUser(User user) {
        log.debug("Looking up resume for user ID: {}", user.getId());
        Optional<Resume> resumeOpt = resumeRepository.findByUser(user);
        log.debug("Resume found: {}", resumeOpt.isPresent());
        
        return resumeOpt.map(this::convertToDto);
    }

    @Transactional
    public ResumeDto saveOrUpdateResume(String username, ResumeDto resumeDto) {
        log.info("Saving/Updating resume for username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Resume resume = resumeRepository.findByUser(user)
                .orElseGet(() -> new Resume(user));

        updateResumeFromDto(resume, resumeDto);
        resume = resumeRepository.save(resume);
        
        return convertToDto(resume);
    }

    private void updateResumeFromDto(Resume resume, ResumeDto dto) {
        resume.setFullName(dto.getFullName());
        resume.setPhone(dto.getPhone());
        resume.setEmail(dto.getEmail());
        resume.setEducation(dto.getEducation());
        resume.setSchool(dto.getSchool());
        resume.setMajor(dto.getMajor());
        resume.setGraduationDate(dto.getGraduationDate());
        resume.setSkills(dto.getSkills());
        resume.setExperience(dto.getExperience());
        resume.setProjects(dto.getProjects());
        resume.setSelfIntroduction(dto.getSelfIntroduction());
        resume.setUpdatedAt(LocalDate.now());
    }

    private ResumeDto convertToDto(Resume resume) {
        if (resume == null) return null;
        
        ResumeDto dto = new ResumeDto();
        dto.setId(resume.getId());
        dto.setFullName(resume.getFullName());
        dto.setPhone(resume.getPhone());
        dto.setEmail(resume.getEmail());
        dto.setEducation(resume.getEducation());
        dto.setSchool(resume.getSchool());
        dto.setMajor(resume.getMajor());
        dto.setGraduationDate(resume.getGraduationDate());
        dto.setSkills(resume.getSkills());
        dto.setExperience(resume.getExperience());
        dto.setProjects(resume.getProjects());
        dto.setSelfIntroduction(resume.getSelfIntroduction());
        dto.setCreatedAt(resume.getCreatedAt());
        dto.setUpdatedAt(resume.getUpdatedAt());
        
        // Calculate completion percentage
        int completedFields = 0;
        int totalFields = 10; // Total number of fields to check
        
        if (resume.getFullName() != null && !resume.getFullName().isEmpty()) completedFields++;
        if (resume.getPhone() != null && !resume.getPhone().isEmpty()) completedFields++;
        if (resume.getEmail() != null && !resume.getEmail().isEmpty()) completedFields++;
        if (resume.getEducation() != null && !resume.getEducation().isEmpty()) completedFields++;
        if (resume.getSchool() != null && !resume.getSchool().isEmpty()) completedFields++;
        if (resume.getMajor() != null && !resume.getMajor().isEmpty()) completedFields++;
        if (resume.getGraduationDate() != null) completedFields++;
        if (resume.getSkills() != null && !resume.getSkills().isEmpty()) completedFields++;
        if (resume.getExperience() != null && !resume.getExperience().isEmpty()) completedFields++;
        if (resume.getSelfIntroduction() != null && !resume.getSelfIntroduction().isEmpty()) completedFields++;
        
        dto.setCompletionPercentage((int) ((completedFields / (double) totalFields) * 100));
        
        return dto;
    }
}
