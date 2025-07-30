-- 1. Add question-related columns to quizzes
ALTER TABLE quizzes 
ADD COLUMN question_text TEXT,
ADD COLUMN correct_answer TEXT,
ADD COLUMN wrong_answer_1 TEXT,
ADD COLUMN wrong_answer_2 TEXT,
ADD COLUMN wrong_answer_3 TEXT;

-- 2. Copy the first question from quiz_questions to quizzes
UPDATE quizzes q
SET question_text = qq.question_text,
    correct_answer = qq.correct_answer,
    wrong_answer_1 = qq.wrong_answer_1,
    wrong_answer_2 = qq.wrong_answer_2,
    wrong_answer_3 = qq.wrong_answer_3
FROM (
  SELECT DISTINCT ON (quiz_id) *
  FROM quiz_questions
  ORDER BY quiz_id, id
) qq
WHERE q.id = qq.quiz_id;

-- 3. Drop foreign key constraint from quiz_answers referencing quiz_questions
ALTER TABLE quiz_answers DROP CONSTRAINT fk_quiz_answers_question;

-- 4. Drop quiz_questions table
DROP TABLE quiz_questions;

-- 5. Simplify quiz_results
ALTER TABLE quiz_results
DROP COLUMN total_questions,
DROP COLUMN correct_answers,
DROP COLUMN passed;

-- 6. Add is_correct to quiz_results
ALTER TABLE quiz_results ADD COLUMN is_correct BOOLEAN;

-- 7. Migrate correctness data from quiz_answers to quiz_results
UPDATE quiz_results r
SET is_correct = CASE 
  WHEN EXISTS (
    SELECT 1 FROM quiz_answers a 
    WHERE a.quiz_result_id = r.id AND a.is_correct = TRUE
  ) THEN TRUE ELSE FALSE END;