-- Java Basics (Topic ID 1)
WITH s1 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (1, 'A ____ is used to create ____ in Java.')
    RETURNING id
), s2 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (1, 'The ____ contains methods like ____().')
    RETURNING id
), s3 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (1, 'A ____ holds values used by the ____.')
    RETURNING id
)
INSERT INTO constructor_sentence_blanks (sentence_id, order_index, flashcard_id)
SELECT s1.id, 1, 1 FROM s1 UNION ALL
SELECT s1.id, 2, 2 FROM s1 UNION ALL
SELECT s2.id, 1, 1 FROM s2 UNION ALL
SELECT s2.id, 2, 3 FROM s2 UNION ALL
SELECT s3.id, 1, 4 FROM s3 UNION ALL
SELECT s3.id, 2, 1 FROM s3;

-- Spring Boot (Topic ID 2)
WITH s4 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (2, 'The ____ annotation creates a ____ endpoint.')
    RETURNING id
), s5 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (2, 'Using ____ allows Spring to inject a ____ automatically.')
    RETURNING id
), s6 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (2, 'The ____ file is used to configure the ____ in Spring.')
    RETURNING id
)
INSERT INTO constructor_sentence_blanks (sentence_id, order_index, flashcard_id)
SELECT s4.id, 1, 6 FROM s4 UNION ALL
SELECT s4.id, 2, 6 FROM s4 UNION ALL
SELECT s5.id, 1, 5 FROM s5 UNION ALL
SELECT s5.id, 2, 8 FROM s5 UNION ALL
SELECT s6.id, 1, 7 FROM s6 UNION ALL
SELECT s6.id, 2, 7 FROM s6;

-- React Native (Topic ID 3)
WITH s7 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (3, 'Props are passed into a ____ to render different ____.')
    RETURNING id
), s8 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (3, 'You manage ____ in a ____ using the useState hook.')
    RETURNING id
), s9 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (3, 'To handle side effects, we use ____ inside a ____.')
    RETURNING id
)
INSERT INTO constructor_sentence_blanks (sentence_id, order_index, flashcard_id)
SELECT s7.id, 1, 9 FROM s7 UNION ALL
SELECT s7.id, 2, 11 FROM s7 UNION ALL
SELECT s8.id, 1, 10 FROM s8 UNION ALL
SELECT s8.id, 2, 9 FROM s8 UNION ALL
SELECT s9.id, 1, 12 FROM s9 UNION ALL
SELECT s9.id, 2, 9 FROM s9;

-- Database Design (Topic ID 4)
WITH s10 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (4, 'Every table should have a ____ and may contain a ____.')
    RETURNING id
), s11 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (4, 'A good ____ helps speed up queries that use a ____.')
    RETURNING id
), s12 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (4, 'Applying ____ eliminates redundancy in ____ design.')
    RETURNING id
)
INSERT INTO constructor_sentence_blanks (sentence_id, order_index, flashcard_id)
SELECT s10.id, 1, 13 FROM s10 UNION ALL
SELECT s10.id, 2, 14 FROM s10 UNION ALL
SELECT s11.id, 1, 16 FROM s11 UNION ALL
SELECT s11.id, 2, 13 FROM s11 UNION ALL
SELECT s12.id, 1, 15 FROM s12 UNION ALL
SELECT s12.id, 2, 15 FROM s12;

-- Debugging Techniques (Topic ID 5)
WITH s13 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (5, 'We placed a ____ to analyze the ____ during execution.')
    RETURNING id
), s14 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (5, 'The ____ provides a history of method calls and helps debug the ____.')
    RETURNING id
), s15 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (5, 'You can use a ____ to monitor changes in a ____.')
    RETURNING id
)
INSERT INTO constructor_sentence_blanks (sentence_id, order_index, flashcard_id)
SELECT s13.id, 1, 17 FROM s13 UNION ALL
SELECT s13.id, 2, 18 FROM s13 UNION ALL
SELECT s14.id, 1, 18 FROM s14 UNION ALL
SELECT s14.id, 2, 19 FROM s14 UNION ALL
SELECT s15.id, 1, 20 FROM s15 UNION ALL
SELECT s15.id, 2, 4 FROM s15;

-- Unit Testing (Topic ID 6)
WITH s16 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (6, 'We use ____ to simulate behavior and test with ____.')
    RETURNING id
), s17 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (6, 'A test passes if the ____ matches the expected ____ in JUnit.')
    RETURNING id
), s18 AS (
    INSERT INTO constructor_sentences (topic_id, sentence_template)
    VALUES (6, 'Writing tests using ____ increases the overall ____.')
    RETURNING id
)
INSERT INTO constructor_sentence_blanks (sentence_id, order_index, flashcard_id)
SELECT s16.id, 1, 23 FROM s16 UNION ALL
SELECT s16.id, 2, 21 FROM s16 UNION ALL
SELECT s17.id, 1, 22 FROM s17 UNION ALL
SELECT s17.id, 2, 22 FROM s17 UNION ALL
SELECT s18.id, 1, 21 FROM s18 UNION ALL
SELECT s18.id, 2, 24 FROM s18;
