-- Insert sample jobs
INSERT INTO jobs (id, title, company, location, type, description, salary, status, created_at, updated_at)
VALUES 
(1, '校园咖啡馆服务员', '星巴克', '校内', '兼职', '负责接待顾客、点单和制作饮品。灵活的工作时间，适合课余时间。要求热情友好，有良好的沟通能力。', '¥20-25', 'OPEN', DATETIME('now', '-2 days'), DATETIME('now', '-2 days')),
(2, '图书馆助理', '大学图书馆', '校内', '兼职', '整理书籍、帮助读者查找资料，维护图书馆秩序。安静的工作环境。要求细心，有责任心。', '¥18-20', 'OPEN', DATETIME('now', '-3 days'), DATETIME('now', '-3 days')),
(3, '前端开发实习生', '科技创新中心', '线上/线下', '实习', '参与网站和应用开发，要求有基本的HTML/CSS/JavaScript技能。可以获得行业经验和技术指导。', '¥30-40', 'OPEN', DATETIME('now', '-7 days'), DATETIME('now', '-7 days')),
(4, '社团活动助理', '学生会', '校内', '志愿者', '协助组织校园活动，负责宣传和现场协调。获取活动组织经验。对活动策划有兴趣的同学优先。', '¥15-18', 'OPEN', DATETIME('now', '-5 days'), DATETIME('now', '-5 days')),
(5, '数据分析助理', '研究中心', '校内', '实习', '协助研究人员收集和分析数据，制作可视化报表。要求熟悉Excel和基本统计知识。', '¥25-30', 'OPEN', DATETIME('now', '-1 day'), DATETIME('now', '-1 day'));
