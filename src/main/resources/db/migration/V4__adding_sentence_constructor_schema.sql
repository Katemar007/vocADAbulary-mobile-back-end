-- 1) Global sentence templates
CREATE TABLE IF NOT EXISTS sentence_templates (
  id               BIGSERIAL PRIMARY KEY,
  template_text    TEXT        NOT NULL,         -- uses ___ as blanks
  source           VARCHAR(32) NOT NULL DEFAULT 'manual',  -- 'manual' | 'ai'
  context_topic    TEXT,                         -- optional, for AI provenance (no FK)
  created_at       TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- 2) Blanks per template (optional target to allow reveal of exact word)
CREATE TABLE IF NOT EXISTS sentence_template_blanks (
  id                   BIGSERIAL PRIMARY KEY,
  template_id          BIGINT NOT NULL REFERENCES sentence_templates(id) ON DELETE CASCADE,
  blank_index          INT    NOT NULL,          -- 0-based
  target_flashcard_id  BIGINT REFERENCES flashcards(id) -- nullable: if null, any LEARNED word is accepted
);

CREATE UNIQUE INDEX IF NOT EXISTS uq_sentence_template_blank_idx
ON sentence_template_blanks(template_id, blank_index);

-- 3) Per-user per-blank stats (for fail-streak & reveal)
CREATE TABLE IF NOT EXISTS user_sentence_blank_stats (
  user_id        BIGINT NOT NULL,
  template_id    BIGINT NOT NULL,
  blank_index    INT    NOT NULL,
  total_correct  INT    NOT NULL DEFAULT 0,
  total_incorrect INT   NOT NULL DEFAULT 0,
  fail_streak    INT    NOT NULL DEFAULT 0,      -- reset on correct; +1 on wrong
  last_attempt_at TIMESTAMPTZ,
  PRIMARY KEY (user_id, template_id, blank_index)
);

-- 4) Attempt audit
CREATE TABLE IF NOT EXISTS user_sentence_attempts (
  id           BIGSERIAL PRIMARY KEY,
  user_id      BIGINT NOT NULL,
  template_id  BIGINT NOT NULL REFERENCES sentence_templates(id) ON DELETE CASCADE,
  final_text   TEXT   NOT NULL,
  is_correct   BOOLEAN NOT NULL,
  created_at   TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- 5) Per-blank fills for each attempt
CREATE TABLE IF NOT EXISTS user_sentence_attempt_fills (
  id                   BIGSERIAL PRIMARY KEY,
  attempt_id           BIGINT NOT NULL REFERENCES user_sentence_attempts(id) ON DELETE CASCADE,
  blank_index          INT    NOT NULL,
  typed_word           TEXT   NOT NULL,
  matched_flashcard_id BIGINT REFERENCES flashcards(id),   -- nullable if no match
  is_correct           BOOLEAN NOT NULL
);

-- 6) Helpful indexes (cheap even with small data)
CREATE INDEX IF NOT EXISTS idx_user_attempts_user ON user_sentence_attempts(user_id);
CREATE INDEX IF NOT EXISTS idx_user_attempts_template ON user_sentence_attempts(template_id);
CREATE INDEX IF NOT EXISTS idx_user_blank_stats_user_template ON user_sentence_blank_stats(user_id, template_id);

-- For your existing table (if not present):
-- Speeds LEARNED lookups
CREATE INDEX IF NOT EXISTS idx_user_flashcards_user_status
  ON user_flashcards(user_id, status);
