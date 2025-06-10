-- Create applications table
CREATE TABLE IF NOT EXISTS applications (
    id BIGINT PRIMARY KEY AUTOINCREMENT,
    job_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    applied_date VARCHAR(10) NOT NULL,
    interview_date VARCHAR(20),
    feedback TEXT,
    cover_letter TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample applications
INSERT INTO applications (job_id, student_id, job_title, company, location, status, applied_date, interview_date, feedback)
VALUES
    (1, 1, '校园咖啡馆服务员', '星巴克', '校内', 'INTERVIEW', '2025-04-10', '2025-04-15 14:00', null),
    (2, 1, '图书馆助理', '大学图书馆', '校内', 'PENDING', '2025-04-11', null, null),
    (3, 1, '前端开发实习生', '科技创新中心', '线上/线下', 'ACCEPTED', '2025-04-01', '2025-04-05 10:30', '面试表现很好，技术能力符合要求。欢迎加入我们的团队！请在下周一前完成入职手续。'),
    (4, 1, '社团活动助理', '学生会', '校内', 'REJECTED', '2025-04-02', null, '感谢您的申请。虽然您的背景很出色，但我们需要有更多活动组织经验的人选。希望您未来申请其他职位时能够成功。');

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_applications_student_id ON applications(student_id);
CREATE INDEX IF NOT EXISTS idx_applications_status ON applications(status);
