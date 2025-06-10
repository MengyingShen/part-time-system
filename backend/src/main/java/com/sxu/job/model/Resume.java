package com.sxu.job.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "resume")
public class Resume {
    
    public Resume() {
        // Default constructor for JPA
    }
    
    public Resume(User user) {
        this.user = user;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String education;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private LocalDate graduationDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String skills;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String experience;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String projects;

    @Column(name = "self_introduction", columnDefinition = "TEXT", nullable = false)
    private String selfIntroduction;

    @Column(updatable = false)
    private LocalDate createdAt = LocalDate.now();

    private LocalDate updatedAt = LocalDate.now();
}
