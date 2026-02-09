-- Add company_id column to vehicles table
ALTER TABLE vehicles ADD COLUMN company_id BIGINT;

-- Foreign key constraint
ALTER TABLE vehicles ADD CONSTRAINT fk_vehicle_company FOREIGN KEY (company_id) REFERENCES companies(id);

-- Populate existing vehicles with 'Logistics' company (assuming it exists, otherwise SR Group)
UPDATE vehicles 
SET company_id = (
    SELECT id FROM companies WHERE name = 'Logistics' LIMIT 1
)
WHERE company_id IS NULL;

-- Fallback if Logistics doesn't exist (e.g. dev env), use SR Group
UPDATE vehicles 
SET company_id = (
    SELECT id FROM companies WHERE name = 'SR Group' LIMIT 1
)
WHERE company_id IS NULL;
