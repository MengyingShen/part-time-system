package com.sxu.job.repository;

import com.sxu.job.model.Application;
import com.sxu.job.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findByStudentId(Long studentId, Pageable pageable);
    Page<Application> findByJobId(Long jobId, Pageable pageable);
    Page<Application> findByJobEmployerId(Long employerId, Pageable pageable);
    long countByJobIdAndStatus(Long jobId, ApplicationStatus status);
    boolean existsByJobIdAndStudentId(Long jobId, Long studentId);
    List<Application> findByJobIdIn(List<Long> jobIds);
}
