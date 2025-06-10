package com.sxu.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedJobDto {
    private Long id;
    private Long jobId;
    private String title;
    private String company;
    private String location;
    private String type;
    private String description;
    private String salary;
    private String savedAt;

    public String getRelativeSavedAt() {
        if (savedAt == null) return "";
        
        LocalDateTime savedDate = LocalDateTime.parse(savedAt.replace(" ", "T"));
        long days = ChronoUnit.DAYS.between(savedDate, LocalDateTime.now());
        
        if (days == 0) return "今天";
        if (days == 1) return "昨天";
        if (days < 7) return days + "天前";
        if (days < 30) return (days / 7) + "周前";
        return "1个月前";
    }
}
