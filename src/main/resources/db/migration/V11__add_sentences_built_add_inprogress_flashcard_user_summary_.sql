-- add a new column 'in_progress_flashcards' to the user_progress_summary table 
    ALTER TABLE user_progress_summary
    ADD COLUMN in_progress_flashcards INT DEFAULT 0;