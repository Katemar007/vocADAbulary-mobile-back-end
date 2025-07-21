-- V7__create_user_progress_summary.sql

CREATE TABLE user_progress_summary (
    user_id BIGINT PRIMARY KEY,
    total_flashcards INT DEFAULT 0,
    learned_flashcards INT DEFAULT 0,
    failed_flashcards INT DEFAULT 0,
    quizzes_attempted INT DEFAULT 0,
    quizzes_passed INT DEFAULT 0,
    quiz_success_rate FLOAT DEFAULT 0,
    sentences_built INT DEFAULT 0,
    last_active DATE,
    updated_at TIMESTAMP,

    CONSTRAINT fk_user_progress_summary_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );