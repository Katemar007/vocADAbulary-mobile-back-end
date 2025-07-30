-- 1. Drop quiz_answers (no longer needed)
DROP TABLE IF EXISTS quiz_answers;

-- 2. Clean up quiz_results table to only store one record per attempt
ALTER TABLE quiz_results
    DROP COLUMN IF EXISTS is_correct, -- remove old column if exists (we'll re-add to enforce consistency)
    ADD COLUMN is_correct BOOLEAN NOT NULL;

-- 3. (Optional) Ensure quiz_results has the correct constraints
ALTER TABLE quiz_results
    ADD CONSTRAINT fk_quiz_results_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE quiz_results
    ADD CONSTRAINT fk_quiz_results_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE;
