package com.sxu.job.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sxu.job.model.enums.JobType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudentProfile extends BaseEntity {
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
    
    @Column(name = "student_id", length = 50, unique = true)
    private String studentId;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
    
    private LocalDate birthdate;
    
    @Column(length = 100)
    private String school;
    
    @Column(length = 100)
    private String major;
    
    @Column(length = 20)
    private String grade;
    
    @Column(name = "edu_begin")
    private LocalDate eduBegin;
    
    @Column(name = "edu_end")
    private LocalDate eduEnd;
    
    @Column(name = "work_days", length = 50)
    private String workDays;
    
    @Column(name = "work_hours")
    private Integer workHours;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> jobCategories;
    
    @Column(name = "expected_salary_min")
    private Integer expectedSalaryMin;
    
    @Column(name = "expected_salary_max")
    private Integer expectedSalaryMax;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "expected_job_type")
    private JobType expectedJobType;
    
    @Column(name = "resume_url", length = 255)
    private String resumeUrl;
}

enum Gender {
    MALE, FEMALE, OTHER
}
