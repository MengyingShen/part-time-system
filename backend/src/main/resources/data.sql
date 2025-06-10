-- Clear existing data is handled by schema.sql
-- This file only contains data insertion

-- Insert roles
INSERT INTO roles (name) VALUES 
('ROLE_STUDENT'),
('ROLE_EMPLOYER'),
('ROLE_ADMIN');

-- Set auto-increment to avoid conflicts
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE roles AUTO_INCREMENT = 1;
ALTER TABLE employers AUTO_INCREMENT = 1;
ALTER TABLE jobs AUTO_INCREMENT = 1;

-- Insert test users (passwords are all 'password' encrypted with BCrypt)
-- Student user
SET @student_id = 1;
INSERT INTO users (id, username, email, password, full_name, phone_number, role) VALUES 
(@student_id, 'student1', 'student1@example.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W', '张三', '13800138001', 'STUDENT');

-- Employer user
SET @employer_id = 2;
INSERT INTO users (id, username, email, password, full_name, phone_number, role) VALUES 
(@employer_id, 'employer1', 'employer1@example.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W', '招聘公司', '13900139001', 'EMPLOYER');

-- Admin user
SET @admin_id = 3;
INSERT INTO users (id, username, email, password, full_name, phone_number, role) VALUES 
(@admin_id, 'admin', 'admin@example.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W', '管理员', '13700137001', 'ADMIN');

-- Assign roles to users
INSERT INTO users_roles (user_id, role_id) VALUES 
(@student_id, 1), -- student1 is a student
(@employer_id, 2), -- employer1 is an employer
(@admin_id, 3); -- admin is an admin

-- Create student profile
INSERT INTO student_profiles (user_id, school, major, grade, gender, birthdate, student_id, resume_url) VALUES 
(@student_id, '清华大学', '计算机科学与技术', '大四', 'MALE', '2000-01-01', '2020012345', 'https://example.com/resumes/1.pdf');

-- Create employer profile
SET @employer_company_id = 1;
INSERT INTO employers (id, user_id, company_name, description, address, website, logo_url, verification_status) VALUES 
(@employer_company_id, @employer_id, '示例科技有限公司', '一家专注于互联网技术的创新公司', '北京市海淀区中关村', 'https://example.com', 'https://example.com/logo.png', 'VERIFIED');

-- Insert test jobs
SET @java_job_id = 1;
SET @frontend_job_id = 2;
SET @pm_job_id = 3;

INSERT INTO jobs (id, title, description, requirements, responsibilities, job_type, location, salary_min, salary_max, status, employer_id) VALUES 
(@java_job_id, 'Java开发实习生', '参与公司核心业务系统的开发与维护', '计算机相关专业，熟悉Java基础，了解Spring框架', '1. 参与需求分析和系统设计\n2. 编写高质量代码\n3. 参与代码审查', 'INTERNSHIP', '北京市海淀区', 3000, 6000, 'OPEN', @employer_company_id),
(@frontend_job_id, '前端开发工程师', '负责公司产品的前端开发工作', '1. 熟悉Vue/React等前端框架\n2. 良好的JavaScript基础\n3. 了解前端工程化', '1. 负责前端页面开发\n2. 与后端工程师协作完成接口联调\n3. 优化前端性能', 'FULL_TIME', '上海市浦东新区', 15000, 25000, 'OPEN', @employer_company_id),
(@pm_job_id, '产品经理助理', '协助产品经理完成产品规划与设计', '1. 对互联网产品有浓厚兴趣\n2. 良好的沟通能力\n3. 熟练使用Axure等原型工具', '1. 参与产品需求分析\n2. 制作产品原型\n3. 编写产品文档', 'PART_TIME', '广州市天河区', 200, 300, 'OPEN', @employer_company_id);

-- Insert saved jobs for student
INSERT INTO saved_jobs (user_id, job_id) VALUES 
(@student_id, @java_job_id),
(@student_id, @frontend_job_id);

-- Insert job applications
INSERT INTO applications (job_id, user_id, status, cover_letter) VALUES 
(@java_job_id, @student_id, 'PENDING', '我对贵公司的Java开发实习生职位非常感兴趣，希望能有机会加入贵公司。');
