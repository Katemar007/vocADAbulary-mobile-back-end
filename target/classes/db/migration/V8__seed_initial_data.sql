-- Insert test users
INSERT INTO users (id, username, email, created_at)
VALUES 
  (1, 'test_user1', 'test1@example.com', now()),
  (2, 'test_user2', 'test2@example.com', now());

-- Insert test topics
INSERT INTO topics (id, name)
VALUES 
  (1, 'Intro to Debugging'),
  (2, 'Functions');