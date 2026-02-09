-- Add new roles if they don't exist
INSERT INTO roles (name, description) VALUES 
('CHIEF', 'Chief of Operations - Global Access'),
('OPERATOR', 'Machine/Vehicle Operator'),
('ENGINEER', 'Mining Engineer'),
('SITE_ENGINEER', 'Construction Site Engineer')
ON CONFLICT (name) DO NOTHING;

-- Ensure existing roles have descriptions
UPDATE roles SET description = 'System Administrator' WHERE name = 'ADMIN';
UPDATE roles SET description = 'Standard Driver' WHERE name = 'DRIVER';
UPDATE roles SET description = 'Department Manager' WHERE name = 'MANAGER';

-- Verify/Seed SR GROUP Admin
INSERT INTO users (username, password, enabled, company)
VALUES ('sr-admin', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlZ6/.x.c.O', true, 'SR GROUP')
ON CONFLICT (username) DO NOTHING;

-- Check if role exists before inserting
DO $$
DECLARE
    v_user_id BIGINT;
    v_role_id BIGINT;
BEGIN
    SELECT id INTO v_user_id FROM users WHERE username = 'sr-admin';
    SELECT id INTO v_role_id FROM roles WHERE name = 'ADMIN';

    IF v_user_id IS NOT NULL AND v_role_id IS NOT NULL THEN
        INSERT INTO user_roles (user_id, role_id)
        VALUES (v_user_id, v_role_id)
        ON CONFLICT (user_id, role_id) DO NOTHING;
    END IF;
END $$;
