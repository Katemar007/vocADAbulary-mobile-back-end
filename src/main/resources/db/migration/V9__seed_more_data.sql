-- Users
INSERT INTO users (id, username, email, created_at) VALUES
(3, 'liam.code', 'liam@example.com', NOW()),
(4, 'olivia_syntax', 'olivia@example.com', NOW()),
(5, 'noah.builder', 'noah@example.com', NOW()),
(6, 'ava_query', 'ava@example.com', NOW()),
(7, 'ethan_stack', 'ethan@example.com', NOW()),
(8, 'emma_dev', 'emma@example.com', NOW());

-- Topics
INSERT INTO topics (id, name, created_at) VALUES
(3, 'Spring Boot', NOW()),
(4, 'React Native', NOW()),
(5, 'Database Design', NOW()),
(6, 'Debugging Techniques', NOW()),
(7, 'Unit Testing', NOW()),
(8, 'Java Basics', NOW());

-- Flashcards for Java Basics (topic_id = 1)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(1, 1, 'Class', 'A blueprint for creating objects in Java.', 'We created a Car class to model vehicles.', 'template, structure', 'klæs', NULL, NOW()),
(2, 1, 'Object', 'An instance of a class.', 'We instantiated an object of the Car class.', 'instance', 'ˈɑːbdʒɛkt', NULL, NOW()),
(3, 1, 'Method', 'A function defined in a class.', 'The drive() method moves the car.', 'function, routine', 'ˈmɛθəd', NULL, NOW()),
(4, 1, 'Variable', 'A container for storing data values.', 'We declared a variable to store speed.', 'field, property', 'ˈvɛəriəbl', NULL, NOW());

-- Flashcards for Spring Boot (topic_id = 2)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(5, 2, 'Dependency Injection', 'A design pattern for passing dependencies into objects.', 'Spring Boot uses DI via @Autowired.', 'DI', 'dɪˈpɛndənsi ɪnˈʤɛkʃən', NULL, NOW()),
(6, 2, '@RestController', 'Annotation to mark a class as a REST controller.', '@RestController exposes REST endpoints.', 'controller', 'rəst kənˈtroʊlər', NULL, NOW()),
(7, 2, 'Application.properties', 'File for Spring Boot configuration.', 'We set server.port in application.properties.', 'config file', 'ˌæplɪˈkeɪʃən prɒpərtiz', NULL, NOW()),
(8, 2, 'Bean', 'An object managed by Spring container.', 'We declared a bean for our service class.', 'component', 'biːn', NULL, NOW());

-- Flashcards for React Native (topic_id = 3)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(9, 3, 'Component', 'Reusable piece of UI in React Native.', 'Header is a functional component.', 'widget, element', 'kəmˈpoʊnənt', NULL, NOW()),
(10, 3, 'State', 'Object to hold component data.', 'We used useState to manage state.', 'data store', 'steɪt', NULL, NOW()),
(11, 3, 'Props', 'Inputs passed to components.', 'Title was passed via props.', 'properties, arguments', 'prɒps', NULL, NOW()),
(12, 3, 'useEffect', 'Hook for side effects in components.', 'useEffect runs when component mounts.', 'effect hook', 'juːz ɪˈfɛkt', NULL, NOW());

-- Flashcards for Database Design (topic_id = 4)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(13, 4, 'Primary Key', 'Unique identifier for table records.', 'User ID is the primary key.', 'identifier', 'ˈpraɪˌmɛri kiː', NULL, NOW()),
(14, 4, 'Foreign Key', 'Links a record to another table.', 'user_id is a foreign key in flashcards.', 'reference key', 'ˈfɔːrən kiː', NULL, NOW()),
(15, 4, 'Normalization', 'Process to reduce data redundancy.', 'We normalized the schema to 3NF.', 'optimization', 'ˌnɔːrmələˈzeɪʃən', NULL, NOW()),
(16, 4, 'Index', 'Structure to improve query speed.', 'Created an index on email column.', 'lookup table', 'ˈɪndɛks', NULL, NOW());

-- Flashcards for Debugging Techniques (topic_id = 5)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(17, 5, 'Breakpoint', 'Stops code execution during debugging.', 'Set a breakpoint to inspect the loop.', 'pause point', 'ˈbreɪkpɔɪnt', NULL, NOW()),
(18, 5, 'Stack Trace', 'Displays method calls at crash time.', 'The error came with a helpful stack trace.', 'call stack', 'stæk treɪs', NULL, NOW()),
(19, 5, 'Console Log', 'Tool for printing debug info.', 'Used console.log() to check variable.', 'log output', 'ˈkɒnsəʊl lɒɡ', NULL, NOW()),
(20, 5, 'Watch Expression', 'Monitors variable changes in debugger.', 'Added a watch expression for count.', 'variable monitor', 'wɒʧ ɪkˈsprɛʃən', NULL, NOW());

-- Flashcards for Unit Testing (topic_id = 6)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(21, 6, 'JUnit', 'Framework for Java unit testing.', 'We wrote tests using JUnit 5.', 'test framework', 'ˈdʒeɪ ˌjuː ɪn ɪt', NULL, NOW()),
(22, 6, 'Assertion', 'Statement to verify expected outcome.', 'Used assertEquals in the test.', 'check, condition', 'əˈsɜːrʃən', NULL, NOW()),
(23, 6, 'Mocking', 'Simulating dependencies in tests.', 'We mocked the service layer.', 'faking', 'ˈmɒkɪŋ', NULL, NOW()),
(24, 6, 'Test Coverage', 'Metric of tested code proportion.', 'Increased coverage to 90%.', 'coverage rate', 'tɛst ˈkʌvərɪdʒ', NULL, NOW());

-- UserFlashcard associations
INSERT INTO user_flashcards (user_id, flashcard_id, status, last_reviewed) VALUES
-- Emma (user_id = 2)
(8, 1, 'IN_PROGRESS', NOW()),
(8, 2, 'LEARNED', NOW()),
(8, 3, 'LEARNED', NOW()),
(8, 4, 'FAVORITE', NOW()),

-- Liam (user_id = 3)
(3, 5, 'IN_PROGRESS', NOW()),
(3, 6, 'LEARNED', NOW()),
(3, 7, 'IN_PROGRESS', NOW()),
(3, 8, 'FAVORITE', NOW()),

-- Olivia (user_id = 4)
(4, 9, 'LEARNED', NOW()),
(4, 10, 'IN_PROGRESS', NOW()),
(4, 11, 'LEARNED', NOW()),
(4, 12, 'FAVORITE', NOW()),

-- Noah (user_id = 5)
(5, 13, 'LEARNED', NOW()),
(5, 14, 'IN_PROGRESS', NOW()),
(5, 15, 'FAVORITE', NOW()),
(5, 16, 'IN_PROGRESS', NOW()),

-- Ava (user_id = 6)
(6, 17, 'IN_PROGRESS', NOW()),
(6, 18, 'LEARNED', NOW()),
(6, 19, 'LEARNED', NOW()),
(6, 20, 'FAVORITE', NOW()),

-- Ethan (user_id = 7)
(7, 21, 'FAVORITE', NOW()),
(7, 22, 'LEARNED', NOW()),
(7, 23, 'IN_PROGRESS', NOW()),
(7, 24, 'IN_PROGRESS', NOW());