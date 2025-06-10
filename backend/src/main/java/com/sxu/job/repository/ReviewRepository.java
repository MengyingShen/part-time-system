package com.sxu.job.repository;

import com.sxu.job.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByTargetId(Long targetId, Pageable pageable);
    Page<Review> findByReviewerId(Long reviewerId, Pageable pageable);
    Page<Review> findByJobId(Long jobId, Pageable pageable);
    
    @Query("SELECT AVG(CASE r.rating WHEN 'ONE' THEN 1 WHEN 'TWO' THEN 2 WHEN 'THREE' THEN 3 WHEN 'FOUR' THEN 4 WHEN 'FIVE' THEN 5 ELSE 0 END) " +
           "FROM Review r WHERE r.target.id = :userId")
    Double getAverageRatingByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.target.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    boolean existsByReviewerIdAndTargetId(Long reviewerId, Long targetId);
    boolean existsByReviewerIdAndJobId(Long reviewerId, Long jobId);
}
