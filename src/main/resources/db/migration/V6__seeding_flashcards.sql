-- Flashcards for Java Basics (topic_id = 1)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(25, 1, 'Constructor', 'Special method called when creating objects.', 'The Car constructor initializes speed to zero.', 'initializer, builder', 'kənˈstrʌktər', NULL, NOW()),
(26, 1, 'Inheritance', 'Mechanism where one class inherits from another.', 'SportsCar inherits from the Car class.', 'extension, derivation', 'ɪnˈhɛrɪtəns', NULL, NOW()),
(27, 1, 'Polymorphism', 'Ability for objects to take multiple forms.', 'The draw() method works differently for shapes.', 'multiple forms', 'ˌpɒlɪˈmɔːrfɪzəm', NULL, NOW()),
(28, 1, 'Encapsulation', 'Bundling data and methods in a class.', 'Private fields are accessed via getter methods.', 'data hiding, wrapping', 'ɪnˌkæpsjʊˈleɪʃən', NULL, NOW()),
(29, 1, 'Interface', 'Contract defining what methods a class must implement.', 'Vehicle interface defines start() and stop().', 'contract, protocol', 'ˈɪntərfeɪs', NULL, NOW()),
(30, 1, 'Package', 'Namespace for organizing related classes.', 'All models are in com.app.models package.', 'namespace, module', 'ˈpækɪdʒ', NULL, NOW()),
(31, 1, 'Static', 'Keyword for class-level members.', 'Static methods belong to the class itself.', 'class-level', 'ˈstætɪk', NULL, NOW()),
(32, 1, 'Final', 'Keyword preventing modification or extension.', 'Final variables cannot be reassigned.', 'constant, immutable', 'ˈfaɪnəl', NULL, NOW()),
(33, 1, 'Abstract', 'Keyword for incomplete classes or methods.', 'Abstract classes cannot be instantiated.', 'incomplete, partial', 'ˈæbstrækt', NULL, NOW()),
(35, 1, 'Exception', 'Error that occurs during program execution.', 'We caught a NullPointerException.', 'error, runtime error', 'ɪkˈsɛpʃən', NULL, NOW()),
(36, 1, 'Try-Catch', 'Block for handling exceptions gracefully.', 'Try-catch prevents program crashes.', 'error handling', 'traɪ kæʧ', NULL, NOW()),
(37, 1, 'Array', 'Data structure storing multiple values.', 'Integer array holds student grades.', 'list, collection', 'əˈreɪ', NULL, NOW()),
(38, 1, 'Loop', 'Control structure for repeated execution.', 'For loop iterates through the array.', 'iteration, repetition', 'luːp', NULL, NOW()),
(39, 1, 'Condition', 'Boolean expression controlling program flow.', 'If condition checks user input validity.', 'boolean test, check', 'kənˈdɪʃən', NULL, NOW());
-- ^ changed trailing comma to semicolon

-- Flashcards for Spring Boot (topic_id = 2)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(40, 2, '@Service', 'Annotation marking business logic components.', '@Service annotation defines UserService class.', 'business layer', 'ˈsɜːrvɪs', NULL, NOW()),
(41, 2, '@Repository', 'Annotation for data access layer components.', '@Repository handles database operations.', 'data layer, DAO', 'rɪˈpɒzɪtəri', NULL, NOW()),
(42, 2, '@Entity', 'JPA annotation marking database table classes.', '@Entity maps User class to users table.', 'table mapping', 'ˈɛntɪti', NULL, NOW()),
(43, 2, 'JPA', 'Java Persistence API for database operations.', 'JPA simplifies database CRUD operations.', 'ORM, persistence', 'dʒeɪ piː eɪ', NULL, NOW()),
(44, 2, '@Autowired', 'Annotation for automatic dependency injection.', '@Autowired injects UserService automatically.', 'injection, wiring', 'ˈɔːtoʊˌwaɪərd', NULL, NOW()),
(45, 2, '@RequestMapping', 'Maps HTTP requests to controller methods.', '@RequestMapping("/users") handles user endpoints.', 'URL mapping', 'rɪˈkwɛst ˈmæpɪŋ', NULL, NOW()),
(46, 2, '@PathVariable', 'Extracts values from URL path segments.', '@PathVariable gets user ID from URL.', 'URL parameter', 'pæθ ˈvɛəriəbl', NULL, NOW()),
(47, 2, '@RequestBody', 'Maps HTTP request body to method parameter.', '@RequestBody converts JSON to User object.', 'body mapping', 'rɪˈkwɛst ˈbɒdi', NULL, NOW()),
(48, 2, 'Spring Context', 'Container managing application objects.', 'Spring context initializes all beans.', 'IoC container', 'sprɪŋ ˈkɒntɛkst', NULL, NOW()),
(49, 2, 'Actuator', 'Module providing production-ready features.', 'Actuator endpoints monitor app health.', 'monitoring, metrics', 'ˈæktʃuˌeɪtər', NULL, NOW()),
(50, 2, '@Configuration', 'Marks classes containing bean definitions.', '@Configuration class defines custom beans.', 'config class', 'kənˌfɪɡjʊˈreɪʃən', NULL, NOW()),
(51, 2, '@Component', 'Generic annotation for Spring-managed classes.', '@Component makes class a Spring bean.', 'managed class', 'kəmˈpoʊnənt', NULL, NOW()),
(52, 2, 'Profile', 'Environment-specific configuration groups.', 'Development profile uses H2 database.', 'environment, config', 'ˈproʊfaɪl', NULL, NOW()),
(53, 2, '@Value', 'Injects property values into fields.', '@Value reads from application.properties.', 'property injection', 'ˈvælju', NULL, NOW());
-- ^ semicolon

