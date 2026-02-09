INSERT INTO roles (name, description) VALUES ('ROLE_ADMIN', 'Administrator');
INSERT INTO roles (name, description) VALUES ('ROLE_USER', 'Standard User');

-- Password is 'admin123'
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt6EXPx.To586Cl/9y/PyzTrkBK', true);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';
