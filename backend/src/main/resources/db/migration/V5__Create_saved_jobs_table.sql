-- Create saved_jobs table
CREATE TABLE IF NOT EXISTS saved_jobs (
    id BIGINT PRIMARY KEY AUTOINCREMENT,
    job_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE(job_id, user_id)
);

-- Insert sample saved jobs
INSERT INTO saved_jobs (job_id, user_id, saved_at)
VALUES
    (1, 1, datetime('now', '-2 days')),
    (2, 1, datetime('now', '-3 days')),
    (3, 1, datetime('now', '-7 days')),
    (4, 1, datetime('now', '-14 days')),
    (5, 1, datetime('now', '-5 days'));

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_saved_jobs_user_id ON saved_jobs(user_id);
