ALTER TABLE drivers ADD COLUMN department VARCHAR(50);
UPDATE drivers SET department = 'Logistics' WHERE department IS NULL;
