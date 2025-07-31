-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
);

-- =========================
-- TOPICS
-- =========================
CREATE TABLE topics (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
);

-- =========================
-- FLASHCARDS
-- =========================
CREATE TABLE flashcards (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    word VARCHAR(255) NOT NULL,
    definition TEXT NOT NULL,
    example TEXT,
    synonyms TEXT,
    phonetic VARCHAR(255),
    audio_url VARCHAR(500),
    created_by BIGINT,
    created_at TIMESTAMP DEFAULT NOW()
);

-- =========================
-- USER_FLASHCARDS
-- =========================
CREATE TABLE user_flashcards (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    flashcard_id BIGINT NOT NULL REFERENCES flashcards(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL,
    last_reviewed TIMESTAMP,
    in_wallet BOOLEAN,
    is_hidden BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (user_id, flashcard_id)
);

-- =========================
-- USER_PROGRESS_SUMMARY
-- =========================
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
    in_progress_flashcards INT DEFAULT 0,
    CONSTRAINT fk_user_progress_summary_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- =========================
-- QUIZZES
-- =========================
CREATE TABLE quizzes (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW(),
    question_text TEXT NOT NULL,
    correct_answer TEXT NOT NULL,
    wrong_answer_1 TEXT NOT NULL,
    wrong_answer_2 TEXT NOT NULL,
    wrong_answer_3 TEXT NOT NULL
);

-- =========================
-- QUIZ RESULTS
-- =========================
CREATE TABLE user_quiz_attempts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    quiz_id BIGINT NOT NULL REFERENCES quizzes(id) ON DELETE CASCADE,
    taken_at TIMESTAMP DEFAULT NOW(),
    is_passed BOOLEAN NOT NULL
);

CREATE TABLE user_quiz_summary (
    user_id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    quizzes_attempted INT DEFAULT 0,
    quizzes_passed INT DEFAULT 0,
    quiz_success_rate FLOAT DEFAULT 0
);

CREATE TABLE user_quiz_status (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    quiz_id BIGINT NOT NULL REFERENCES quizzes(id) ON DELETE CASCADE,
    is_hidden BOOLEAN DEFAULT FALSE,
    successful_streak INT DEFAULT 0,
    PRIMARY KEY (user_id, quiz_id)
);