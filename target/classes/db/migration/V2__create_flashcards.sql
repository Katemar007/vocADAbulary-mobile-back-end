-- V2__create_flashcards_table.sql

CREATE TABLE flashcards (
    id SERIAL PRIMARY KEY,
    topic_id INT NOT NULL,
    word VARCHAR(255) NOT NULL,
    definition TEXT,
    example TEXT,
    synonyms VARCHAR(255),
    phonetic VARCHAR(255),
    audio_url VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT fk_flashcards_topic FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE
);