-- Flashcards for React Native (topic_id = 3)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(54, 3, 'JSX', 'Syntax extension mixing JavaScript with markup.', 'JSX makes React components readable.', 'markup syntax', 'dʒeɪ ɛs ɛks', NULL, NOW()),
(55, 3, 'Hook', 'Functions adding state to functional components.', 'useState hook manages component state.', 'state function', 'hʊk', NULL, NOW()),
(56, 3, 'Navigation', 'System for moving between screens.', 'React Navigation handles screen transitions.', 'routing, screen flow', 'ˌnævɪˈɡeɪʃən', NULL, NOW()),
(57, 3, 'StyleSheet', 'Object defining component styling rules.', 'StyleSheet.create organizes component styles.', 'CSS-like styles', 'ˈstaɪlʃiːt', NULL, NOW()),
(58, 3, 'FlatList', 'Component for rendering scrollable lists.', 'FlatList displays array of items efficiently.', 'list view', 'flæt lɪst', NULL, NOW()),
(59, 3, 'AsyncStorage', 'Local storage solution for React Native.', 'AsyncStorage persists user preferences.', 'local storage', 'əˈsɪŋk ˈstɔːrɪdʒ', NULL, NOW()),
(60, 3, 'Touchable', 'Component handling user touch interactions.', 'TouchableOpacity provides button feedback.', 'pressable, button', 'ˈtʌʧəbl', NULL, NOW()),
(61, 3, 'Flex', 'Layout system for arranging components.', 'Flex properties control component positioning.', 'flexbox, layout', 'flɛks', NULL, NOW()),
(62, 3, 'Metro', 'Bundler that packages React Native apps.', 'Metro compiles JavaScript for mobile.', 'bundler, compiler', 'ˈmɛtroʊ', NULL, NOW()),
(63, 3, 'Bridge', 'Communication layer between JS and native.', 'React Native bridge enables native features.', 'communication layer', 'brɪdʒ', NULL, NOW()),
(64, 3, 'Expo', 'Platform simplifying React Native development.', 'Expo provides ready-to-use components.', 'development platform', 'ˈɛkspoʊ', NULL, NOW()),
(65, 3, 'Lifecycle', 'Stages of component creation and destruction.', 'useEffect manages component lifecycle.', 'component stages', 'ˈlaɪfˌsaɪkəl', NULL, NOW()),
(66, 3, 'Context', 'Way to share data across component tree.', 'React Context avoids prop drilling.', 'global state', 'ˈkɒntɛkst', NULL, NOW()),
(67, 3, 'Reducer', 'Function managing complex state changes.', 'useReducer handles multiple state updates.', 'state manager', 'rɪˈduːsər', NULL, NOW()),
(68, 3, 'Ref', 'Way to access DOM elements directly.', 'useRef stores mutable object references.', 'reference, pointer', 'rɛf', NULL, NOW());
-- ^ semicolon

