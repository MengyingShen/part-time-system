-- Add company_name column to jobs table for simpler querying
ALTER TABLE jobs ADD COLUMN company_name VARCHAR(100);

-- Update existing jobs with company names
UPDATE jobs SET company_name = (
    SELECT company_name 
    FROM employers 
    WHERE employers.id = jobs.employer_id
) WHERE company_name IS NULL;

-- Add NOT NULL constraint after backfilling
ALTER TABLE jobs MODIFY company_name VARCHAR(100) NOT NULL;

-- Insert sample jobs if they don't exist
INSERT INTO jobs (
    id, title, description, requirements, responsibilities, job_type, 
    location, salary_min, salary_max, status, employer_id, company_name
) VALUES 
(1, '校园咖啡馆服务员', 
  '负责接待顾客、点单和制作饮品。灵活的工作时间，适合课余时间。',
  '热情友好，有良好的沟通能力，有服务经验者优先。',
  '1. 接待顾客并协助点单\n2. 制作咖啡和简单饮品\n3. 保持工作区域整洁',
  'PART_TIME', '校内', 20.00, 25.00, 'OPEN', 
  (SELECT id FROM employers WHERE company_name = '星巴克' LIMIT 1), '星巴克')
ON CONFLICT (id) DO NOTHING;

INSERT INTO jobs (
    id, title, description, requirements, job_type, 
    location, salary_min, salary_max, status, employer_id, company_name
) VALUES 
(2, '图书馆助理', 
  '整理书籍、帮助读者查找资料，维护图书馆秩序。',
  '细心，有责任心，能适应安静的工作环境。',
  'STUDENT_WORK', '校内', 18.00, 20.00, 'OPEN',
  (SELECT id FROM employers WHERE company_name = '大学图书馆' LIMIT 1), '大学图书馆')
ON CONFLICT (id) DO NOTHING;

-- Add more sample jobs as needed...

-- Create an index on company_name for faster lookups
CREATE INDEX idx_jobs_company ON jobs(company_name);
