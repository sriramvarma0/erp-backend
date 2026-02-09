-- Drop the old string column
ALTER TABLE users DROP COLUMN IF EXISTS company;

-- Add the new foreign key column
ALTER TABLE users ADD COLUMN company_id BIGINT;

-- Add constraint
ALTER TABLE users ADD CONSTRAINT fk_user_company FOREIGN KEY (company_id) REFERENCES companies(id);

-- Add SR Group if not exists (V5 added others)
INSERT INTO companies (name, description)
SELECT 'SR Group', 'Parent Company - Global Access'
WHERE NOT EXISTS (SELECT 1 FROM companies WHERE name = 'SR Group');
