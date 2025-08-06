-- ================================================
-- SEED SENTENCE CONSTRUCTOR TEMPLATES (LEARNED)
-- ================================================

INSERT INTO sentence_templates (template_text, source, context_topic, created_at)
VALUES
    -- Flashcard 2: Object (Java Basics)
    ('In Java, an ___ can be created from a class.', 'manual', 'Java Basics', NOW()),
    -- Flashcard 3: Method (Java Basics)
    ('A class can have a ___ to perform an action.', 'manual', 'Java Basics', NOW()),
    -- Flashcard 6: @RestController (Spring Boot)
    ('The ___ annotation makes a class handle REST calls.', 'manual', 'Spring Boot', NOW()),
    -- Flashcard 9: Component (React Native)
    ('A header in an app can be a ___.', 'manual', 'React Native', NOW()),
    -- Flashcard 11: Props (React Native)
    ('You can pass data to a component using ___.', 'manual', 'React Native', NOW()),
    -- Flashcard 13: Primary Key (Database Design)
    ('Each table should have a unique ___.', 'manual', 'Database Design', NOW()),
    -- Flashcard 18: Stack Trace (Debugging Techniques)
    ('The error message included a long ___.', 'manual', 'Debugging Techniques', NOW()),
    -- Flashcard 19: Console Log (Debugging Techniques)
    ('We used a ___ to print debug information.', 'manual', 'Debugging Techniques', NOW()),
    -- Flashcard 22: Assertion (Unit Testing)
    ('A test will fail if the ___ is not true.', 'manual', 'Unit Testing', NOW());

-- Link each sentence to its blank target (flashcard_id)
INSERT INTO sentence_template_blanks (template_id, blank_index, target_flashcard_id)
VALUES
    ((SELECT id FROM sentence_templates WHERE template_text = 'In Java, an ___ can be created from a class.'), 0, 2),
    ((SELECT id FROM sentence_templates WHERE template_text = 'A class can have a ___ to perform an action.'), 0, 3),
    ((SELECT id FROM sentence_templates WHERE template_text = 'The ___ annotation makes a class handle REST calls.'), 0, 6),
    ((SELECT id FROM sentence_templates WHERE template_text = 'A header in an app can be a ___.'), 0, 9),
    ((SELECT id FROM sentence_templates WHERE template_text = 'You can pass data to a component using ___.') ,0, 11),
    ((SELECT id FROM sentence_templates WHERE template_text = 'Each table should have a unique ___.') ,0, 13),
    ((SELECT id FROM sentence_templates WHERE template_text = 'The error message included a long ___.') ,0, 18),
    ((SELECT id FROM sentence_templates WHERE template_text = 'We used a ___ to print debug information.') ,0, 19),
    ((SELECT id FROM sentence_templates WHERE template_text = 'A test will fail if the ___ is not true.') ,0, 22);
