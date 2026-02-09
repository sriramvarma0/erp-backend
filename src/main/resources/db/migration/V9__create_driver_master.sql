CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    driver_id VARCHAR(50) NOT NULL UNIQUE, -- System generated (e.g., DRV-2024-001)
    employee_code VARCHAR(50) UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    dob DATE,
    gender VARCHAR(20),
    contact_number VARCHAR(20) NOT NULL,
    emergency_contact_number VARCHAR(20),
    address_permanent TEXT,
    address_current TEXT,

    -- License Details
    license_number VARCHAR(50) UNIQUE NOT NULL,
    license_type VARCHAR(50), -- LMV, HMV, Transport
    license_issuing_authority VARCHAR(100),
    license_issue_date DATE,
    license_expiry_date DATE,

    -- Compliance & Legal
    aadhaar_number VARCHAR(20) UNIQUE,
    pan_number VARCHAR(20),
    police_verification_status VARCHAR(50), -- Verified, Pending, Failed
    medical_fitness_expiry DATE,
    alcohol_test_required BOOLEAN DEFAULT FALSE,

    -- Employment Details
    employment_type VARCHAR(50), -- Permanent, Contract, Vendor
    date_of_joining DATE,
    shift_type VARCHAR(50), -- Day, Night, Rotational
    assigned_depot VARCHAR(100),
    reporting_manager VARCHAR(100),

    -- Vehicle & Operations
    assigned_vehicle_id BIGINT, -- Link to vehicles table
    route_assigned VARCHAR(100),
    driving_experience_years INT,
    trip_count INT DEFAULT 0,

    -- Performance & Safety
    accident_history BOOLEAN DEFAULT FALSE,
    number_of_accidents INT DEFAULT 0,
    traffic_violations_count INT DEFAULT 0,
    safety_training_completed BOOLEAN DEFAULT FALSE,
    driver_rating DECIMAL(3, 1), -- 1.0 to 5.0

    -- Payroll & Costing
    salary_type VARCHAR(50), -- Monthly, Per Trip, Per KM
    base_salary DECIMAL(10, 2),
    allowances DECIMAL(10, 2),
    incentives DECIMAL(10, 2),
    penalties DECIMAL(10, 2),

    -- Status & Availability
    status VARCHAR(50) DEFAULT 'Active', -- Active, On Trip, On Leave, Suspended
    availability_status VARCHAR(50) DEFAULT 'Available',
    last_trip_date TIMESTAMP,

    -- System & Creds
    user_id BIGINT UNIQUE, -- Link to users table for login credentials
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_driver_vehicle FOREIGN KEY (assigned_vehicle_id) REFERENCES vehicles(id),
    CONSTRAINT fk_driver_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Index for search
CREATE INDEX idx_driver_name ON drivers(full_name);
CREATE INDEX idx_driver_license ON drivers(license_number);
CREATE INDEX idx_driver_status ON drivers(status);

-- Seed Data
INSERT INTO drivers (driver_id, full_name, contact_number, license_number, status, employment_type)
VALUES ('DRV001', 'Ramesh Kumar', '9876543210', 'DL-01-2010-123456', 'Active', 'Permanent');
