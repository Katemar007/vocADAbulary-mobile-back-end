-- Create table for Constructor Sentences
CREATE TABLE constructor_sentences (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
    sentence_template TEXT NOT NULL
);

-- Create table for blanks inside each constructor sentence
CREATE TABLE constructor_sentence_blanks (
    id BIGSERIAL PRIMARY KEY,
    sentence_id BIGINT NOT NULL REFERENCES constructor_sentences(id) ON DELETE CASCADE,
    order_index INT NOT NULL,
    flashcard_id BIGINT NOT NULL REFERENCES flashcards(id) ON DELETE CASCADE
);

-- Indexes for performance
CREATE INDEX idx_constructor_sentences_topic_id ON constructor_sentences(topic_id);
CREATE INDEX idx_constructor_sentence_blanks_sentence_id ON constructor_sentence_blanks(sentence_id);
