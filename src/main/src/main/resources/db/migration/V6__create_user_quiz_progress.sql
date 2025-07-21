-- V6__create_user_quiz_progress.sql

CREATE TABLE user_quiz_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    current_question_index INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    last_updated TIMESTAMP,

    CONSTRAINT fk_user_quiz_progress_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_quiz_progress_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);