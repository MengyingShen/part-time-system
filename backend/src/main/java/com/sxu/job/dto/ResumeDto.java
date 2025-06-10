package com.sxu.job.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ResumeDto {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String education;
    private String school;
    private String major;
    private LocalDate graduationDate;
    private String skills;
    private String experience;
    private String projects;
    private String selfIntroduction;
    private Integer completionPercentage;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
