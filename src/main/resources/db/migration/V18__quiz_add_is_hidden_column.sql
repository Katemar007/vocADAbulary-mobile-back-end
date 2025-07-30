-- 1. Add a hidden column to mark quizzes as hidden for a user
ALTER TABLE quiz_results
    ADD COLUMN hidden BOOLEAN NOT NULL DEFAULT FALSE;