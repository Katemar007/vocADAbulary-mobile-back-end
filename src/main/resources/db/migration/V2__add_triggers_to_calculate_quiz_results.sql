-- =============================================
-- Function: update_quiz_summary_and_status
-- =============================================

CREATE OR REPLACE FUNCTION update_quiz_summary_and_status()
RETURNS TRIGGER AS $$
BEGIN
    -- 1️⃣ Update user_quiz_status (streak, hidden)
    INSERT INTO user_quiz_status (user_id, quiz_id, is_hidden, successful_streak)
    VALUES (
        NEW.user_id,
        NEW.quiz_id,
        FALSE,
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

    -- 2️⃣ Update user_quiz_summary (attempts, passed, success rate)
    INSERT INTO user_quiz_summary (user_id, quizzes_attempted, quizzes_passed, quiz_success_rate)
    VALUES (
        NEW.user_id,
        1,
        (SELECT COUNT(*) FROM user_quiz_status WHERE user_id = NEW.user_id AND is_hidden = TRUE),
        CASE
            WHEN (SELECT COUNT(*) FROM quizzes) > 0
            THEN (SELECT COUNT(*) FROM user_quiz_status WHERE user_id = NEW.user_id AND is_hidden = TRUE)::float / (SELECT COUNT(*) FROM quizzes)
            ELSE 0
        END
    )
    ON CONFLICT (user_id)
    DO UPDATE SET
        quizzes_attempted = user_quiz_summary.quizzes_attempted + 1,
        quizzes_passed = (
            SELECT COUNT(*) FROM user_quiz_status WHERE user_id = NEW.user_id AND is_hidden = TRUE
        ),
        quiz_success_rate = CASE
            WHEN (SELECT COUNT(*) FROM quizzes) > 0
            THEN (
                SELECT COUNT(*) FROM user_quiz_status WHERE user_id = NEW.user_id AND is_hidden = TRUE
            )::float / (SELECT COUNT(*) FROM quizzes)
            ELSE 0
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