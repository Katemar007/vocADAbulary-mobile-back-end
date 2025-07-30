-- 1. Add a streak_count column to track consecutive passes
ALTER TABLE quiz_results
    ADD COLUMN streak_count INT NOT NULL DEFAULT 0;

-- 2. Initialize streak_count based on is_correct (for existing rows)
-- If the user previously passed the quiz, start with 1; otherwise, 0
UPDATE quiz_results
SET streak_count = CASE 
    WHEN is_correct = TRUE THEN 1
    ELSE 0
END;