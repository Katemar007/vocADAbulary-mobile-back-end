-- === Insert Topics ===
INSERT INTO topic (id, name, created_at) VALUES
  (1, 'Functions', NOW()),
  (2, 'Recursion', NOW()),
  (3, 'Debugging', NOW()),
  (4, 'Lists and Tuples', NOW()),
  (5, 'Conditionals', NOW());

-- === Insert Flashcards ===
-- Topic 1: Functions
INSERT INTO flashcard (topic_id, word, phonetic, definition, example, synonyms, audio_url, created_at) VALUES
  (1, 'function', 'ˈfʌŋkʃən', 'A reusable block of code that performs a specific task.', 'We created a function to validate user input.', 'method, procedure', '', NOW()),
  (1, 'parameter', 'pəˈræmɪtər', 'A variable passed into a function.', 'The function takes two parameters: width and height.', 'argument, input', '', NOW());

-- Topic 2: Recursion
INSERT INTO flashcard (topic_id, word, phonetic, definition, example, synonyms, audio_url, created_at) VALUES
  (2, 'recursion', 'rɪˈkɜːʃən', 'The process in which a function calls itself.', 'The factorial of 5 was calculated using recursion.', 'self-calling function', '', NOW()),
  (2, 'base case', 'beɪs keɪs', 'The condition that stops a recursive function from calling itself.', 'Define a base case to prevent infinite recursion.', '', '', NOW());

-- Topic 3: Debugging
INSERT INTO flashcard (topic_id, word, phonetic, definition, example, synonyms, audio_url, created_at) VALUES
  (3, 'debug', 'diːˈbʌɡ', 'To find and fix errors in code.', 'I used console.log to debug the login issue.', 'troubleshoot, fix', '', NOW()),
  (3, 'breakpoint', 'ˈbreɪkpɔɪnt', 'A marker that pauses code execution during debugging.', 'She set a breakpoint to examine variable values.', '', '', NOW());

-- Topic 4: Lists and Tuples
INSERT INTO flashcard (topic_id, word, phonetic, definition, example, synonyms, audio_url, created_at) VALUES
  (4, 'list', 'lɪst', 'An ordered, mutable collection of elements.', 'We looped through the list of scores.', 'array, collection', '', NOW()),
  (4, 'tuple', 'ˈtʌpl', 'An ordered, immutable collection of elements.', 'The function returns a tuple of coordinates.', '', '', NOW());

-- Topic 5: Conditionals
INSERT INTO flashcard (topic_id, word, phonetic, definition, example, synonyms, audio_url, created_at) VALUES
  (5, 'if statement', 'ɪf ˈsteɪtmənt', 'A conditional that executes code if a condition is true.', 'Use an if statement to check if the number is even.', 'conditional', '', NOW()),
  (5, 'else', 'ɛls', 'A block that executes if the condition is false.', 'Add an else block for the default case.', '', '', NOW());