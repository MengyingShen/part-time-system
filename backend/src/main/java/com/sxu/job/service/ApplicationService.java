package com.sxu.job.service;

import com.sxu.job.dto.ApplicationDto;
import com.sxu.job.model.enums.ApplicationStatus;
import com.sxu.job.repository.ApplicationRepository;
import com.sxu.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional(readOnly = true)
    public List<ApplicationDto> getStudentApplications(Long studentId) {
        // For now, return mock data matching the frontend
        return getMockApplications();
    }

    private List<ApplicationDto> getMockApplications() {
        return List.of(
            ApplicationDto.builder()
                .id(1L)
                .jobTitle("校园咖啡馆服务员")
                .company("星巴克")
                .location("校内")
                .status(ApplicationStatus.INTERVIEW.name())
                .appliedDate("2025-04-10")
                .interviewDate("2025-04-15 14:00")
                .build(),
            ApplicationDto.builder()
                .id(2L)
                .jobTitle("图书馆助理")
                .company("大学图书馆")
                .location("校内")
                .status(ApplicationStatus.PENDING.name())
                .appliedDate("2025-04-11")
                .build(),
            ApplicationDto.builder()
                .id(3L)
                .jobTitle("前端开发实习生")
                .company("科技创新中心")
                .location("线上/线下")
                .status(ApplicationStatus.ACCEPTED.name())
                .appliedDate("2025-04-01")
                .interviewDate("2025-04-05 10:30")
                .feedback("面试表现很好，技术能力符合要求。欢迎加入我们的团队！请在下周一前完成入职手续。")
                .build(),
            ApplicationDto.builder()
                .id(4L)
                .jobTitle("社团活动助理")
                .company("学生会")
                .location("校内")
                .status(ApplicationStatus.REJECTED.name())
                .appliedDate("2025-04-02")
                .feedback("感谢您的申请。虽然您的背景很出色，但我们需要有更多活动组织经验的人选。希望您未来申请其他职位时能够成功。")
                .build()
        );
    }
}
