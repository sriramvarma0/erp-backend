CREATE TABLE mining_operators (
    id BIGSERIAL PRIMARY KEY,
    operator_id VARCHAR(255) NOT NULL UNIQUE,
    employee_code VARCHAR(255) UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    dob DATE,
    gender VARCHAR(50),
    contact_number VARCHAR(50) NOT NULL,
    emergency_contact_number VARCHAR(50),
    address_permanent TEXT,
    address_current TEXT,
    
    license_number VARCHAR(255),
    license_type VARCHAR(50),
    license_expiry_date DATE,
    
    aadhaar_number VARCHAR(50),
    pan_number VARCHAR(50),
    medical_fitness_expiry DATE,
    
    employment_type VARCHAR(50),
    date_of_joining DATE,
    shift_type VARCHAR(50),
    reporting_manager VARCHAR(255),
    
    assigned_machine_id BIGINT REFERENCES mining_vehicles(id),
    operating_zone VARCHAR(100),
    experience_years INTEGER,
    
    salary_type VARCHAR(50),
    base_salary NUMERIC(19, 2),
    allowances NUMERIC(19, 2),
    incentives NUMERIC(19, 2),
    
    status VARCHAR(50),
    last_active_date TIMESTAMP,
    
    user_id BIGINT UNIQUE REFERENCES users(id),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
