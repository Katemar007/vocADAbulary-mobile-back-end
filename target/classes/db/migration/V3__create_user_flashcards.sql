-- V3__create_user_flashcards_table.sql

CREATE TABLE user_flashcards (
    user_id BIGINT NOT NULL,
    flashcard_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    last_reviewed TIMESTAMP WITHOUT TIME ZONE,

    PRIMARY KEY (user_id, flashcard_id),

    CONSTRAINT fk_userflashcards_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_userflashcards_flashcard FOREIGN KEY (flashcard_id) REFERENCES flashcards(id) ON DELETE CASCADE
);