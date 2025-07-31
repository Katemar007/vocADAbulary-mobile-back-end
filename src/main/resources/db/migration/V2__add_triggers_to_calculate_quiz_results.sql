-- =============================================
-- Function: update quiz summary and status
-- =============================================

CREATE OR REPLACE FUNCTION update_quiz_summary_and_status()
RETURNS TRIGGER AS $$
BEGIN
    -- 1️⃣ Update user_quiz_summary (attempts, passed, success rate)
    INSERT INTO user_quiz_summary (user_id, quizzes_attempted, quizzes_passed, quiz_success_rate)
    VALUES (
        NEW.user_id,
        1,
        CASE WHEN NEW.is_passed THEN 1 ELSE 0 END,
        CASE WHEN NEW.is_passed THEN 1.0 ELSE 0.0 END
    )
    ON CONFLICT (user_id)
    DO UPDATE SET
        quizzes_attempted = user_quiz_summary.quizzes_attempted + 1,
        quizzes_passed = user_quiz_summary.quizzes_passed + (CASE WHEN NEW.is_passed THEN 1 ELSE 0 END),
        quiz_success_rate = 
            CASE 
                WHEN (user_quiz_summary.quizzes_attempted + 1) > 0 
                THEN (user_quiz_summary.quizzes_passed + (CASE WHEN NEW.is_passed THEN 1 ELSE 0 END))::float 
                     / (user_quiz_summary.quizzes_attempted + 1)
                ELSE 0 
            END;

    -- 2️⃣ Update user_quiz_status (streak, hidden)
    INSERT INTO user_quiz_status (user_id, quiz_id, is_hidden, successful_streak)
    VALUES (
        NEW.user_id,
        NEW.quiz_id,
        CASE WHEN NEW.is_passed THEN FALSE ELSE FALSE END, -- default is false
        CASE WHEN NEW.is_passed THEN 1 ELSE 0 END
    )
    ON CONFLICT (user_id, quiz_id)
    DO UPDATE SET
        successful_streak = CASE 
            WHEN NEW.is_passed THEN user_quiz_status.successful_streak + 1 
            ELSE 0 
        END,
        is_hidden = CASE 
            WHEN NEW.is_passed AND (user_quiz_status.successful_streak + 1) >= 2 
            THEN TRUE 
            ELSE FALSE 
        END;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- =============================================
-- Trigger: run after each insert in user_quiz_attempts
-- =============================================
CREATE TRIGGER trg_update_quiz_summary_and_status
AFTER INSERT ON user_quiz_attempts
FOR EACH ROW
EXECUTE FUNCTION update_quiz_summary_and_status();