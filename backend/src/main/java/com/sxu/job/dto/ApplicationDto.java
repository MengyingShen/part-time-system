package com.sxu.job.dto;

import com.sxu.job.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {
    private Long id;
    private String jobTitle;
    private String company;
    private String location;
    private String status;
    private String appliedDate;
    private String interviewDate;
    private String feedback;

    public String getStatusDisplay() {
        return switch (status) {
            case "PENDING" -> "已申请";
            case "REVIEWED" -> "已查看";
            case "INTERVIEW" -> "面试中";
            case "ACCEPTED" -> "已通过";
            case "REJECTED" -> "已拒绝";
            default -> status;
        };
    }
}
