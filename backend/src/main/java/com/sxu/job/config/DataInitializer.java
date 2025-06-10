package com.sxu.job.config;

import com.sxu.job.model.Employer;
import com.sxu.job.model.User;
import com.sxu.job.model.enums.Role;
import com.sxu.job.repository.EmployerRepository;
import com.sxu.job.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            EmployerRepository employerRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Create a sample employer user
            if (userRepository.findByUsername("starbucks").isEmpty()) {
                User employerUser = User.builder()
                    .username("starbucks")
                    .email("hr@starbucks.com")
                    .password(passwordEncoder.encode("password"))
                    .fullName("Starbucks HR")
                    .phoneNumber("13800138000")
                    .role(Role.EMPLOYER)
                    .build();
                employerUser = userRepository.save(employerUser);

                Employer employer = Employer.builder()
                    .user(employerUser)
                    .companyName("星巴克")
                    .companyDescription("全球领先的专业咖啡烘焙商和零售商")
                    .companyAddress("上海市静安区南京西路123号")
                    .companyWebsite("https://www.starbucks.com.cn")
                    .verificationStatus(Employer.VerificationStatus.VERIFIED)
                    .build();
                employerRepository.save(employer);
            }

            // Add more sample employers as needed...
        };
    }
}
