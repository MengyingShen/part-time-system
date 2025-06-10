package com.sxu.job.model;

import com.sxu.job.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Application extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @Column(name = "job_title", nullable = false)
    private String jobTitle;
    
    @Column(name = "company", nullable = false)
    private String company;
    
    @Column(name = "location", nullable = false)
    private String location;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status;
    
    @Column(name = "applied_date", nullable = false)
    private String appliedDate;
    
    @Column(name = "interview_date")
    private String interviewDate;
    
    @Column(columnDefinition = "TEXT")
    private String feedback;
    
    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;
    
    @PrePersist
    protected void onCreate() {
        if (appliedDate == null) {
            appliedDate = java.time.LocalDate.now().toString();
        }
        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
    }
}
