-- Insert roles
INSERT IGNORE INTO roles (id, name) VALUES 
(1, 'ROLE_STUDENT'),
(2, 'ROLE_EMPLOYER'),
(3, 'ROLE_ADMIN');

-- Insert test users (passwords are all 'password' encrypted with BCrypt)
-- Student user
INSERT IGNORE INTO users (id, username, email, password, full_name, phone_number, role) VALUES 
(1, 'student1', 'student1@example.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W', '张三', '13800138001', 'STUDENT');

-- Employer user
INSERT IGNORE INTO users (id, username, email, password, full_name, phone_number, role) VALUES 
(2, 'employer1', 'employer1@example.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W', '招聘公司', '13900139001', 'EMPLOYER');

-- Admin user
INSERT IGNORE INTO users (id, username, email, password, full_name, phone_number, role) VALUES 
(3, 'admin', 'admin@example.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJddBaZSqmUc8W', '管理员', '13700137001', 'ADMIN');

-- Assign roles to users
INSERT IGNORE INTO users_roles (user_id, role_id) VALUES 
(1, 1), -- student1 is a student
(2, 2), -- employer1 is an employer
(3, 3); -- admin is an admin

-- Create student profile
INSERT IGNORE INTO student_profiles (user_id, school, major, grade, gender, birthdate, student_id, resume_url) VALUES 
(1, '清华大学', '计算机科学与技术', '大四', 'MALE', '2000-01-01', '2020012345', 'https://example.com/resumes/1.pdf');

-- Create employer profile
INSERT IGNORE INTO employers (id, user_id, company_name, description, address, website, logo_url, verification_status) VALUES 
(1, 2, '示例科技有限公司', '一家专注于互联网技术的创新公司', '北京市海淀区中关村', 'https://example.com', 'https://example.com/logo.png', 'VERIFIED');

-- Insert test jobs
INSERT IGNORE INTO jobs (id, title, description, requirements, responsibilities, job_type, location, salary_min, salary_max, status, employer_id) VALUES 
(1, 'Java开发实习生', '参与公司核心业务系统的开发与维护', '计算机相关专业，熟悉Java基础，了解Spring框架', '1. 参与需求分析和系统设计\n2. 编写高质量代码\n3. 参与代码审查', 'INTERNSHIP', '北京市海淀区', 3000, 6000, 'OPEN', 1),
(2, '前端开发工程师', '负责公司产品的前端开发工作', '1. 熟悉Vue/React等前端框架\n2. 良好的JavaScript基础\n3. 了解前端工程化', '1. 负责前端页面开发\n2. 与后端工程师协作完成接口联调\n3. 优化前端性能', 'FULL_TIME', '上海市浦东新区', 15000, 25000, 'OPEN', 1),
(3, '产品经理助理', '协助产品经理完成产品规划与设计', '1. 对互联网产品有浓厚兴趣\n2. 良好的沟通能力\n3. 熟练使用Axure等原型工具', '1. 参与产品需求分析\n2. 制作产品原型\n3. 编写产品文档', 'PART_TIME', '广州市天河区', 200, 300, 'OPEN', 1);

-- Insert saved jobs for student
INSERT IGNORE INTO saved_jobs (user_id, job_id) VALUES 
(1, 1),
(1, 2);

-- Insert job applications
INSERT IGNORE INTO applications (job_id, user_id, status, cover_letter) VALUES 
(1, 1, 'PENDING', '我对贵公司的Java开发实习生职位非常感兴趣，希望能有机会加入贵公司。');
