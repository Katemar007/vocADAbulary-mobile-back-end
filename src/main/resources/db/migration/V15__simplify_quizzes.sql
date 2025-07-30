-- 1. Drop the old column if it exists
ALTER TABLE quiz_results
    DROP COLUMN IF EXISTS is_correct;

-- 2. Add the column as nullable first
ALTER TABLE quiz_results
    ADD COLUMN is_correct BOOLEAN;

-- 3. Set default values for existing rows
-- (Here we set everything to FALSE initially, you can adjust if needed)
UPDATE quiz_results
SET is_correct = FALSE
WHERE is_correct IS NULL;

-- 4. Make the column NOT NULL after data is populated
ALTER TABLE quiz_results
    ALTER COLUMN is_correct SET NOT NULL;