-- Flashcards for Database Design (topic_id = 4)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(69, 4, 'Schema', 'Structure defining database organization.', 'Database schema includes tables and relationships.', 'structure, blueprint', 'ˈskiːmə', NULL, NOW()),
(70, 4, 'Query', 'Command to retrieve or modify data.', 'SQL query fetches user information.', 'database command', 'ˈkwɪəri', NULL, NOW()),
(71, 4, 'Join', 'Operation combining data from multiple tables.', 'Inner join connects users with orders.', 'table combination', 'dʒɔɪn', NULL, NOW()),
(72, 4, 'Transaction', 'Group of operations executed as single unit.', 'Database transaction ensures data consistency.', 'atomic operation', 'trænˈzækʃən', NULL, NOW()),
(73, 4, 'Constraint', 'Rule enforcing data integrity.', 'NOT NULL constraint prevents empty values.', 'rule, validation', 'kənˈstreɪnt', NULL, NOW()),
(74, 4, 'View', 'Virtual table based on query results.', 'Database view simplifies complex queries.', 'virtual table', 'vjuː', NULL, NOW()),
(75, 4, 'Trigger', 'Code automatically executed on data changes.', 'Database trigger logs all modifications.', 'automatic procedure', 'ˈtrɪɡər', NULL, NOW()),
(76, 4, 'Procedure', 'Stored database program performing tasks.', 'Stored procedure calculates monthly reports.', 'database function', 'prəˈsiːdʒər', NULL, NOW()),
(77, 4, 'CRUD', 'Basic operations: Create, Read, Update, Delete.', 'REST API implements CRUD operations.', 'basic operations', 'krʌd', NULL, NOW()),
(78, 4, 'Relationship', 'Connection between different table entities.', 'One-to-many relationship links users to orders.', 'association, link', 'rɪˈleɪʃənʃɪp', NULL, NOW()),
(79, 4, 'Migration', 'Script updating database structure.', 'Database migration adds new columns.', 'schema update', 'maɪˈɡreɪʃən', NULL, NOW()),
(80, 4, 'Backup', 'Copy of database for disaster recovery.', 'Daily backup protects against data loss.', 'data copy, archive', 'ˈbækʌp', NULL, NOW()),
(81, 4, 'Replication', 'Process of copying data across servers.', 'Database replication ensures high availability.', 'data copying', 'ˌrɛplɪˈkeɪʃən', NULL, NOW()),
(82, 4, 'Partition', 'Dividing large tables into smaller pieces.', 'Table partition improves query performance.', 'data division', 'pɑːrˈtɪʃən', NULL, NOW()),
(83, 4, 'Aggregate', 'Function performing calculations on data groups.', 'COUNT aggregate returns total records.', 'summary function', 'ˈæɡrɪɡət', NULL, NOW());
-- ^ semicolon

-- Debugging (topic_id = 5)  — (renamed comment to match data)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(84, 5, 'Debugger', 'Tool for examining code execution step-by-step.', 'IDE debugger helps find logical errors.', 'debugging tool', 'dɪˈbʌɡər', NULL, NOW()),
(85, 5, 'Step Over', 'Execute current line without entering functions.', 'Step over skips function implementation details.', 'line execution', 'stɛp ˈoʊvər', NULL, NOW()),
(86, 5, 'Step Into', 'Enter function calls during debugging.', 'Step into reveals function internal logic.', 'function entry', 'stɛp ˈɪntu', NULL, NOW()),
(87, 5, 'Call Stack', 'List of active function calls.', 'Call stack shows execution path.', 'function stack', 'kɔl stæk', NULL, NOW()),
(88, 5, 'Memory Leak', 'Unreleased memory causing performance issues.', 'Memory leak detector found unused objects.', 'memory waste', 'ˈmɛməri lik', NULL, NOW()),
(89, 5, 'Race Condition', 'Error from timing of concurrent operations.', 'Thread synchronization prevents race conditions.', 'timing error', 'reɪs kənˈdɪʃən', NULL, NOW()),
(90, 5, 'Unit Test', 'Test verifying individual code components.', 'Unit tests catch bugs early.', 'component test', 'ˈjunɪt tɛst', NULL, NOW()),
(91, 5, 'Log Level', 'Classification of logging message importance.', 'DEBUG log level shows detailed information.', 'logging priority', 'lɔɡ ˈlɛvəl', NULL, NOW()),
(92, 5, 'Profiler', 'Tool measuring code performance characteristics.', 'Performance profiler identifies bottlenecks.', 'performance tool', 'ˈproʊfaɪlər', NULL, NOW()),
(93, 5, 'Assertion', 'Statement validating expected conditions.', 'Debug assertion checks variable bounds.', 'condition check', 'əˈsɜrʃən', NULL, NOW()),
(94, 5, 'Error Handling', 'Technique for managing runtime errors gracefully.', 'Proper error handling improves user experience.', 'exception management', 'ˈɛrər ˈhændlɪŋ', NULL, NOW()),
(95, 5, 'Code Review', 'Process of examining code for errors.', 'Code review catches bugs before deployment.', 'peer review', 'koʊd rɪˈvju', NULL, NOW()),
(96, 5, 'Rubber Duck', 'Debugging method explaining code aloud.', 'Rubber duck debugging reveals logical flaws.', 'explanation method', 'ˈrʌbər dʌk', NULL, NOW()),
(97, 5, 'Binary Search', 'Method narrowing bug location systematically.', 'Binary search debugging isolates problem areas.', 'systematic search', 'ˈbaɪnəri sɜrʧ', NULL, NOW()),
(98, 5, 'Regression Test', 'Test ensuring fixes don''t break existing features.', 'Regression tests verify system stability.', 'stability test', 'rɪˈɡrɛʃən tɛst', NULL, NOW());
-- ^^ changed don"t → don''t and ended with semicolon

