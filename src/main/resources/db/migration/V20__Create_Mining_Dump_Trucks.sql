CREATE TABLE mining_dump_trucks (
    id BIGSERIAL PRIMARY KEY,
    asset_id UUID NOT NULL UNIQUE,
    company_id BIGINT NOT NULL,
    
    manufacturer VARCHAR(100),
    model VARCHAR(100),
    variant VARCHAR(100),
    chassis_number VARCHAR(100) UNIQUE,
    engine_number VARCHAR(100) UNIQUE,
    registration_number VARCHAR(100) UNIQUE,
    ownership_type VARCHAR(50),
    asset_status VARCHAR(50),
    
    engine_displacement_cc INT,
    fuel_type VARCHAR(50),
    emission_norm VARCHAR(50),
    max_power_hp INT,
    max_torque_nm INT,
    axle_configuration VARCHAR(50),
    transmission_type VARCHAR(50),
    
    gvw_kg DOUBLE PRECISION,
    kerb_weight_kg DOUBLE PRECISION,
    payload_capacity_kg DOUBLE PRECISION,
    body_type VARCHAR(50),
    
    overall_length_mm DOUBLE PRECISION,
    wheelbase_mm DOUBLE PRECISION,
    ground_clearance_mm DOUBLE PRECISION,
    turning_radius_mm DOUBLE PRECISION,
    
    number_of_tyres INT,
    front_tyre_size VARCHAR(50),
    rear_tyre_size VARCHAR(50),
    
    fuel_tank_capacity_ltr DOUBLE PRECISION,
    average_mileage_kmpl DOUBLE PRECISION,
    
    purchase_date DATE,
    vendor_name VARCHAR(255),
    invoice_number VARCHAR(100),
    purchase_price DECIMAL(19, 2),
    depreciation_rate DOUBLE PRECISION,
    insurance_policy_number VARCHAR(100),
    insurance_expiry_date DATE,
    
    CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES companies(id)
);
