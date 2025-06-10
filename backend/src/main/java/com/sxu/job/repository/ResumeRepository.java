package com.sxu.job.repository;

import com.sxu.job.model.Resume;
import com.sxu.job.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByUser(User user);
    Optional<Resume> findByUserId(Long userId);
    boolean existsByUser(User user);
}
