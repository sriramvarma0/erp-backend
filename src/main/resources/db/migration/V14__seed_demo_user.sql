-- Password is 'password123' (BCrypt hash)
INSERT INTO users (username, password, enabled, company) 
VALUES ('admin-user', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlZ6/.x.c.O', true, 'Logistics');

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.username = 'admin-user' AND r.name = 'ROLE_ADMIN';
