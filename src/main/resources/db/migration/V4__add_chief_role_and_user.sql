INSERT INTO roles (name, description) VALUES ('ROLE_CHIEF', 'Chief Executive');

INSERT INTO users (username, password, enabled) 
VALUES ('chief', '$2a$10$NMiTKI.yOnLSMKSJTiYGiube/OsHeycXit2wVMLf9WKjbqYyC5V.6', true);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'chief' AND r.name = 'ROLE_CHIEF';
