package com.sxu.job.config;

import com.sxu.job.model.Resume;
import com.sxu.job.model.User;
import com.sxu.job.repository.ResumeRepository;
import com.sxu.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class ResumeInitializer {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    @Bean
    public CommandLineRunner initResumeData() {
        return args -> {
            // Check if the user exists
            userRepository.findByUsername("stepbystepcode").ifPresent(user -> {
                // Check if resume already exists
                if (!resumeRepository.existsByUser(user)) {
                    Resume resume = new Resume();
                    resume.setUser(user);
                    resume.setFullName("张三");
                    resume.setPhone("13812345678");
                    resume.setEmail("zhangsan@example.com");
                    resume.setEducation("本科");
                    resume.setSchool("北京大学");
                    resume.setMajor("计算机科学");
                    resume.setGraduationDate(LocalDate.of(2027, 6, 1));
                    resume.setSkills("Java, Python, JavaScript, React, 英语 (流利)");
                    resume.setExperience("暂无工作经历");
                    resume.setProjects("暂无项目经验");
                    resume.setSelfIntroduction("北京大学计算机科学专业在读学生，对编程充满热情，具备扎实的计算机基础知识。");
                    
                    resumeRepository.save(resume);
                    log.info("Created resume for user: stepbystepcode");
                } else {
                    log.info("Resume already exists for user: stepbystepcode");
                }
            });
        };
    }
}