-- Unit Testing (topic_id = 6)
INSERT INTO flashcards (id, topic_id, word, definition, example, synonyms, phonetic, audio_url, created_at) VALUES
(99, 6, 'Test Suite', 'Collection of related test cases.', 'Test suite covers all user authentication.', 'test collection', 'tɛst swit', NULL, NOW()),
(100, 6, 'Test Case', 'Individual scenario verifying specific behavior.', 'Test case validates password encryption.', 'test scenario', 'tɛst keɪs', NULL, NOW()),
(101, 6, 'Setup', 'Preparation code run before each test.', 'Setup method initializes test database.', 'test preparation', 'ˈsɛtʌp', NULL, NOW()),
(102, 6, 'Teardown', 'Cleanup code run after each test.', 'Teardown method clears test data.', 'test cleanup', 'ˈtɛrˌdaʊn', NULL, NOW()),
(103, 6, 'Fixture', 'Predefined test data and environment.', 'Test fixture provides consistent data.', 'test data', 'ˈfɪkstʃər', NULL, NOW()),
(104, 6, 'Stub', 'Minimal implementation for testing dependencies.', 'Method stub returns fixed values.', 'dummy implementation', 'stʌb', NULL, NOW()),
(105, 6, 'Spy', 'Wrapper recording calls to real objects.', 'Test spy tracks method invocations.', 'call tracker', 'spaɪ', NULL, NOW()),
(106, 6, 'Integration Test', 'Test verifying component interactions.', 'Integration test checks API endpoints.', 'component test', 'ˌɪntɪˈɡreɪʃən tɛst', NULL, NOW()),
(107, 6, 'Test Runner', 'Framework executing and reporting tests.', 'JUnit test runner displays results.', 'test executor', 'tɛst ˈrʌnər', NULL, NOW()),
(108, 6, 'Parameterized Test', 'Test running with multiple input values.', 'Parameterized test checks various scenarios.', 'data-driven test', 'pəˈræmɪtəˌraɪzd tɛst', NULL, NOW()),
(109, 6, 'Test Double', 'Generic term for test substitutes.', 'Test double replaces external dependencies.', 'test substitute', 'tɛst ˈdʌbəl', NULL, NOW()),
(110, 6, 'Behavior Verification', 'Testing that methods are called correctly.', 'Behavior verification checks mock interactions.', 'interaction testing', 'bɪˈheɪvjər ˌvɛrəfəˈkeɪʃən', NULL, NOW()),
(111, 6, 'Test Isolation', 'Principle ensuring tests don''t affect each other.', 'Test isolation prevents cascading failures.', 'test independence', 'tɛst ˌaɪsəˈleɪʃən', NULL, NOW()),
(112, 6, 'Given-When-Then', 'Pattern structuring test scenarios clearly.', 'Given-When-Then improves test readability.', 'test structure', 'ˈɡɪvən wɛn ðɛn', NULL, NOW()),
(113, 6, 'Code Coverage', 'Metric showing percentage of tested code.', 'Line coverage reached 95% after new tests.', 'test completeness', 'koʊd ˈkʌvərɪdʒ', NULL, NOW());
-- ^ semicolon
