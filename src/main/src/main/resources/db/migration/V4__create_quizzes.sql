-- V4__create_quizzes_table.sql

CREATE TABLE quizzes (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_quizzes_topic FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE
);